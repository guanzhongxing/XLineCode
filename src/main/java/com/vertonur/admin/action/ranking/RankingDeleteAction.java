package com.vertonur.admin.action.ranking;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.context.SystemContextService;
import com.vertonur.ext.ranking.service.RankingService;
import com.vertonur.admin.form.RankingConfigForm;
import com.vertonur.common.OperactionCheckAction;

public class RankingDeleteAction extends OperactionCheckAction {

	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		RankingConfigForm castedForm = (RankingConfigForm) form;
		int[] ids = castedForm.getIds();
		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		rankingService.deleteRanking(ids);

		resetToken(request);
		return mapping.findForward("ToRankingList");
	}
}
