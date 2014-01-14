package com.vertonur.admin.security;

import java.util.Set;

import com.vertonur.pojo.security.Group.GroupType;
import com.vertonur.pojo.security.Permission;

public class PermissionConfigSection {

	private String desc;
	private boolean displayed = true;
	private GroupType belongGroupType = GroupType.GENERIC_USR;
	private Set<PermissionConfigEntry> entries;

	public void fullfillEntries(Set<Permission> permissions) {
		for (PermissionConfigEntry entry : getEntries())
			entry.fullfillEntry(permissions);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isDisplayed() {
		return displayed;
	}

	public void setDisplayed(boolean displayed) {
		this.displayed = displayed;
	}

	public GroupType getBelongGroupType() {
		return belongGroupType;
	}

	public void setBelongGroupType(GroupType belongGroupType) {
		this.belongGroupType = belongGroupType;
	}

	public Set<PermissionConfigEntry> getEntries() {
		return entries;
	}

	public void setEntries(Set<PermissionConfigEntry> entries) {
		this.entries = entries;
	}
}
