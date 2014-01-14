package com.vertonur.admin.action.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.admin.form.UserAdminForm;
import com.vertonur.bean.User;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.security.Group;
import com.vertonur.service.UserService;
import com.vertonur.util.ForumCommonUtil;

public class UserListAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String startStr = (String) request.getParameter("start");
		int paginationStart = ForumCommonUtil
				.strToIntTransitionBuffer(startStr);

		UserAdminForm adminForm = (UserAdminForm) form;
		String username = adminForm.getUsername();
		int groupId = adminForm.getGroupId();

		long paginationSize;
		List<User> members;
		UserService userService = new UserService();
		if ((!"".equals(username) && null != username) || groupId != 0) {
			paginationSize = userService.getUserNumByNameAndGroupId_AM(
					username, groupId);
			members = userService.getUsersByNameAndGroupId_AM(username,
					paginationStart, groupId);
		} else {
			paginationSize = userService.getUserNum();
			members = userService.getUsers(paginationStart);
		}
		request.setAttribute("members", members);

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		List<Group> groups = groupService.getTopLevelGroups();
		request.setAttribute("groups", groups);
		
		String requestPath = request.getRequestURI() + "?";
		if (!"".equals(username) && null != username) {
			requestPath += "username=" + username;
			request.setAttribute("username", username);
		}
		if (groupId != 0) {
			requestPath += "&groupId=" + groupId;
			request.setAttribute("groupId", groupId);
		}
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				paginationStart, requestPath, CxtType.MEMBER);
		request.setAttribute(PaginationContext.PAGE_CXT, pageCxt);
		
		return mapping.findForward("UserListPage");
	}
}
