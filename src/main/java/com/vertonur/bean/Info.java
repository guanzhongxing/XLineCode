package com.vertonur.bean;

import java.util.Date;
import java.util.List;

import com.vertonur.pojo.AbstractInfo;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.State;

public abstract class Info {

	public List<Attachment> getAttachments() {
		return getCore().getAttachments();
	}

	public User getAuthor() {
		return new User(getCore().getAuthor());
	}

	public String getContent() {
		return getCore().getContent();
	}

	public abstract AbstractInfo getCore();

	public Date getCreatedTime() {
		return getCore().getCreatedTime();
	}

	public int getId() {
		return getCore().getId();
	}

	public String getSubject() {
		return getCore().getSubject();
	}

	public void setAuthor(User theCurrentUser) {
		getCore().setAuthor(theCurrentUser.getCore());
	}

	public void setContent(String content) {
		getCore().setContent(content);
	}

	public void setCreatedTime(Date date) {
		getCore().setCreatedTime(date);
	}

	public void setSubject(String title) {
		getCore().setSubject(title);
	}

	public State getState() {
		return getCore().getState();
	}

	public void setState(State state) {
		getCore().setState(state);
	}
}
