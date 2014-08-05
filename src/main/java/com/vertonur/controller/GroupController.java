package com.vertonur.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.bean.User;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.security.Group;
import com.vertonur.service.UserService;

@Controller
public class GroupController {

	@RequestMapping(value = "/admin/groups", method = RequestMethod.GET)
	public String getGroupList(
			@RequestParam(required = false) boolean permissionConfig,
			HttpServletRequest request) {

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		List<Group> groups = null;
		if (permissionConfig)
			groups = groupService.getAdminTopLevelGroups();
		else
			groups = groupService.getTopLevelGroups();
		request.setAttribute("groups", groups);

		return "default/admin/group_list";
	}

	@RequestMapping(value = "/admin/groups/form", method = RequestMethod.GET)
	public String showGroupForm(@RequestParam(defaultValue = "0") int groupId,
			HttpServletRequest request) {

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		List<Group> groups = groupService.getTopLevelGroups();
		request.setAttribute("groups", groups);

		if (groupId != 0) {
			Group group = groupService.getGroupById(groupId);
			if (group.getNestedLevel() == 0)
				groups.remove(group);

			request.setAttribute("edittedGroup", group);
		}

		return "default/admin/group_form";
	}

	@RequestMapping(value = "/admin/groups/{groupId}", method = RequestMethod.GET)
	public String getSubGroupList(@PathVariable int groupId,
			@RequestParam(required = false) String type,
			HttpServletRequest request) {

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		Group group = groupService.getGroupById(groupId);
		request.setAttribute("group", group);

		if ("list".equals(type))
			return "default/admin/sub_group_list";
		else if ("rankingOption".equals(type)) {
			return "default/admin/ranking/ranking_sub_group_option";
		} else if ("userOption".equals(type)) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			UserService userService = new UserService();
			User user = userService.getUserById(userId);
			request.setAttribute("user", user);
			return "default/admin/user_sub_group_option";
		} else
			return "default/admin/sub_group_option";
	}
}
