package com.vertonur.user.user.form;

import org.apache.struts.validator.ValidatorForm;

public class ChangePasswordForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9094330799029293429L;
	private int userId;
	private String currentPwd;
	private String newPwd;
	private String confirmPwd;

	public ChangePasswordForm() {
	}

	public String getCurrentPwd() {
		return currentPwd;
	}

	public void setCurrentPwd(String currentPwd) {
		this.currentPwd = currentPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}