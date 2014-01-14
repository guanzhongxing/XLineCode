package com.vertonur.user.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;

public class InitUserLoginAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		request.setAttribute("loginCaptchaEnabled",
				config.isLoginCaptchaEnabled());
		return mapping.findForward("ToLoginFormPage");
	}
}
