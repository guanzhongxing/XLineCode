package com.vertonur.admin.security;

public class PermissionConfigOption {

	private String id;
	private String name;
	private boolean selected = true;

	public PermissionConfigOption(String id) {
		this.id = id;
	}

	public PermissionConfigOption(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
