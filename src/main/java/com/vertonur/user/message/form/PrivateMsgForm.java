package com.vertonur.user.message.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class PrivateMsgForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String subject;
	private String content;
	private String userId;
	private boolean attachSig = false;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setAttachSig(boolean attachSig) {
		this.attachSig = attachSig;
	}

	public boolean isAttachSig() {
		return attachSig;
	}
	
	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = super.validate(arg0, arg1);

		if (errors != null)
			return errors;
		return null;
	}
}
