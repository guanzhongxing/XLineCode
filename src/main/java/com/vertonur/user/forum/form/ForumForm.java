package com.vertonur.user.forum.form;

import org.apache.struts.action.ActionForm;

public class ForumForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String forumid = null;

	private String forumname = null;

	private int pageid = 0;

	public ForumForm() {
	}

	public String getForumid() {
		return forumid;
	}

	public void setForumid(String forumid) {
		this.forumid = forumid;
	}

	public String getForumname() {
		return forumname;
	}

	public void setForumname(String forumname) {
		this.forumname = forumname;
	}

	public int getPageid() {
		return pageid;
	}

	public void setPageid(int pageid) {
		this.pageid = pageid;
	}

}
