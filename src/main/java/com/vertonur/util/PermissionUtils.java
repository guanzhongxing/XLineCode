/**
 * 
 */
package com.vertonur.util;

import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vertonur.bean.Topic;
import com.vertonur.pojo.security.Permission;
import com.vertonur.security.spring.aop.datasource.PermissionDataSource;

public class PermissionUtils {

	public static boolean checkAttachmentUploadPermission(
			ServletContext servletContext, int forumId) {

		return checkPermission(servletContext, forumId,
				"validateUploadAttmPermissionDataSource");
	}

	public static boolean checkAttachmentDownloadPermission(
			ServletContext servletContext, int forumId) {

		return checkPermission(servletContext, forumId,
				"validateDownloadAttmPermissionDataSource");
	}

	public static boolean checkResponsePostPermission(
			ServletContext servletContext, Topic topic) {
		WebApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		PermissionDataSource dataSource = (PermissionDataSource) appContext
				.getBean("validateSaveRspPermissionDataSource");
		Set<Permission> permissions = dataSource.getPermissionSet(topic
				.getCore());

		return com.vertonur.security.util.PermissionUtils
				.checkPermissions(permissions);
	}

	public static boolean checkTopicPostPermission(
			ServletContext servletContext, int forumId) {

		return checkPermission(servletContext, forumId,
				"validateSaveTopicPermissionDataSource");
	}

	public static boolean checkOperateGroupPermission(
			ServletContext servletContext) {

		return checkPermission(servletContext, 0,
				"validateOperateGroupPermissionDataSource");
	}

	public static boolean checkOperateUserPermission(
			ServletContext servletContext) {

		return checkPermission(servletContext, 0,
				"validateOperateUserPermissoinDataSource");
	}

	public static boolean checkOperateForumzonePermission(
			ServletContext servletContext) {

		return checkPermission(servletContext, 0,
				"validateOperateForumzonePermissionDataSource");
	}

	public static boolean checkOperateForumPermission(
			ServletContext servletContext) {

		return checkPermission(servletContext, 0,
				"validateOperateForumPermissionDataSource");
	}

	public static boolean checkConfigRuntimeParameterPermission(
			ServletContext servletContext) {

		return checkPermission(servletContext, 0,
				"validateConfigRuntimeParameterPermissionDataSource");
	}

	public static boolean checkOperateRankingPermission(
			ServletContext servletContext) {

		return checkPermission(servletContext, 0,
				"validateOperateRankingPermissionDataSource");
	}

	public static boolean checkConfigPointsPermission(
			ServletContext servletContext) {

		return checkPermission(servletContext, 0,
				"validateConfigPointsPermissionDataSource");
	}

	public static boolean checkConfigBackendPermissionPermission(
			ServletContext servletContext) {

		return checkPermission(servletContext, 0,
				"validateConfigBackendPermissionPermissionDataSource");
	}

	public static boolean checkModeratorEditionPermission(
			ServletContext servletContext, int forumId) {

		return checkPermission(servletContext, forumId,
				"validateModifyContentPermissionDataSource");
	}

	public static boolean checkModeratorDeletionPermission(
			ServletContext servletContext, int forumId) {

		return checkPermission(servletContext, forumId,
				"validateDeleteContentPermissionDataSource");
	}

	public static boolean checkMoveTopicPermission(
			ServletContext servletContext, int forumId) {

		return checkPermission(servletContext, forumId,
				"validateMoveTopicPermissionDataSource");
	}

	public static boolean checkTopicLockPermission(
			ServletContext servletContext, int forumId) {

		return checkPermission(servletContext, forumId,
				"validateTopicLockPermissionDataSource");
	}

	private static boolean checkPermission(ServletContext servletContext,
			int forumId, String dataSourceName) {
		WebApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		PermissionDataSource dataSource = (PermissionDataSource) appContext
				.getBean(dataSourceName);
		Set<Permission> permissions = dataSource.getPermissionSet(forumId);

		return com.vertonur.security.util.PermissionUtils
				.checkPermissions(permissions);
	}
}
