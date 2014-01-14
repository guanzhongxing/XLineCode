package com.vertonur.user.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.UserService;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.pojo.User;
import com.vertonur.pojo.config.UserConfig;

public class DisplayMyProfileAction extends OperactionCheckAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return super.execute(mapping, form, request, response);
	}

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveToken(request);

		int userId = Integer.parseInt(request.getParameter("userId"));
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		User user = userService.getUserById(userId);
		request.setAttribute("displayedUser", user);
		
		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		UserConfig config = service.getUserConfig();
		request.setAttribute("avatarHeight", config.getAvatarHeight());
		request.setAttribute("avatarWidth", config.getAvatarWidth());

		return mapping.findForward("ToDisplayMyProfilePage");
	}
}
