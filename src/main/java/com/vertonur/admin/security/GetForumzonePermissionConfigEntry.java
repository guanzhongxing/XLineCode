package com.vertonur.admin.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vertonur.security.spring.aop.datasource.PermissionDataSource;
import com.vertonur.bean.Forumzone;
import com.vertonur.pojo.security.Permission;
import com.vertonur.service.ForumzoneService;

public class GetForumzonePermissionConfigEntry extends
		SelectionPermissionConfigEntry {

	private PermissionDataSource dataSource;

	@Override
	public void fullfillEntry(Set<Permission> permissions) {
		ForumzoneService service = new ForumzoneService();
		Set<Permission> allForumzonePermissions = new HashSet<Permission>();
		List<Forumzone> forumzones = service.getForumzones();
		for (Forumzone forumzone : forumzones) {
			Set<Permission> forumzonePermissions = dataSource
					.getPermissionSet(String.valueOf(forumzone.getId()));
			int size = forumzonePermissions.size();
			int counter = 0;
			StringBuilder permissionId = new StringBuilder();
			for (Permission permission : forumzonePermissions) {
				permissionId.append(String.valueOf(permission.getId()));
				counter++;
				if (counter < size)
					permissionId.append("_");
			}

			PermissionConfigOption option = new PermissionConfigOption(
					permissionId.toString(), forumzone.getName());
			if (permissions.containsAll(forumzonePermissions))
				option.setSelected(false);
			addOption(option);

			allForumzonePermissions.addAll(forumzonePermissions);
		}

		if (allForumzonePermissions.size() == 0
				|| permissions.containsAll(allForumzonePermissions))
			setAllowAll(true);
	}

	public PermissionDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(PermissionDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
