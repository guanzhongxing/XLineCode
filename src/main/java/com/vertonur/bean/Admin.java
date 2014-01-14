package com.vertonur.bean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.vertonur.pojo.Category;
import com.vertonur.pojo.Department;

public class Admin extends User {

	public Admin(com.vertonur.pojo.User admin) {
		super(admin);
	}

	public com.vertonur.pojo.Admin getCore() {
		return (com.vertonur.pojo.Admin) super.getCore();
	}

	public Set<Forum> getForums() {
		Set<Category> categories = ((com.vertonur.pojo.Admin) getCore()).getCategories();
		Iterator<Category> iterator = categories.iterator();
		Set<Forum> forums = new HashSet<Forum>();
		while (iterator.hasNext()) {
			Category Category = iterator.next();
			Forum forum = new Forum(Category);
			forums.add(forum);
		}
		return forums;
	}

	public Set<Forumzone> getForumzones() {
		Set<Department> departments = getCore().getDepartments();
		Iterator<Department> iterator = departments.iterator();
		Set<Forumzone> forumzones = new HashSet<Forumzone>();
		while (iterator.hasNext()) {
			Department department = iterator.next();
			Forumzone forumzone = new Forumzone(department);
			forumzones.add(forumzone);
		}
		return forumzones;
	}
}
