package com.vertonur.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.User;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.security.Group;
import com.vertonur.service.UserService;

public class GroupListAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		String action = request.getParameter("action");
		if ("edit".equals(action) || "add".equals(action)) {
			saveToken(request);

			List<Group> groups = groupService.getTopLevelGroups();
			request.setAttribute("groups", groups);

			String groupIdStr = request.getParameter("groupId");
			if (groupIdStr != null && !"".equals(groupIdStr)) {
				int groupId = Integer.parseInt(groupIdStr);
				Group group = groupService.getGroupById(groupId);
				if (group.getNestedLevel() == 0)
					groups.remove(group);

				request.setAttribute("edittedGroup", group);
			}

			return mapping.findForward("GroupEditPage");
		} else if ("subGroup".equals(action)) {
			int groupId = Integer.parseInt(request.getParameter("groupId"));
			Group group = groupService.getGroupById(groupId);
			request.setAttribute("group", group);

			String type = request.getParameter("type");
			if (type != null && "list".equals(type))
				return mapping.findForward("SubGroupListPage");
			else if (type != null && "rankingOption".equals(type)) {
				return mapping.findForward("RankingSubGroupOptionPage");
			} else if (type != null && "userOption".equals(type)) {
				int userId = Integer.parseInt(request.getParameter("userId"));
				UserService userService = new UserService();
				User user = userService.getUserById(userId);
				request.setAttribute("user", user);
				return mapping.findForward("UserSubGroupOptionPage");
			} else
				return mapping.findForward("SubGroupOptionPage");
		} else {
			saveToken(request);

			String permissionConfig = request.getParameter("permissionConfig");
			List<Group> groups = null;
			if ("true".equals(permissionConfig))
				groups = groupService.getAdminTopLevelGroups();
			else
				groups = groupService.getTopLevelGroups();
			request.setAttribute("groups", groups);
			return mapping.findForward("GroupListPage");
		}
	}
}
