package com.vertonur.admin.action.ranking;

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
import com.vertonur.ext.ranking.bean.PointConfig;
import com.vertonur.ext.ranking.service.RankingService;

public class RankingPointsConfigAction extends Action {

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
		int topicPoints = castedForm.getTopicPoints();
		int rspPoints = castedForm.getRspPoints();
		int uploadAttmPoints = castedForm.getUploadAttmPoints();
		RankingService rankingService = SystemContextService.getService()
				.getExtendedBeanService(RankingService.class);
		PointConfig pointConfig = rankingService.getPointConfig();
		pointConfig.setInfoPoints(topicPoints);
		pointConfig.setCmtPoints(rspPoints);
		pointConfig.setUploadAttmPoints(uploadAttmPoints);
		rankingService.updatePointConfig(pointConfig);

		resetToken(request);
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"message.update.points.success"));
		saveMessages(request, messages);
		return mapping.findForward("ToPointConfig");
	}
}
