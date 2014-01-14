package com.vertonur.bean;

import com.vertonur.pojo.Info.InfoType;

public class Topic extends Info {

	private com.vertonur.pojo.Info info;

	public Topic() {
		info = new com.vertonur.pojo.Info();
	}

	public Topic(com.vertonur.pojo.Info info) {
		this.info = info;
	}

	@Override
	public com.vertonur.pojo.Info getCore() {
		return info;
	}

	public Forum getForum() {
		return new Forum(info.getCategory());
	}

	public TopicStatistician getStatistician() {
		return new TopicStatistician(info.getStatistician());
	}

	public boolean isNewToUser() {
		return info.isNewToUser();
	}

	public boolean isReadToUser() {
		return info.isReadToUser();
	}

	public void setNewToUser(boolean newToUser) {
		info.setNewToUser(newToUser);
	}

	public void setReadToUser(boolean readToUser) {
		info.setReadToUser(readToUser);
	}

	public void setStatistician(TopicStatistician statistician) {
		info.setStatistician(statistician.getCore());
	}

	public boolean isModerated() {
		return info.isModerated();
	}

	public void setForum(Forum forum) {
		info.setCategory(forum.getCore());
	}

	public InfoType getInfoType() {
		return info.getInfoType();
	}

	public void setInfoType(InfoType infoType) {
		info.setInfoType(infoType);
	}

	public boolean isLocked() {
		return info.isLocked();
	}
	
	public boolean isHot() {
		return info.isHot();
	}
}
