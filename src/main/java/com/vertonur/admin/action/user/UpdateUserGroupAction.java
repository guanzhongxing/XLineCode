package com.vertonur.admin.action.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.admin.form.UserAdminForm;
import com.vertonur.bean.User;
import com.vertonur.common.SecureHttpMethodRouterAction;
import com.vertonur.pojo.security.Group;
import com.vertonur.service.UserService;

public class UpdateUserGroupAction extends SecureHttpMethodRouterAction {

	@Override
	protected ActionForward handleGetRequest(HttpServletRequest request,
			HttpServletResponse response, ActionMapping mapping, ActionForm form)
			throws Exception {
		saveToken(request);
		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		List<Group> groups = groupService.getTopLevelGroups();
		request.setAttribute("groups", groups);

		int userId = Integer.parseInt(request.getParameter("userId"));
		UserService userService = new UserService();
		User user = userService.getUserById(userId);
		request.setAttribute("user", user);

		return mapping.findForward("UserGroupsPage");
	}

	@Override
	protected ActionForward handlePostRequest(HttpServletRequest request,
			HttpServletResponse response, ActionMapping mapping, ActionForm form)
			throws Exception {
		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		UserAdminForm loginForm = (UserAdminForm) form;
		int userId = loginForm.getUserId();
		UserService userService = new UserService();
		User user = userService.getUserById(userId);

		int[] groupIds = loginForm.getGroupIds();
		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);

		Set<Group> groups = new HashSet<Group>();
		for (int id : groupIds) {
			Group group = groupService.getGroupById(id);
			groups.add(group);
		}
		user.setGroups(groups);
		userService.updateUser(user);

		return mapping.findForward("AdminUserList");
	}

}
