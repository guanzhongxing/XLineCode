package com.vertonur.admin.forumzone.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

public class ForumzoneForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	private int forumzoneId;
	private int[] forumzoneIds;
	private String forumzoneName;
	private String forumzoneDescription;
	private boolean moderate;
	private int priority;

	public void setForumzoneId(int forumzoneId) {
		this.forumzoneId = forumzoneId;
	}

	public int getForumzoneId() {
		return forumzoneId;
	}

	public void setForumzoneName(String forumzoneName) {
		this.forumzoneName = forumzoneName;
	}

	public String getForumzoneName() {
		return forumzoneName;
	}

	public void setForumzoneDescription(String forumzoneDescription) {
		this.forumzoneDescription = forumzoneDescription;
	}

	public String getForumzoneDescription() {
		return forumzoneDescription;
	}

	public int[] getForumzoneIds() {
		return forumzoneIds;
	}

	public void setForumzoneIds(int[] forumzoneIds) {
		this.forumzoneIds = forumzoneIds;
	}

	public boolean isModerate() {
		return moderate;
	}

	public void setModerate(boolean moderate) {
		this.moderate = moderate;
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
}
