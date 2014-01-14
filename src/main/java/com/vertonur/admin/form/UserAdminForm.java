package com.vertonur.admin.form;

import org.apache.struts.action.ActionForm;

public class UserAdminForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7076931671782587582L;

	private int userId;
	private int[] userIds;
	private int groupId;
	private int[] groupIds;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int[] getUserIds() {
		return userIds;
	}

	public void setUserIds(int[] userIds) {
		this.userIds = userIds;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(int[] groupIds) {
		this.groupIds = groupIds;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
