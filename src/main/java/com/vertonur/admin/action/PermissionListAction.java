package com.vertonur.admin.action;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.admin.security.PermissionConfigSection;
import com.vertonur.admin.security.PermissionConfigSessionManager;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.pojo.security.Group;

public class PermissionListAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		saveToken(request);
		int groupId = Integer.parseInt(request.getParameter("groupId"));
		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		Group group = groupService.getGroupById(groupId);
		request.setAttribute("group", group);

		String permissionType = request.getParameter("permissionType");
		String managerName = "permissionConfigSessionManager";
		if ("backend".equals(permissionType))
			managerName = "backendPermissionConfigSessionManager";
		ServletContext context = this.getServlet().getServletContext();
		WebApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		PermissionConfigSessionManager permissionConfigSessionManager = (PermissionConfigSessionManager) appContext
				.getBean(managerName);
		Set<PermissionConfigSection> sections = permissionConfigSessionManager
				.getSections(group);
		request.setAttribute("sections", sections);

		return mapping.findForward("PermissionListPage");
	}
}
