package com.vertonur.admin.security;

import java.util.HashSet;
import java.util.Set;

public abstract class SelectionPermissionConfigEntry extends
		PermissionConfigEntry {

	private boolean multiple = false;
	private boolean allowAll = false;
	private Set<PermissionConfigOption> options = new HashSet<PermissionConfigOption>();

	public SelectionPermissionConfigEntry() {
		setType(Type.SELECTION);
	}

	public boolean isAllowAll() {
		return allowAll;
	}

	public void setAllowAll(boolean allowAll) {
		this.allowAll = allowAll;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public Set<PermissionConfigOption> getOptions() {
		return options;
	}

	public void setOptions(Set<PermissionConfigOption> options) {
		this.options = options;
	}

	public void addOption(PermissionConfigOption option) {
		options.add(option);
	}
}
