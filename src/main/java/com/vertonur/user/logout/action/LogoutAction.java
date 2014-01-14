package com.vertonur.user.logout.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.session.UserSession;
import com.vertonur.constants.Constants;

public class LogoutAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		if (userSession != null&&userSession.isLogin()) {
			SystemContextService.getService().logout(userSession);
			response.sendRedirect("user/filter_logout");
			return null;
		}
		
		return mapping.findForward("UserLoginPage");
	}
}