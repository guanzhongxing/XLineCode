package com.vertonur.admin.form;

import org.apache.struts.action.ActionForm;

public class MoveTopicForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1954724458438275454L;
	private int topicId;
	private int newForumId;
	private String reason;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getNewForumId() {
		return newForumId;
	}

	public void setNewForumId(int newForumId) {
		this.newForumId = newForumId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
