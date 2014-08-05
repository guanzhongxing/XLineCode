package com.vertonur.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.ext.ranking.bean.PointConfig;
import com.vertonur.ext.ranking.bean.Ranking;
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

	@RequestMapping(value = "/rankings/points", method = RequestMethod.GET)
	public String getRankingPoints(HttpServletRequest request) {

		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		PointConfig config = rankingService.getPointConfig();
		request.setAttribute("config", config);

		return "default/admin/ranking/ranking_points_config";
	}
}
