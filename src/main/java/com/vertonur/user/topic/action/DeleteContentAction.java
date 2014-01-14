package com.vertonur.user.topic.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.constants.Constants;
import com.vertonur.service.InfoService;
import com.vertonur.session.UserSession;

public class DeleteContentAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int contentId = Integer.parseInt(request.getParameter("contentId"));
		String reason = request.getParameter("reason");
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		InfoService infoService = new InfoService();
		String contentType = request.getParameter("contentType");
		if ("topic".equals(contentType)) {
			infoService.deleteTopic(contentId, userSession, reason);
			return mapping.findForward("DisplayTopics");
		} else {
			infoService.deleteRsp(contentId, userSession, reason);
			return mapping.findForward("DisplayResponses");
		}
	}
}
