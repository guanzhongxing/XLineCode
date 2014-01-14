package com.vertonur.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class SecureHttpMethodRouterAction extends
		OperactionCheckAction {

	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String requestType = request.getMethod();
		if ("GET".equals(requestType))
			return handleGetRequest(request, response, mapping, form);
		else
			return handlePostRequest(request, response, mapping, form);
	}

	protected abstract ActionForward handleGetRequest(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping mapping, ActionForm form) throws Exception;

	protected abstract ActionForward handlePostRequest(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping mapping, ActionForm form) throws Exception;
}
