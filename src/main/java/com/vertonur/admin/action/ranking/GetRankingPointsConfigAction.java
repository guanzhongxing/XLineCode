package com.vertonur.admin.action.ranking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.ext.ranking.bean.PointConfig;
import com.vertonur.ext.ranking.service.RankingService;

public class GetRankingPointsConfigAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveToken(request);
		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		PointConfig config = rankingService.getPointConfig();
		request.setAttribute("config", config);

		return mapping.findForward("PointsConfigPage");
	}
}
