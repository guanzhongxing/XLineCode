package com.vertonur.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.session.UserSession;
import com.vertonur.session.UserSession.RequestContext;
import com.vertonur.constants.Constants;

public abstract class OperactionCheckAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		if (userSession.isGuest()) {
			Enumeration<String> names = request.getParameterNames();
			Map<String, String> params = new HashMap<String, String>();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				params.put(name, request.getParameter(name));
			}
			RequestContext context = userSession.new RequestContext(
					request.getServletPath(), params);
			userSession.setRequestContext(context);
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.all.session.invalid"));
			saveErrors(request, errors);
			return mapping.findForward("TimeoutSession");
		}

		// TODO: check operaction permissions accroding to roles

		return processRequest(mapping, form, request, response);
	}

	public abstract ActionForward processRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
