package com.vertonur.admin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class GroupConfigForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916893792232030466L;
	private int groupId;
	private int[] groupIds;
	private String groupName;
	private String groupDesc;
	private int parentGroupId;
	private String groupType;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public int getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(int parentGroupId) {
		this.parentGroupId = parentGroupId;
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

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = super.validate(arg0, arg1);
		String action = arg1.getParameter("action");
		// disable validation for deletion of groups
		if ("delete".equals(action))
			return null;

		if (errors != null)
			return errors;
		return null;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
}
