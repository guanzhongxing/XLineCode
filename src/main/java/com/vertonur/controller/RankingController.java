package com.vertonur.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.ext.ranking.bean.PointConfig;
import com.vertonur.ext.ranking.bean.Ranking;
import com.vertonur.ext.ranking.exception.RankingWithPointsExistException;
import com.vertonur.ext.ranking.service.RankingService;
import com.vertonur.pojo.security.Group;

@Controller
public class RankingController {

	@RequestMapping(value = "/rankings", method = RequestMethod.GET)
	public String getList(HttpServletRequest request) {

		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		Ranking ranking = rankingService.getFirstRanking();
		Ranking timeRanking = rankingService.getFirstTimeRanking();
		request.setAttribute("ranking", ranking);
		request.setAttribute("timeRanking", timeRanking);

		return "default/admin/ranking/ranking_list";
	}

	@RequestMapping(value = "/rankings/{rankingId}", method = RequestMethod.GET)
	public String getSubList(@PathVariable int rankingId,
			@RequestParam int nestedLv, HttpServletRequest request) {

		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		Ranking ranking = rankingService.getRankingById(rankingId);
		request.setAttribute("ranking", ranking);
		request.setAttribute("nestedLv", ++nestedLv);
		return "default/admin/ranking/ranking_list_fragment";

	}

	@RequestMapping(value = "/rankings/form", method = RequestMethod.GET)
	public String showRankingForm(
			@RequestParam(defaultValue = "0") int rankingId,
			HttpServletRequest request) {

		if (rankingId != 0) {
			RankingService rankingService = SystemContextService.getService()
					.getExtendedBeanService(RankingService.class);
			Ranking ranking = rankingService.getRankingById(rankingId);
			request.setAttribute("ranking", ranking);

			Set<Group> groups = ranking.getGroups();
			Set<Integer> ids = new HashSet<Integer>();
			for (Group group : groups)
				ids.add(group.getId());
			request.setAttribute("rankingGroupIds", ids);
		}

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		List<Group> groups = groupService.getTopLevelGroups();
		request.setAttribute("groups", groups);

		return "default/admin/ranking/ranking_form";
	}

	@RequestMapping(value = "/rankings/form", method = RequestMethod.POST)
	public String createRanking(@Valid Ranking ranking,
			@RequestParam(required = false) int[] groupIds,
			HttpServletRequest request) {

		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		if (groupIds != null) {
			GroupService groupService = SystemContextService.getService()
					.getDataManagementService(ServiceEnum.GROUP_SERVICE);

			Set<Group> groups = new HashSet<Group>();
			for (int id : groupIds) {
				Group group = groupService.getGroupById(id);
				groups.add(group);
			}
			ranking.setGroups(groups);
		}

		try {
			rankingService.saveRanking(ranking);
		} catch (RankingWithPointsExistException e) {
			request.setAttribute("pointsExist", true);
			return "default/admin/ranking/ranking_form";
		}

		return "redirect:/rankings";
	}

	@RequestMapping(value = "/rankings/{rankingId}", method = RequestMethod.PUT)
	public String updateRanking(@Valid Ranking ranking,
			@PathVariable int rankingId,
			@RequestParam(required = false) int[] groupIds,
			HttpServletRequest request) {

		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		Ranking savedRanking = rankingService.getRankingById(rankingId);
		savedRanking.setName(ranking.getName());
		int originalPoints = savedRanking.getPoints();
		savedRanking.setPoints(ranking.getPoints());
		boolean timeRanking = ranking.isTimeRanking();
		savedRanking.setTimeRanking(timeRanking);
		if (timeRanking)
			savedRanking.setLimitHours(ranking.getLimitHours());

		if (groupIds != null) {
			GroupService groupService = SystemContextService.getService()
					.getDataManagementService(ServiceEnum.GROUP_SERVICE);

			Set<Group> groups = new HashSet<Group>();
			for (int id : groupIds) {
				Group group = groupService.getGroupById(id);
				groups.add(group);
			}
			savedRanking.setGroups(groups);
		}

		try {
			rankingService.updateRanking(savedRanking);
		} catch (RankingWithPointsExistException e) {
			request.setAttribute("pointsExist", true);
			savedRanking.setPoints(originalPoints);
			return "default/admin/ranking/ranking_form";
		}

		return "redirect:/rankings";
	}

	@RequestMapping(value = "/rankings", method = RequestMethod.DELETE)
	public String deleteRanking(@RequestParam int[] ids,
			HttpServletRequest request) {

		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		rankingService.deleteRanking(ids);

		return "redirect:/rankings";
	}

	@RequestMapping(value = "/rankings/points", method = RequestMethod.GET)
	public String getRankingPoints(
			@RequestParam(required = false) boolean updated,
			HttpServletRequest request) {

		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		PointConfig config = rankingService.getPointConfig();
		request.setAttribute("config", config);
		request.setAttribute("updated", updated);

		return "default/admin/ranking/ranking_points_config";
	}

	@RequestMapping(value = "/rankings/points", method = RequestMethod.PUT)
	public String updateRankingPoints(int topicPoints, int rspPoints,
			int uploadAttmPoints, HttpServletRequest request) {

		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		PointConfig pointConfig = rankingService.getPointConfig();
		pointConfig.setInfoPoints(topicPoints);
		pointConfig.setCmtPoints(rspPoints);
		pointConfig.setUploadAttmPoints(uploadAttmPoints);
		rankingService.updatePointConfig(pointConfig);

		return "redirect:/rankings/points?updated=true";
	}
}
