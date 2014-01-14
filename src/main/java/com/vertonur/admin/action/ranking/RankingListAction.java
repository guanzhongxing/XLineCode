package com.vertonur.admin.action.ranking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.ext.ranking.bean.Ranking;
import com.vertonur.ext.ranking.service.RankingService;
import com.vertonur.common.OperactionCheckAction;

public class RankingListAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		String action = request.getParameter("action");
		if ("subList".equals(action)) {
			int rankingId = Integer.parseInt(request.getParameter("rankingId"));
			int nestedLv = Integer.parseInt(request.getParameter("nestedLv"));
			Ranking ranking = rankingService.getRankingById(rankingId);
			request.setAttribute("ranking", ranking);
			request.setAttribute("nestedLv", ++nestedLv);
			return mapping.findForward("RankingListFragmentPage");
		} else {
			Ranking ranking = rankingService.getFirstRanking();
			Ranking timeRanking = rankingService.getFirstTimeRanking();
			request.setAttribute("ranking", ranking);
			request.setAttribute("timeRanking", timeRanking);

			saveToken(request);
			return mapping.findForward("RankingListPage");
		}
	}
}
