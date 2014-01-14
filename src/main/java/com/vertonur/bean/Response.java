package com.vertonur.bean;

import com.vertonur.pojo.Comment;

public class Response extends Info {

	private Comment comment;

	public Response() {
		comment = new Comment();
	}

	public Response(Comment comment) {
		this.comment = comment;
	}

	public Comment getCore() {
		return comment;
	}

	public Topic getTopic() {
		return new Topic(comment.getInfo());
	}

	public void setLatestOne(boolean isLatestOne) {
		comment.setLatestOne(isLatestOne);
	}

	public void setTopic(Topic topic) {
		comment.setInfo((com.vertonur.pojo.Info) topic.getCore());
	}
}
