package com.vertonur.bean;

import java.util.Date;

import com.vertonur.pojo.UserReadInfo;

public class UserReadTopic {

	private UserReadInfo readInfo;

	public UserReadTopic(User theCurrentUser, Topic topic, Date date) {
		readInfo = new UserReadInfo(theCurrentUser.getCore(),
				(com.vertonur.pojo.Info) topic.getCore(), date);
	}

	public UserReadTopic(UserReadInfo readInfo) {
		this.readInfo = readInfo;
	}

	public UserReadInfo getCore() {
		return readInfo;
	}

	public int getId() {
		return readInfo.getId();
	}

	public Topic getReadTopic() {
		return new Topic(readInfo.getReadInfo());
	}
}
