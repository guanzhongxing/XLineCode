package com.vertonur.admin.security;

import java.util.Set;

import com.vertonur.pojo.security.Permission;
import com.vertonur.security.spring.aop.datasource.PermissionDataSource;

public class FixedValuePermissionConfigEntry extends
		CheckboxPermissionConfigEntry {

	private PermissionDataSource dataSource;

	@Override
	public void fullfillEntry(Set<Permission> permissions) {
		// for fixed value data source,there is no need to pass in a proprietary
		// mark
		Set<Permission> requiredPermissions = dataSource
				.getPermissionSet("mock");
		Permission requiredPermission = requiredPermissions.iterator().next();
		PermissionConfigOption option = new PermissionConfigOption(
				String.valueOf(requiredPermission.getId()));

		if (permissions.contains(requiredPermission))
			option.setSelected(true);
		else
			option.setSelected(false);
		setOption(option);
	}

	public PermissionDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(PermissionDataSource dataSource) {
		this.dataSource = dataSource;
	}
}
