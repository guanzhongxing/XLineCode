package com.vertonur.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vertonur.admin.form.PermissionConfigForm;
import com.vertonur.admin.security.PermissionConfigSection;
import com.vertonur.admin.security.PermissionConfigSessionManager;
import com.vertonur.bean.Forum;
import com.vertonur.bean.Forumzone;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.PermissionService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.Assigned2SubGroupException;
import com.vertonur.pojo.security.Group;
import com.vertonur.pojo.security.Group.GroupType;
import com.vertonur.pojo.security.Permission;
import com.vertonur.security.spring.aop.datasource.PermissionDataSource;
import com.vertonur.service.ForumService;
import com.vertonur.service.ForumzoneService;

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

	@RequestMapping(value = "/admin/permissions/{groupId}", method = RequestMethod.PUT)
	public String updateGroupPermissionConfig(@PathVariable int groupId,
			PermissionConfigForm form, HttpServletRequest request)
			throws Assigned2SubGroupException {

		SystemContextService systemContextService = SystemContextService
				.getService();
		GroupService groupService = systemContextService
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		Group group = groupService.getGroupById(groupId);

		ServletContext context = request.getServletContext();
		WebApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		PermissionService permissionService = systemContextService
				.getDataManagementService(ServiceEnum.PERMISSION_SERVICE);
		String permissionType = form.getPermissionType();
		int[] forumzonePermissionIds = form.getForumzonePermissionIds();
		int[] forumPermissionIds = form.getForumPermissionIds();
		if ("backend".equals(permissionType)) {
			processBackendPermissions(appContext, permissionService,
					groupService, form.getGroupPermissionIds(), group,
					"validateOperateGroupPermissionDataSource");
			processBackendPermissions(appContext, permissionService,
					groupService, form.getUserPermissionIds(), group,
					"validateOperateUserPermissoinDataSource");
			processBackendPermissions(appContext, permissionService,
					groupService, form.getRuntimeParameterPermissionIds(),
					group, "validateConfigRuntimeParameterPermissionDataSource");
			processBackendPermissions(appContext, permissionService,
					groupService, form.getRankingPermissionIds(), group,
					"validateOperateRankingPermissionDataSource");
			processBackendPermissions(appContext, permissionService,
					groupService, form.getPointsPermissionIds(), group,
					"validateConfigPointsPermissionDataSource");

			int id = 0;
			if (forumzonePermissionIds != null)
				id = forumzonePermissionIds[0];
			processBackendPermissions(appContext, permissionService,
					groupService, id, group,
					"validateOperateForumzonePermissionDataSource");

			id = 0;
			if (forumPermissionIds != null)
				id = forumPermissionIds[0];
			processBackendPermissions(appContext, permissionService,
					groupService, id, group,
					"validateOperateForumPermissionDataSource");

			return "redirect:/admin/groups?permissionConfig=true";
		} else {
			ForumzoneService forumzoneService = new ForumzoneService();
			List<Forumzone> forumzones = forumzoneService.getForumzones();
			Set<Integer> forumzoneIds = new HashSet<Integer>();
			for (Forumzone forumzone : forumzones)
				forumzoneIds.add(forumzone.getId());

			ForumService forumService = new ForumService();
			List<Forum> forums = forumService.getForums();
			Set<Integer> forumIds = new HashSet<Integer>();
			for (Forum forum : forums)
				forumIds.add(forum.getId());

			processPermissions(appContext, permissionService, forumzoneIds,
					forumzonePermissionIds, group,
					"readForumzonePermissionDataSource");

			processPermissions(appContext, permissionService, forumIds,
					forumPermissionIds, group, "readForumPermissionDataSource");

			GroupType type = group.getGroupType();
			if (type.equals(GroupType.GENERIC_GST)) {
				int[] annoymousRspPermissionIds = form
						.getAnnoymousRspPermissionIds();
				processPermissions(appContext, permissionService, forumIds,
						annoymousRspPermissionIds, group,
						"annoymousCmtPermissionDataSource");
			}

			int[] replyOnlyPermissionIds = form.getReplyOnlyPermissionIds();
			processPermissions(appContext, permissionService, forumIds,
					replyOnlyPermissionIds, group,
					"saveTopicPermissionDataSource");

			String[] readOnlyPermissionIds = form.getReadOnlyPermissionIds();
			int length = readOnlyPermissionIds.length;
			List<Integer> permissionIdList = new ArrayList<Integer>();
			for (int i = 0; i < length; i++) {
				String[] permissionIdStrs = readOnlyPermissionIds[i].split("_");
				for (int j = 0; j < permissionIdStrs.length; j++)
					permissionIdList.add(Integer.parseInt(permissionIdStrs[j]));
			}
			int size = permissionIdList.size();
			int[] permissionIds = new int[size];
			for (int i = 0; i < size; i++)
				permissionIds[i] = permissionIdList.get(i);
			processPermissions(appContext, permissionService, forumIds,
					permissionIds, group, "readOnlyForumPermissionDataSource");

			int[] downloadAttmPermissionIds = form
					.getDownloadAttmPermissionIds();
			processPermissions(appContext, permissionService, forumIds,
					downloadAttmPermissionIds, group,
					"downloadAttmPermissionDataSource");

			int[] uploadAttmPermissionIds = form.getUploadAttmPermissionIds();
			processPermissions(appContext, permissionService, forumIds,
					uploadAttmPermissionIds, group,
					"uploadAttmPermissionDataSource");

			if (group.getGroupType().equals(GroupType.GENERIC_MDR)) {
				int[] auditContentPermissionIds = form
						.getAuditContentPermissionIds();
				processPermissions(appContext, permissionService, forumIds,
						auditContentPermissionIds, group,
						"auditContentPermissionDataSource");

				int[] moderateContentPermissionIds = form
						.getModerateContentPermissionIds();
				processPermissions(appContext, permissionService, forumIds,
						moderateContentPermissionIds, group,
						"moderateContentPermissionDataSource");

				int[] editContentPermissionIds = form
						.getEditContentPermissionIds();
				processPermissions(appContext, permissionService, forumIds,
						editContentPermissionIds, group,
						"editContentPermissionDataSource");

				int[] moveTopicPermissionIds = form.getMoveTopicPermissionIds();
				processPermissions(appContext, permissionService, forumIds,
						moveTopicPermissionIds, group,
						"moveTopicPermissionDataSource");

				int[] topicLockPermissionIds = form.getTopicLockPermissionIds();
				processPermissions(appContext, permissionService, forumIds,
						topicLockPermissionIds, group,
						"topicLockPermissionDataSource");

				int[] removeContentPermissionIds = form
						.getRemoveContentPermissionIds();
				processPermissions(appContext, permissionService, forumIds,
						removeContentPermissionIds, group,
						"removeContentPermissionDataSource");
			}
			groupService.updateGroup(group);
			return "redirect:/admin/groups";
		}
	}

	private void processPermissions(WebApplicationContext appContext,
			PermissionService permissionService, Set<Integer> ids,
			int[] selectedPermissionIds, Group group, String datasourceName) {
		Set<Permission> allPermissions = new HashSet<Permission>();
		PermissionDataSource permissionDataSource = (PermissionDataSource) appContext
				.getBean(datasourceName);
		for (int id : ids) {
			Set<Permission> permissions = permissionDataSource
					.getPermissionSet(id);
			allPermissions.addAll(permissions);
		}

		Permission permission = null;
		Set<Permission> selectedPermissions = new HashSet<Permission>();
		for (int id : selectedPermissionIds) {
			if (id != -1) {
				permission = permissionService.getPermissionById(id);
				selectedPermissions.add(permission);
			}
		}

		allPermissions.removeAll(selectedPermissions);
		group.removePermissions(selectedPermissions);
		group.addPermissions(allPermissions);
	}

	private void processBackendPermissions(WebApplicationContext appContext,
			PermissionService permissionService, GroupService groupService,
			int id, Group group, String datasourceName) {
		Set<Permission> backendPermissions = group.getBackendPermissions();
		if (id != 0) {
			Permission permission = permissionService.getPermissionById(id);
			backendPermissions.add(permission);
		} else {
			PermissionDataSource permissionDataSource = (PermissionDataSource) appContext
					.getBean(datasourceName);
			Set<Permission> permissions = permissionDataSource
					.getPermissionSet("mock");
			backendPermissions.removeAll(permissions);
		}
		groupService.updateBackendPermissions(group, backendPermissions);
	}
}
