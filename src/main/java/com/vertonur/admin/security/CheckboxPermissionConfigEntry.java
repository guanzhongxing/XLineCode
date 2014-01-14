package com.vertonur.admin.security;

public abstract class CheckboxPermissionConfigEntry extends
		PermissionConfigEntry {

	private PermissionConfigOption option;

	public CheckboxPermissionConfigEntry() {
		setType(Type.CHECKBOX);
	}

	public PermissionConfigOption getOption() {
		return option;
	}

	public void setOption(PermissionConfigOption option) {
		this.option = option;
	}
}
