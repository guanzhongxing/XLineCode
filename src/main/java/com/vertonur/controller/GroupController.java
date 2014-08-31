package com.vertonur.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.bean.User;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.Assigned2SubGroupException;
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

	@RequestMapping(value = "/admin/groups/form", method = RequestMethod.POST)
	public String createGroup(@Valid Group group,
			@RequestParam int parentGroupId, HttpServletRequest request) {

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		Group parentGroup = groupService.getGroupById(parentGroupId);
		if (parentGroupId == 0) {
			group.setNestedLevel(0);
		} else {
			group.setParent(parentGroup);
			parentGroup.addSubGroup(group);
			group.setNestedLevel(parentGroup.getNestedLevel() + 1);
		}
		groupService.saveGroup(group);

		return "redirect:/admin/groups";
	}

	@RequestMapping(value = "/admin/groups/{groupId}", method = RequestMethod.PUT)
	public String updateGroup(@Valid Group group, @PathVariable int groupId,
			@RequestParam int parentGroupId, HttpServletRequest request)
			throws Assigned2SubGroupException {

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		Group parentGroup = groupService.getGroupById(parentGroupId);
		Group savedGroup = groupService.getGroupById(groupId);
		savedGroup.setName(group.getName());
		savedGroup.setDescription(group.getDescription());
		savedGroup.setGroupType(group.getGroupType());
		Group originalParent = savedGroup.getParent();
		if (parentGroupId == 0) {
			if (originalParent != null)
				originalParent.removeSubGroup(savedGroup);
			savedGroup.setParent(null);
			savedGroup.setNestedLevel(0);
		} else {
			if (originalParent == null) {
				parentGroup.addSubGroup(savedGroup);
				savedGroup.setParent(parentGroup);
				savedGroup.setNestedLevel(parentGroup.getNestedLevel() + 1);
			} else if (originalParent != null
					&& originalParent.getId() != parentGroupId) {
				originalParent.removeSubGroup(savedGroup);
				parentGroup.addSubGroup(savedGroup);
				savedGroup.setParent(parentGroup);
				savedGroup.setNestedLevel(parentGroup.getNestedLevel() + 1);
			}
		}

		groupService.updateGroup(savedGroup);

		return "redirect:/admin/groups";
	}

	@RequestMapping(value = "/admin/groups", method = RequestMethod.DELETE)
	public String deleteGroups(@RequestParam int[] groupIds,
			HttpServletRequest request) {

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		for (int id : groupIds)
			groupService.deleteGroup(id);

		return "redirect:/admin/groups";
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
