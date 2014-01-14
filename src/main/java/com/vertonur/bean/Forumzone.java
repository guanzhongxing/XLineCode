package com.vertonur.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.vertonur.pojo.Department;
import com.vertonur.service.ForumService;

public class Forumzone {

	private Department department;

	public Forumzone() {
		department = new Department();
	}

	public Forumzone(Department department) {
		this.department = department;
	}

	public void addAdmins(Set<Admin> admins) {
		Set<com.vertonur.pojo.Admin> coreAdmins = new HashSet<com.vertonur.pojo.Admin>();
		Iterator<Admin> iterator = admins.iterator();
		while (iterator.hasNext()) {
			Admin admin = iterator.next();
			coreAdmins.add(admin.getCore());
		}
		department.addAdmins(coreAdmins);
	}

	public Department getCore() {
		return department;
	}

	public String getDescription() {
		return department.getDescription();
	}

	public List<Forum> getForums() {
		ForumService service = new ForumService();
		return service.getForumzoneForums(getId());
	}

	public int getId() {
		return department.getId();
	}

	public String getName() {
		return department.getName();
	}

	public void removeAdmin(Admin castedAdmin) {
		department.removeAdmin(castedAdmin.getCore());
	}

	public void setCreatedTime(Date date) {
		department.setCreatedTime(date);
	}

	public void setCreator(Admin admin) {
		department.setCreator(admin.getCore());
	}

	public void setDescription(String forumzoneDescription) {
		department.setDescription(forumzoneDescription);
	}

	public void setName(String forumzoneName) {
		department.setName(forumzoneName);
	}

	public boolean isDeprecated() {
		return department.isDeprecated();
	}

	public void setDeprecated(boolean deprecated) {
		department.setDeprecated(deprecated);
	}

	public boolean isModerated() {
		return department.isModerated();
	}

	public void setModerated(boolean moderated) {
		department.setModerated(moderated);
	}

	public int getPriority() {
		return department.getPriority();
	}

	public void setPriority(int priority) {
		department.setPriority(priority);
	}
}
