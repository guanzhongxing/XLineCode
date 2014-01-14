package com.vertonur.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.bean.Admin;
import com.vertonur.bean.Topic;
import com.vertonur.bean.User;
import com.vertonur.bean.UserMsgStatistician;
import com.vertonur.bean.UserReadTopic;
import com.vertonur.pojo.UserReadInfo;
import com.vertonur.util.ForumCommonUtil;

public class UserService {

	private com.vertonur.dms.UserService service;

	public UserService() {
		service = SystemContextService.getService().getDataManagementService(
				ServiceEnum.USER_SERVICE);
	}

	public User getUserById(int userId) {
		com.vertonur.pojo.User user = service.getUserById(userId);
		if (user instanceof com.vertonur.pojo.Admin)
			return new Admin(user);

		return new User(user);
	}

	public void updateUser(User theCurrentUser) {
		service.updateUser(theCurrentUser.getCore());
	}

	public Admin getAdminById(int adminId) {
		return new Admin(service.getAdminById(adminId));
	}

	public boolean isReadTopic(User user, Topic topic) {
		return service.isReadInfo(user.getCore(),
				(com.vertonur.pojo.Info) topic.getCore());
	}

	public long getUserNum() {
		return service.getUserNum();
	}

	public User getLatestUser() {
		return new User(service.getLatestUser());
	}

	public List<Admin> getAdmins() {
		List<Admin> admins = new ArrayList<Admin>();
		List<com.vertonur.pojo.Admin> coreAdmins = service.getAdmins();
		coreAdmins.size();
		int size = coreAdmins.size();
		for (int i = 0; i < size; i++) {
			Admin admin = new Admin(coreAdmins.get(i));
			admins.add(admin);
		}
		return admins;
	}

	public boolean deleteUser(User user) {
		return service.deleteUser(user.getCore());
	}

	public Set<UserReadTopic> getReadTopics(User theCurrentUser) {
		Set<UserReadTopic> readTopics = new HashSet<UserReadTopic>();
		Set<UserReadInfo> readInfos = service.getReadInfos(theCurrentUser
				.getCore());
		Iterator<UserReadInfo> iterator = readInfos.iterator();
		while (iterator.hasNext()) {
			UserReadInfo readInfo = iterator.next();
			UserReadTopic readTopic = new UserReadTopic(readInfo);
			readTopics.add(readTopic);
		}
		return readTopics;
	}

	public List<User> getUsers(int start) {
		List<com.vertonur.pojo.User> coreUsers = service.getUsers(start);
		List<User> users = new ArrayList<User>();
		int size = coreUsers.size();
		for (int i = 0; i < size; i++) {
			User user = new User(coreUsers.get(i));
			users.add(user);
		}
		return users;
	}

	public void lockUser(User user) {
		service.lockUser(user.getCore());
	}

	public void unLockUser(User user) {
		service.unLockUser(user.getCore());
	}

	public List<User> getUsersByNameAndGroupId_AM(String username, int start,
			int groupId) {
		List<com.vertonur.pojo.User> users = service.getUsersByNameAndGroupId_AM(
				username, start, groupId);
		return ForumCommonUtil.convertCoreUsersToUsers(users);
	}

	public List<User> getUsersByName_EM(String userName) {
		List<com.vertonur.pojo.User> users = service.getUsersByName_EM(userName);
		return ForumCommonUtil.convertCoreUsersToUsers(users);
	}

	public long getUserNumByNameAndGroupId_AM(String userName, int groupId) {
		return service.getUserNumByNameAndGroupId_AM(userName, groupId);
	}

	public void activateUser(int id) {
		service.activateUser(id);
	}

	public void updateUserStatistician(int userId,
			UserMsgStatistician statistician) {
		service.updateUserStatistician(userId, statistician.getCore());
	}
}
