package com.vertonur.user.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.User;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.config.UserConfig;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;

public class DisplayProfileAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int userId = Integer.parseInt(request.getParameter("userId"));
		UserService userService = new UserService();
		User user = userService.getUserById(userId);
		request.setAttribute("displayedUser", user);

		InfoService infoService = new InfoService();
		long topicNum = infoService.getTopicNumByCreator(user);
		request.setAttribute("topicNum", topicNum);

		long rsNum = infoService.getResponseNumByUser(user);
		request.setAttribute("rsNum", rsNum);

		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		UserConfig config = service.getUserConfig();
		request.setAttribute("avatarHeight", config.getAvatarHeight());
		request.setAttribute("avatarWidth", config.getAvatarWidth());

		return mapping.findForward("ToDisplayProfilePage");
	}
}
