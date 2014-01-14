package com.vertonur.admin.security;

import java.util.Set;

import com.vertonur.pojo.security.Group;
import com.vertonur.pojo.security.Group.GroupType;
import com.vertonur.pojo.security.Permission;

public class PermissionConfigSessionManager {

	private Set<PermissionConfigSection> sections;

	/**
	 * Hide annoymous post config section when the group type of the selected
	 * group isn't guset
	 * 
	 * @param group
	 * @param permissions
	 */
	public void fullfillSections(Group group) {
		GroupType type = group.getGroupType();
		Set<Permission> permissions = group.getPermissions();
		permissions.addAll(group.getBackendPermissions());
		for (PermissionConfigSection section : sections) {
			GroupType sectionGroupType = section.getBelongGroupType();
			if (sectionGroupType.equals(GroupType.GENERIC_GST)) {
				if (type.equals(GroupType.GENERIC_GST))
					section.fullfillEntries(permissions);
				else
					section.setDisplayed(false);
			} else if (sectionGroupType.equals(GroupType.GENERIC_MDR)) {
				if (type.equals(GroupType.GENERIC_MDR))
					section.fullfillEntries(permissions);
				else
					section.setDisplayed(false);
			} else {
				section.fullfillEntries(permissions);
			}
		}
	}

	public Set<PermissionConfigSection> getSections(Group group) {
		fullfillSections(group);
		return sections;
	}

	public void setSections(Set<PermissionConfigSection> sections) {
		this.sections = sections;
	}
}
