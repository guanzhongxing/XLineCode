package com.vertonur.admin.form;

import org.apache.struts.action.ActionForm;

public class ModerationForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916893792232030466L;
	private int[] msgIds;
	private String[] logTypes;
	private int[] logIds;

	public int[] getMsgIds() {
		return msgIds;
	}

	public void setMsgIds(int[] msgIds) {
		this.msgIds = msgIds;
	}

	public int[] getLogIds() {
		return logIds;
	}

	public void setLogIds(int[] logIds) {
		this.logIds = logIds;
	}

	public String[] getLogTypes() {
		return logTypes;
	}

	public void setLogTypes(String[] logTypes) {
		this.logTypes = logTypes;
	}
}
