package com.vertonur.controller;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vertonur.admin.security.PermissionConfigSection;
import com.vertonur.admin.security.PermissionConfigSessionManager;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.security.Group;

@Controller
public class PermissionController {

	@RequestMapping(value = "/admin/permissions/{groupId}", method = RequestMethod.GET)
	public String showPermissionConfigForm(@PathVariable int groupId,
			HttpServletRequest request) {

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		Group group = groupService.getGroupById(groupId);
		request.setAttribute("group", group);

		String permissionType = request.getParameter("permissionType");
		String managerName = "permissionConfigSessionManager";
		if ("backend".equals(permissionType))
			managerName = "backendPermissionConfigSessionManager";
		ServletContext context = request.getServletContext();
		WebApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		PermissionConfigSessionManager permissionConfigSessionManager = (PermissionConfigSessionManager) appContext
				.getBean(managerName);
		Set<PermissionConfigSection> sections = permissionConfigSessionManager
				.getSections(group);
		request.setAttribute("sections", sections);

		return "default/admin/group_security_form";
	}
}
