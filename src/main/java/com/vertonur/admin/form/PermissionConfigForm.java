package com.vertonur.admin.form;

import org.apache.struts.action.ActionForm;

public class PermissionConfigForm extends ActionForm {

	private static final long serialVersionUID = -7612610857457196355L;
	private int groupId;
	private int[] forumzonePermissionIds;
	private int[] forumPermissionIds;
	private int[] annoymousRspPermissionIds;
	private int[] replyOnlyPermissionIds;
	private String[] readOnlyPermissionIds;
	private int[] uploadAttmPermissionIds;
	private int[] downloadAttmPermissionIds;
	private int[] moveTopicPermissionIds;
	private int[] topicLockPermissionIds;
	private int groupPermissionIds;
	private String permissionType;

	public int getGroupPermissionIds() {
		return groupPermissionIds;
	}

	public void setGroupPermissionIds(int groupPermissionIds) {
		this.groupPermissionIds = groupPermissionIds;
	}

	public int getUserPermissionIds() {
		return userPermissionIds;
	}

	public void setUserPermissionIds(int userPermissionIds) {
		this.userPermissionIds = userPermissionIds;
	}

	public int getRuntimeParameterPermissionIds() {
		return runtimeParameterPermissionIds;
	}

	public void setRuntimeParameterPermissionIds(
			int runtimeParameterPermissionIds) {
		this.runtimeParameterPermissionIds = runtimeParameterPermissionIds;
	}

	public int getRankingPermissionIds() {
		return rankingPermissionIds;
	}

	public void setRankingPermissionIds(int rankingPermissionIds) {
		this.rankingPermissionIds = rankingPermissionIds;
	}

	public int getPointsPermissionIds() {
		return pointsPermissionIds;
	}

	public void setPointsPermissionIds(int pointsPermissionIds) {
		this.pointsPermissionIds = pointsPermissionIds;
	}

	private int userPermissionIds;
	private int runtimeParameterPermissionIds;
	private int rankingPermissionIds;
	private int pointsPermissionIds;

	private int[] moderateContentPermissionIds;
	private int[] auditContentPermissionIds;
	private int[] removeContentPermissionIds;
	private int[] editContentPermissionIds;

	public int getGroupId() {
		return groupId;
	}

	public int[] getModerateContentPermissionIds() {
		return moderateContentPermissionIds;
	}

	public void setModerateContentPermissionIds(
			int[] moderateContentPermissionIds) {
		this.moderateContentPermissionIds = moderateContentPermissionIds;
	}

	public int[] getAuditContentPermissionIds() {
		return auditContentPermissionIds;
	}

	public void setAuditContentPermissionIds(int[] auditContentPermissionIds) {
		this.auditContentPermissionIds = auditContentPermissionIds;
	}

	public int[] getRemoveContentPermissionIds() {
		return removeContentPermissionIds;
	}

	public void setRemoveContentPermissionIds(int[] removeContentPermissionIds) {
		this.removeContentPermissionIds = removeContentPermissionIds;
	}

	public int[] getEditContentPermissionIds() {
		return editContentPermissionIds;
	}

	public void setEditContentPermissionIds(int[] editContentPermissionIds) {
		this.editContentPermissionIds = editContentPermissionIds;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int[] getForumzonePermissionIds() {
		return forumzonePermissionIds;
	}

	public void setForumzonePermissionIds(int[] forumzonePermissionIds) {
		this.forumzonePermissionIds = forumzonePermissionIds;
	}

	public int[] getForumPermissionIds() {
		return forumPermissionIds;
	}

	public void setForumPermissionIds(int[] forumPermissionIds) {
		this.forumPermissionIds = forumPermissionIds;
	}

	public int[] getAnnoymousRspPermissionIds() {
		return annoymousRspPermissionIds;
	}

	public void setAnnoymousRspPermissionIds(int[] annoymousRspPermissionIds) {
		this.annoymousRspPermissionIds = annoymousRspPermissionIds;
	}

	public int[] getReplyOnlyPermissionIds() {
		return replyOnlyPermissionIds;
	}

	public void setReplyOnlyPermissionIds(int[] replyOnlyPermissionIds) {
		this.replyOnlyPermissionIds = replyOnlyPermissionIds;
	}

	public String[] getReadOnlyPermissionIds() {
		return readOnlyPermissionIds;
	}

	public void setReadOnlyPermissionIds(String[] readOnlyPermissionIds) {
		this.readOnlyPermissionIds = readOnlyPermissionIds;
	}

	public int[] getUploadAttmPermissionIds() {
		return uploadAttmPermissionIds;
	}

	public void setUploadAttmPermissionIds(int[] uploadAttmPermissionIds) {
		this.uploadAttmPermissionIds = uploadAttmPermissionIds;
	}

	public int[] getDownloadAttmPermissionIds() {
		return downloadAttmPermissionIds;
	}

	public void setDownloadAttmPermissionIds(int[] downloadAttmPermissionIds) {
		this.downloadAttmPermissionIds = downloadAttmPermissionIds;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public int[] getMoveTopicPermissionIds() {
		return moveTopicPermissionIds;
	}

	public void setMoveTopicPermissionIds(int[] moveTopicPermissionIds) {
		this.moveTopicPermissionIds = moveTopicPermissionIds;
	}

	public int[] getTopicLockPermissionIds() {
		return topicLockPermissionIds;
	}

	public void setTopicLockPermissionIds(int[] topicLockPermissionIds) {
		this.topicLockPermissionIds = topicLockPermissionIds;
	}

}
