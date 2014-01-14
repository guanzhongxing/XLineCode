package com.vertonur.admin.security;

import java.util.Set;

import com.vertonur.pojo.security.Permission;

public abstract class PermissionConfigEntry {

	public enum Type {
		SELECTION, CHECKBOX
	}

	private String name;
	private String desc;
	private Type type;

	/**
	 * Fullfill selection options of this entry.
	 * 
	 * @param permissions
	 *            permissions within a group
	 */
	public abstract void fullfillEntry(Set<Permission> permissions);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Type getType() {
		return type;
	}

	protected void setType(Type type) {
		this.type = type;
	}
}
