package com.vertonur.user.registration.form;

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
public class RegistrationForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String pwd;
	private String confirmPwd;
	private char gender;
	private String email;
	private String signature;
	private String grade;
	private FormFile image;
	private int groupId;
	private boolean fromAdmin;

	public RegistrationForm() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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
		// int size=image.getFileSize();

		SystemContextService service = SystemContextService.getService();
		UserService userService = service
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		User user = userService.getUserByEmail(email);
		if (user != null)
			errors.add("email", new ActionMessage("error.form.email.exist"));

		UserConfig userConfig = service.getDataManagementService(
				ServiceEnum.RUNTIME_PARAMETER_SERVICE).getUserConfig();
		int avatarSize = userConfig.getAvatarSize();
		int size = avatarSize * 1024;
		if (image != null && image.getFileSize() != 0
				&& image.getFileSize() > size)
			errors.add("image", new ActionMessage("error.form.fileinvalid",
					avatarSize));

		if (errors != null && !errors.isEmpty())
			return errors;
		else
			return null;

	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public boolean isFromAdmin() {
		return fromAdmin;
	}

	public void setFromAdmin(boolean fromAdmin) {
		this.fromAdmin = fromAdmin;
	}
}