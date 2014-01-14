package com.vertonur.user.registration.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.UserService;
import com.vertonur.common.HttpMethodRouterAction;
import com.vertonur.pojo.User;

public class ActivateAccountAction extends HttpMethodRouterAction {

	@Override
	protected ActionForward handleGetRequest(HttpServletRequest request,
			HttpServletResponse response, ActionMapping mapping, ActionForm form)
			throws ServletException, IOException {

		String initToken = request.getParameter("initManualActivationToken");
		if (initToken != null) {
			saveToken(request);
			return mapping.findForward("ManuallyActivateAcctPage");
		}

		String sessionId = request.getParameter("authCode");
		String userIdStr = request.getParameter("userId");
		return activateAccount(mapping, request, response, sessionId, userIdStr);
	}

	@Override
	protected ActionForward handlePostRequest(HttpServletRequest request,
			HttpServletResponse response, ActionMapping mapping, ActionForm form)
			throws Exception {
		String userIdStr = request.getParameter("userId");
		String sessionId = request.getParameter("authCode");
		return activateAccount(mapping, request, response, sessionId, userIdStr);
	}

	private ActionForward activateAccount(ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response,
			String sessionId, String userIdStr) throws ServletException,
			IOException {
		boolean validUserId = true;
		int id = -1;
		if (userIdStr == null)
			validUserId = false;
		try {
			id = Integer.parseInt(userIdStr);
		} catch (NumberFormatException e) {
			validUserId = false;
		}

		boolean validSessionId = SystemContextService.getService()
				.isAcctActivationSessionValid(sessionId, id);
		if (validSessionId && validUserId) {
			UserService userService = SystemContextService.getService()
					.getDataManagementService(ServiceEnum.USER_SERVICE);
			User user = userService.getUserById(id);
			String password = user.getPassword();
			userService.activateUser(id);
			response.sendRedirect("userLogin.do?userId=" + id + "&&password="
					+ password + "&&registrationMark=true");
			return null;
		} else {
			request.setAttribute("invalidActivation", "true");
			request.setAttribute("manualActivationUrl",
					"initManuallyActivateAcct.do?initManualActivationToken=true");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.jsp.invalid.activation"));
			saveMessages(request, messages);
			return mapping.findForward("MessagePage");
		}
	}
}
