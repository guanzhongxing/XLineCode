package com.vertonur.user.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.InvalidOldPasswordException;
import com.vertonur.session.UserSession;
import com.vertonur.user.user.form.ChangePasswordForm;

public class ChangePasswordAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.findForward("ToForumList");
		}

		ChangePasswordForm castedForm = (ChangePasswordForm) form;
		String currentPwd = castedForm.getCurrentPwd();
		String newPwd = castedForm.getNewPwd();
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		SystemContextService service = SystemContextService.getService();
		UserService userService = service
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		int id = userSession.getUserId();
		try {
			userService.changePassword(id, currentPwd, newPwd);
			request.setAttribute("passwordChanged", "true");
			return mapping.findForward("MessagePage");
		} catch (InvalidOldPasswordException e) {
			ActionMessages errors = new ActionMessages();
			errors.add("invalidPwd", new ActionMessage(
					"error.invalid.pwd"));
			saveErrors(request, errors);
			return mapping.findForward("ToChangePasswordPage");
		}
	}
}
