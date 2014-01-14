package com.vertonur.bean;

public class UserMsgStatistician {

	private com.vertonur.pojo.statistician.UserMsgStatistician statistician;

	public UserMsgStatistician() {
		this.statistician = new com.vertonur.pojo.statistician.UserMsgStatistician();
	}

	public UserMsgStatistician(
			com.vertonur.pojo.statistician.UserMsgStatistician statistician) {
		this.statistician = statistician;
	}

	public com.vertonur.pojo.statistician.UserMsgStatistician getCore() {
		return statistician;
	}

	public int getId() {
		return statistician.getId();
	}

	public int getResponseNum() {
		return statistician.getCommentNum();
	}

	public int getTopicNum() {
		return statistician.getInfoNum();
	}

	public User getUser() {
		return new User(statistician.getUser());
	}

	public void setId(int id) {
		statistician.setId(id);
	}

	public void setUser(User user) {
		statistician.setUser(user.getCore());
	}
}
