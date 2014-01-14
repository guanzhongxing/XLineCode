package com.vertonur.user.user.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.User;
import com.vertonur.pojo.config.UserConfig;

//validation about correction of user's inputed data hasn't been made yet
public class MyProfileForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	private int userId;
	private String name;
	private String email;
	private String oldEmail;
	private String qq;
	private String msn;
	private String webSite;
	private String location;
	private String interests;
	private String signature;
	private String language;

	private byte delAvatar;
	private byte showEmailAddr;
	private byte hideOnlineStatus;
	private byte attachSignature;

	private FormFile image;

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public MyProfileForm() {
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getQq() {
		return qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public byte isShowEmailAddr() {
		return showEmailAddr;
	}

	public void setShowEmailAddr(byte showEmailAddr) {
		this.showEmailAddr = showEmailAddr;
	}

	public byte isHideOnlineStatus() {
		return hideOnlineStatus;
	}

	public void setHideOnlineStatus(byte hideOnlineStatus) {
		this.hideOnlineStatus = hideOnlineStatus;
	}

	public byte isAttachSignature() {
		return attachSignature;
	}

	public void setAttachSignature(byte attachSignature) {
		this.attachSignature = attachSignature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setDelAvatar(byte delAvatar) {
		this.delAvatar = delAvatar;
	}

	public byte isDelAvatar() {
		return delAvatar;
	}

	public FormFile getImage() {
		return image;
	}

	public void setImage(FormFile image) {
		this.image = image;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// run the validations declared in validation.xml first
		ActionErrors errors = super.validate(mapping, request);
		errors = (errors == null) ? new ActionErrors() : errors;

		if (!"".equals(email) && !email.equals(oldEmail)) {
			SystemContextService service = SystemContextService.getService();
			UserService userService = service
					.getDataManagementService(ServiceEnum.USER_SERVICE);
			User user = userService.getUserByEmail(email);
			if (user != null)
				errors.add("email", new ActionMessage("error.form.email.exist"));
		}

		SystemContextService service = SystemContextService.getService();
		UserConfig userConfig = service.getDataManagementService(
				ServiceEnum.RUNTIME_PARAMETER_SERVICE).getUserConfig();
		int avatarSize = userConfig.getAvatarSize();
		int size = avatarSize * 1024;
		if (image != null && image.getFileSize() != 0
				&& image.getFileSize() > size)
			errors.add("fileinvalid", new ActionMessage(
					"error.form.fileinvalid", avatarSize));

		if (errors != null && !errors.isEmpty())
			return errors;
		else
			return null;

	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
}