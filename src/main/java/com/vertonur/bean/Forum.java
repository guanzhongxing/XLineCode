package com.vertonur.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.vertonur.pojo.Category;

public class Forum {

	private Category category;
	private List<Topic> recentTopics;
	private List<Topic> hotTopics;

	public Forum() {
		category = new Category();
	}

	public Forum(Category category) {
		this.category = category;
	}

	public void addAdmins(Set<Admin> admins) {
		Iterator<Admin> iterator = admins.iterator();
		Set<com.vertonur.pojo.Admin> coreAdmins = new HashSet<com.vertonur.pojo.Admin>();
		while (iterator.hasNext()) {
			Admin admin = iterator.next();
			coreAdmins.add(admin.getCore());
		}
		category.addAdmins(coreAdmins);
	}

	public Set<Admin> getAdmins() {
		Set<com.vertonur.pojo.Admin> coreAdmins = category.getAdmins();
		Iterator<com.vertonur.pojo.Admin> iterator = coreAdmins.iterator();
		Set<Admin> admins = new HashSet<Admin>();
		while (iterator.hasNext()) {
			com.vertonur.pojo.Admin coreAdmin = iterator.next();
			Admin admin = new Admin(coreAdmin);
			admins.add(admin);
		}
		return admins;
	}

	public Category getCore() {
		return category;
	}

	public String getDescription() {
		return category.getDescription();
	}

	public Forumzone getForumzone() {
		return new Forumzone(category.getDepartment());
	}

	public int getId() {
		return category.getId();
	}

	public String getName() {
		return category.getName();
	}

	public ForumStatistician getStatistician() {
		return new ForumStatistician(category.getStatistician());
	}

	public boolean isHasNewTopic() {
		return category.isHasNewInfo();
	}

	public void removeAdmins() {
		category.removeAdmins();
	}

	public void setCreatedTime(Date date) {
		category.setCreatedTime(date);
	}

	public void setCreator(Admin admin) {
		category.setCreator(admin.getCore());
	}

	public void setDescription(String description) {
		category.setDescription(description);
	}

	public void setName(String name) {
		category.setName(name);
	}

	public void setStatistician(ForumStatistician forumStatistician) {
		category.setStatistician(forumStatistician.getCore());
	}

	public boolean isModerated() {
		return category.isModerated();
	}

	public void setModerated(boolean moderated) {
		category.setModerated(moderated);
	}

	public boolean isDeprecated() {
		return category.isDeprecated();
	}

	public void setDeprecated(boolean deprecated) {
		category.setDeprecated(deprecated);
	}

	public List<Topic> getRecentTopics() {
		return recentTopics;
	}

	public void setRecentTopics(List<Topic> recentTopics) {
		this.recentTopics = recentTopics;
	}

	public List<Topic> getHotTopics() {
		return hotTopics;
	}

	public void setHotTopics(List<Topic> hotTopics) {
		this.hotTopics = hotTopics;
	}

	public int getPriority() {
		return category.getPriority();
	}

	public void setPriority(int priority) {
		category.setPriority(priority);
	}
}
