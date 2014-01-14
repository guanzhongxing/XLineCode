package com.vertonur.admin.action.ranking;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.ext.ranking.bean.Ranking;
import com.vertonur.ext.ranking.service.RankingService;
import com.vertonur.pojo.security.Group;

public class InitRankingCreationAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveToken(request);
		String rankingIdStr = request.getParameter("rankingId");
		if (rankingIdStr != null) {
			RankingService rankingService = SystemContextService.getService()
					.getExtendedBeanService(RankingService.class);
			Ranking ranking = rankingService.getRankingById(Integer
					.parseInt(rankingIdStr));
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

		return mapping.findForward("RankingFormPage");
	}
}
