package com.vertonur.admin.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vertonur.bean.Forum;
import com.vertonur.pojo.security.Permission;
import com.vertonur.security.spring.aop.datasource.PermissionDataSource;
import com.vertonur.service.ForumService;

public class ForumPermissionConfigEntry extends SelectionPermissionConfigEntry {

	private PermissionDataSource dataSource;

	@Override
	public void fullfillEntry(Set<Permission> permissions) {
		ForumService service = new ForumService();
		Set<Permission> allForumPermissions = new HashSet<Permission>();
		List<Forum> forums = service.getForums();
		for (Forum forum : forums) {
			Set<Permission> forumPermissions = dataSource
					.getPermissionSet(forum.getId());
			int size = forumPermissions.size();
			int counter = 0;
			StringBuilder permissionId = new StringBuilder();
			for (Permission permission : forumPermissions) {
				permissionId.append(String.valueOf(permission.getId()));
				counter++;
				if (counter < size)
					permissionId.append("_");
			}

			PermissionConfigOption option = new PermissionConfigOption(
					permissionId.toString(), forum.getName());
			if (permissions.containsAll(forumPermissions))
				option.setSelected(false);
			addOption(option);

			allForumPermissions.addAll(forumPermissions);
		}

		if (allForumPermissions.size() == 0
				|| permissions.containsAll(allForumPermissions))
			setAllowAll(true);
	}

	public PermissionDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(PermissionDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
