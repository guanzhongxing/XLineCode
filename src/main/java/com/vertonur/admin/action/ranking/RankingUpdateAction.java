package com.vertonur.admin.action.ranking;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.admin.form.RankingConfigForm;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.ext.ranking.bean.Ranking;
import com.vertonur.ext.ranking.exception.RankingWithPointsExistException;
import com.vertonur.ext.ranking.service.RankingService;
import com.vertonur.pojo.security.Group;

public class RankingUpdateAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		RankingConfigForm castedForm = (RankingConfigForm) form;
		int rankingId = castedForm.getId();
		String name = castedForm.getName();
		int points = castedForm.getPoints();
		boolean timeRanking = castedForm.isTimeRanking();

		Ranking ranking = null;
		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		if (rankingId != 0)
			ranking = rankingService.getRankingById(rankingId);
		else
			ranking = new Ranking();
		ranking.setName(name);
		int originalPoints = ranking.getPoints();
		ranking.setPoints(points);
		ranking.setTimeRanking(timeRanking);
		if (timeRanking)
			ranking.setLimitHours(castedForm.getLimitHours());

		int[] groupIds = castedForm.getGroupIds();
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
			if (rankingId != 0)
				rankingService.updateRanking(ranking);
			else
				rankingService.saveRanking(ranking);
		} catch (RankingWithPointsExistException e) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.ranking.with.points.exist"));
			saveErrors(request, errors);
			ranking.setPoints(originalPoints);
		}

		resetToken(request);
		return mapping.findForward("ToRankingList");
	}
}
