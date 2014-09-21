package com.vertonur.admin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

public class SystemConfigForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8881005441124765670L;

	private String homepageLink;
	private String forumName;
	private String forumPageTitle;
	private String forumDesc;
	private boolean registrationCaptchaEnabled;
	private boolean downloadCaptchaEnabled;
	private boolean loginCaptchaEnabled;

	public boolean isRegistrationCaptchaEnabled() {
		return registrationCaptchaEnabled;
	}

	public void setRegistrationCaptchaEnabled(boolean registrationCaptchaEnabled) {
		this.registrationCaptchaEnabled = registrationCaptchaEnabled;
	}

	public boolean isDownloadCaptchaEnabled() {
		return downloadCaptchaEnabled;
	}

	public void setDownloadCaptchaEnabled(boolean downloadCaptchaEnabled) {
		this.downloadCaptchaEnabled = downloadCaptchaEnabled;
	}

	public boolean isLoginCaptchaEnabled() {
		return loginCaptchaEnabled;
	}

	public void setLoginCaptchaEnabled(boolean loginCaptchaEnabled) {
		this.loginCaptchaEnabled = loginCaptchaEnabled;
	}

	private int topicsPerPage;
	private int rspsPerPage;
	private int usersPerPage;
	private long newTopicInterval;
	private long newRspInterval;

	private boolean registrationEnabled;

	private int hotTopicDef;
	private int recentTopicPageNum;

	private int avatarSize;
	private int avatarHeight;
	private int avatarWidth;
	private int sessionTimeout;
	private int loginSessionTimeout;

	private String sender;
	private String smtpHost;
	private int smtpPort;
	private boolean requireAuth;
	private boolean requireSSL;
	private String smtpUsername;
	private String smtpPwd;
	private String address;
	private String newRspNotificationFile;
	private String newRspNotificationSubject;
	private String newPmNotificationFile;
	private String newPmNotificationSubject;
	private String activateAcctNotificationFile;
	private String activateAcctNotificationSubject;
	private String recoverPwdNotificationFile;
	private String recoverPwdNotificationSubject;
	private String emailFormat;
	private boolean notifyUserOnNewRsp;
	private boolean requireAuthEmail;

	private int mdrDigestionNum;
	private int assignPendingLogInterval;

	public String getHomepageLink() {
		return homepageLink;
	}

	public void setHomepageLink(String homepageLink) {
		this.homepageLink = homepageLink;
	}

	public String getForumPageTitle() {
		return forumPageTitle;
	}

	public void setForumPageTitle(String forumPageTitle) {
		this.forumPageTitle = forumPageTitle;
	}

	public int getTopicsPerPage() {
		return topicsPerPage;
	}

	public void setTopicsPerPage(int topicsPerPage) {
		this.topicsPerPage = topicsPerPage;
	}

	public int getRspsPerPage() {
		return rspsPerPage;
	}

	public void setRspsPerPage(int rspsPerPage) {
		this.rspsPerPage = rspsPerPage;
	}

	public int getUsersPerPage() {
		return usersPerPage;
	}

	public void setUsersPerPage(int usersPerPage) {
		this.usersPerPage = usersPerPage;
	}

	public long getNewTopicInterval() {
		return newTopicInterval;
	}

	public void setNewTopicInterval(long newTopicInterval) {
		this.newTopicInterval = newTopicInterval;
	}

	public long getNewRspInterval() {
		return newRspInterval;
	}

	public void setNewRspInterval(long newRspInterval) {
		this.newRspInterval = newRspInterval;
	}

	public boolean isRegistrationEnabled() {
		return registrationEnabled;
	}

	public void setRegistrationEnabled(boolean registrationEnabled) {
		this.registrationEnabled = registrationEnabled;
	}

	public int getHotTopicDef() {
		return hotTopicDef;
	}

	public void setHotTopicDef(int hotTopicDef) {
		this.hotTopicDef = hotTopicDef;
	}

	public int getRecentTopicPageNum() {
		return recentTopicPageNum;
	}

	public void setRecentTopicPageNum(int recentTopicPageNum) {
		this.recentTopicPageNum = recentTopicPageNum;
	}

	public int getAvatarSize() {
		return avatarSize;
	}

	public void setAvatarSize(int avatarSize) {
		this.avatarSize = avatarSize;
	}

	public int getAvatarHeight() {
		return avatarHeight;
	}

	public void setAvatarHeight(int avatarHeight) {
		this.avatarHeight = avatarHeight;
	}

	public int getAvatarWidth() {
		return avatarWidth;
	}

	public void setAvatarWidth(int avatarWidth) {
		this.avatarWidth = avatarWidth;
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public boolean isRequireAuth() {
		return requireAuth;
	}

	public void setRequireAuth(boolean requireAuth) {
		this.requireAuth = requireAuth;
	}

	public boolean isRequireSSL() {
		return requireSSL;
	}

	public void setRequireSSL(boolean requireSSL) {
		this.requireSSL = requireSSL;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNewRspNotificationFile() {
		return newRspNotificationFile;
	}

	public void setNewRspNotificationFile(String newRspNotificationFile) {
		this.newRspNotificationFile = newRspNotificationFile;
	}

	public String getNewRspNotificationSubject() {
		return newRspNotificationSubject;
	}

	public void setNewRspNotificationSubject(String newRspNotificationSubject) {
		this.newRspNotificationSubject = newRspNotificationSubject;
	}

	public String getNewPmNotificationFile() {
		return newPmNotificationFile;
	}

	public void setNewPmNotificationFile(String newPmNotificationFile) {
		this.newPmNotificationFile = newPmNotificationFile;
	}

	public String getNewPmNotificationSubject() {
		return newPmNotificationSubject;
	}

	public void setNewPmNotificationSubject(String newPmNotificationSubject) {
		this.newPmNotificationSubject = newPmNotificationSubject;
	}

	public String getActivateAcctNotificationFile() {
		return activateAcctNotificationFile;
	}

	public void setActivateAcctNotificationFile(
			String activateAcctNotificationFile) {
		this.activateAcctNotificationFile = activateAcctNotificationFile;
	}

	public String getActivateAcctNotificationSubject() {
		return activateAcctNotificationSubject;
	}

	public void setActivateAcctNotificationSubject(
			String activateAcctNotificationSubject) {
		this.activateAcctNotificationSubject = activateAcctNotificationSubject;
	}

	public String getRecoverPwdNotificationFile() {
		return recoverPwdNotificationFile;
	}

	public void setRecoverPwdNotificationFile(String recoverPwdNotificationFile) {
		this.recoverPwdNotificationFile = recoverPwdNotificationFile;
	}

	public String getRecoverPwdNotificationSubject() {
		return recoverPwdNotificationSubject;
	}

	public void setRecoverPwdNotificationSubject(
			String recoverPwdNotificationSubject) {
		this.recoverPwdNotificationSubject = recoverPwdNotificationSubject;
	}

	public String getEmailFormat() {
		return emailFormat;
	}

	public void setEmailFormat(String emailFormat) {
		this.emailFormat = emailFormat;
	}

	public boolean isNotifyUserOnNewRsp() {
		return notifyUserOnNewRsp;
	}

	public void setNotifyUserOnNewRsp(boolean notifyUserOnNewRsp) {
		this.notifyUserOnNewRsp = notifyUserOnNewRsp;
	}

	public boolean isRequireAuthEmail() {
		return requireAuthEmail;
	}

	public void setRequireAuthEmail(boolean requireAuthEmail) {
		this.requireAuthEmail = requireAuthEmail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPwd() {
		return smtpPwd;
	}

	public void setSmtpPwd(String smtpPwd) {
		this.smtpPwd = smtpPwd;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public int getMdrDigestionNum() {
		return mdrDigestionNum;
	}

	public void setMdrDigestionNum(int mdrDigestionNum) {
		this.mdrDigestionNum = mdrDigestionNum;
	}

	public int getAssignPendingLogInterval() {
		return assignPendingLogInterval;
	}

	public void setAssignPendingLogInterval(int assignPendingLogInterval) {
		this.assignPendingLogInterval = assignPendingLogInterval;
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = super.validate(arg0, arg1);
		if (topicsPerPage == 0)
			errors.add("topicsPerPage", new ActionMessage(
					"error.form.field.required"));
		else if (topicsPerPage < 0)
			errors.add("topicsPerPage", new ActionMessage(
					"error.form.positive.integer.required"));

		if (rspsPerPage == 0)
			errors.add("rspsPerPage", new ActionMessage(
					"error.form.field.required"));
		else if (rspsPerPage < 0)
			errors.add("rspsPerPage", new ActionMessage(
					"error.form.positive.integer.required"));

		if (usersPerPage == 0)
			errors.add("usersPerPage", new ActionMessage(
					"error.form.field.required"));
		else if (usersPerPage < 0)
			errors.add("usersPerPage", new ActionMessage(
					"error.form.positive.integer.required"));

		if (newTopicInterval == 0)
			errors.add("newTopicInterval", new ActionMessage(
					"error.form.field.required"));
		else if (newTopicInterval < 0)
			errors.add("newTopicInterval", new ActionMessage(
					"error.form.positive.integer.required"));

		if (newRspInterval == 0)
			errors.add("newRspInterval", new ActionMessage(
					"error.form.field.required"));
		else if (newRspInterval < 0)
			errors.add("newRspInterval", new ActionMessage(
					"error.form.positive.integer.required"));

		/*
		 * if (recentTopicCacheSize == 0) errors.add("recentTopicCacheSize", new
		 * ActionMessage( "error.form.field.required")); else if
		 * (recentTopicCacheSize < 0) errors.add("recentTopicCacheSize", new
		 * ActionMessage( "error.form.positive.integer.required"));
		 */

		if (hotTopicDef == 0)
			errors.add("hotTopicDef", new ActionMessage(
					"error.form.field.required"));
		else if (hotTopicDef < 0)
			errors.add("hotTopicDef", new ActionMessage(
					"error.form.positive.integer.required"));

		if (recentTopicPageNum == 0)
			errors.add("recentTopicPageNum", new ActionMessage(
					"error.form.field.required"));
		else if (recentTopicPageNum < 0)
			errors.add("recentTopicPageNum", new ActionMessage(
					"error.form.positive.integer.required"));

		if (avatarSize == 0)
			errors.add("avatarSize", new ActionMessage(
					"error.form.field.required"));
		else if (avatarSize < 0)
			errors.add("avatarSize", new ActionMessage(
					"error.form.positive.integer.required"));

		if (avatarHeight == 0)
			errors.add("avatarHeight", new ActionMessage(
					"error.form.field.required"));
		else if (avatarHeight < 0)
			errors.add("avatarHeight", new ActionMessage(
					"error.form.positive.integer.required"));

		if (avatarWidth == 0)
			errors.add("avatarWidth", new ActionMessage(
					"error.form.field.required"));
		else if (avatarWidth < 0)
			errors.add("avatarWidth", new ActionMessage(
					"error.form.positive.integer.required"));

		if (sessionTimeout == 0)
			errors.add("sessionTimeout", new ActionMessage(
					"error.form.field.required"));
		else if (sessionTimeout < 0)
			errors.add("sessionTimeout", new ActionMessage(
					"error.form.positive.integer.required"));

		if (loginSessionTimeout == 0)
			errors.add("loginSessionTimeout", new ActionMessage(
					"error.form.field.required"));
		else if (loginSessionTimeout < 0)
			errors.add("loginSessionTimeout", new ActionMessage(
					"error.form.positive.integer.required"));

		if (smtpPort == 0)
			errors.add("smtpPort", new ActionMessage(
					"error.form.field.required"));
		else if (smtpPort < 0)
			errors.add("smtpPort", new ActionMessage(
					"error.form.positive.integer.required"));

		if (mdrDigestionNum == 0)
			errors.add("mdrDigestionNum", new ActionMessage(
					"error.form.field.required"));
		else if (mdrDigestionNum < 0)
			errors.add("mdrDigestionNum", new ActionMessage(
					"error.form.positive.integer.required"));

		if (assignPendingLogInterval == 0)
			errors.add("assignPendingLogInterval", new ActionMessage(
					"error.form.field.required"));
		else if (assignPendingLogInterval < 0)
			errors.add("assignPendingLogInterval", new ActionMessage(
					"error.form.positive.integer.required"));
		if (errors != null)
			return errors;
		return null;
	}

	public int getLoginSessionTimeout() {
		return loginSessionTimeout;
	}

	public void setLoginSessionTimeout(int loginSessionTimeout) {
		this.loginSessionTimeout = loginSessionTimeout;
	}

	public String getForumDesc() {
		return forumDesc;
	}

	public void setForumDesc(String forumDesc) {
		this.forumDesc = forumDesc;
	}
}