package com.vertonur.admin.action;

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

public class TopicLockAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);

		int topicId = Integer.parseInt(request.getParameter("topicId"));
		String reason = request.getParameter("reason");
		InfoService service = new InfoService();
		if (request.getPathInfo().contains("/lock"))
			service.lockTopic(topicId, userSession, reason);
		else
			service.unlockTopic(topicId, userSession, reason);

		return mapping.findForward("DisplayResponses");
	}
}
