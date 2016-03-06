package com.vertonur.bean.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vertonur.pojo.config.Config;

@Entity(name = "INFO_FRM_GBL_CFG")
public class GlobalConfig implements Config {

	private int id;
	@NotNull(message = "{not.null}")
	private String homepageLink;
	@NotNull(message = "{not.null}")
	private String forumName;
	@NotNull(message = "{not.null}")
	private String forumPageTitle;
	private String forumDescription;
	private boolean registrationCaptchaEnabled;
	private boolean downloadCaptchaEnabled;
	private boolean loginCaptchaEnabled;
	private int metaDescLength;

	@Id
	@GeneratedValue
	@Column(name = "GBL_CFG_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "GBL_CFG_HOME_PAGE_LINK")
	public String getHomepageLink() {
		return homepageLink;
	}

	public void setHomepageLink(String homepageLink) {
		this.homepageLink = homepageLink;
	}

	@Column(name = "GBL_CFG_FORUM_PAGE_TITLE")
	public String getForumPageTitle() {
		return forumPageTitle;
	}

	public void setForumPageTitle(String forumPageTitle) {
		this.forumPageTitle = forumPageTitle;
	}

	@Column(name = "GBL_CFG_FORUM_NME")
	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	@Column(name = "GBL_CFG_FORUM_DESC")
	public String getForumDescription() {
		return forumDescription;
	}

	public void setForumDescription(String forumDescription) {
		this.forumDescription = forumDescription;
	}

	@Column(name = "GBL_CFG_RGTRN_CAPTCHA_ENABLED")
	public boolean isRegistrationCaptchaEnabled() {
		return registrationCaptchaEnabled;
	}

	public void setRegistrationCaptchaEnabled(boolean registrationCaptchaEnabled) {
		this.registrationCaptchaEnabled = registrationCaptchaEnabled;
	}

	@Column(name = "GBL_CFG_DNLD_CAPTCHA_ENABLED")
	public boolean isDownloadCaptchaEnabled() {
		return downloadCaptchaEnabled;
	}

	public void setDownloadCaptchaEnabled(boolean downloadCaptchaEnabled) {
		this.downloadCaptchaEnabled = downloadCaptchaEnabled;
	}

	@Column(name = "GBL_CFG_LGIN_CAPTCHA_ENABLED")
	public boolean isLoginCaptchaEnabled() {
		return loginCaptchaEnabled;
	}

	public void setLoginCaptchaEnabled(boolean loginCaptchaEnabled) {
		this.loginCaptchaEnabled = loginCaptchaEnabled;
	}

	@Column(name = "GBL_CFG_META_DESC_LGH")
	public int getMetaDescLength() {
		return metaDescLength;
	}

	public void setMetaDescLength(int metaDescLength) {
		this.metaDescLength = metaDescLength;
	}
}
