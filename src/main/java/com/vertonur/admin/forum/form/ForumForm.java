package com.vertonur.admin.forum.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

public class ForumForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	private int forumId;
	private String[] forumIds;
	private String forumName;
	private String forumDescription;
	private int newForumzoneId;
	private int forumzoneId;
	private boolean moderated;
	private int priority;

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getForumDescription() {
		return forumDescription;
	}

	public void setForumDescription(String forumDescription) {
		this.forumDescription = forumDescription;
	}

	public boolean isModerated() {
		return moderated;
	}

	public void setModerated(boolean moderated) {
		this.moderated = moderated;
	}

	public String[] getForumIds() {
		return forumIds;
	}

	public void setForumIds(String[] forumIds) {
		this.forumIds = forumIds;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = super.validate(arg0, arg1);
		if (priority < 0)
			errors.add("priority", new ActionMessage(
					"error.form.positive.integer.required"));
		else if (priority == 0)
			errors.add("priority", new ActionMessage(
					"error.form.field.required"));

		if (errors != null)
			return errors;
		return null;
	}

	public int getNewForumzoneId() {
		return newForumzoneId;
	}

	public void setNewForumzoneId(int newForumzoneId) {
		this.newForumzoneId = newForumzoneId;
	}

	public int getForumzoneId() {
		return forumzoneId;
	}

	public void setForumzoneId(int forumzoneId) {
		this.forumzoneId = forumzoneId;
	}
}