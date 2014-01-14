package com.vertonur.user.registration.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.security.Group;

public class InitRegistrationAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		saveToken(request);
		String fromAdmin = request.getParameter("fromAdmin");
		if ("true".equals(fromAdmin)) {
			request.setAttribute("fromAdmin", true);
			GroupService groupService = SystemContextService.getService()
					.getDataManagementService(ServiceEnum.GROUP_SERVICE);
			List<Group> groups = groupService.getTopLevelGroups();
			request.setAttribute("groups", groups);
		}

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		request.setAttribute("registrationCaptchaEnabled",
				config.isRegistrationCaptchaEnabled());
		return mapping.findForward("ToRegistrationPage");
	}
}
