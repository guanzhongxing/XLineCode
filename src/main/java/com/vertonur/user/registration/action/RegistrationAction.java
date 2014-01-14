package com.vertonur.user.registration.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.RuntimeConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.MailService;
import com.vertonur.dms.SystemService;
import com.vertonur.dms.TemplateService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.Admin;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.AttachmentInfo;
import com.vertonur.pojo.AttachmentInfo.AttachmentType;
import com.vertonur.pojo.Moderator;
import com.vertonur.pojo.User;
import com.vertonur.pojo.config.AttachmentConfig;
import com.vertonur.pojo.security.Group;
import com.vertonur.pojo.security.Group.GroupType;
import com.vertonur.pojo.statistician.UserMsgStatistician;
import com.vertonur.user.registration.form.RegistrationForm;
import com.vertonur.util.ForumCommonUtil;

import freemarker.template.TemplateException;

public final class RegistrationAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!isTokenValid(request)) {
			return mapping.findForward("DoubleSubmit");
		}

		GlobalConfig globalConfig = SystemConfig.getConfig().getGlobalConfig();
		if (globalConfig.isRegistrationCaptchaEnabled()) {
			if (!"true".equals(ForumCommonUtil.checkCaptchaResult(request))) {
				request.setAttribute("wrongCaptcha", true);
				request.setAttribute("requireCaptcha", true);
				return mapping.getInputForward();
			}
		}

		RegistrationForm rgtForm = (RegistrationForm) form;
		String fromAdminStr = request.getParameter("fromAdmin");
		boolean fromAdmin = false;
		if (fromAdminStr != null && !"".equals(fromAdminStr))
			fromAdmin = Boolean.valueOf(fromAdminStr);

		SystemContextService service = SystemContextService.getService();
		SystemService systemService = service
				.getDataManagementService(ServiceEnum.SYSTEM_SERVICE);
		GroupService groupService = service
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		Group group = null;
		if (fromAdmin) {
			int groupId = rgtForm.getGroupId();
			group = groupService.getGroupById(groupId);
		} else {
			int defaultUserGroupId = systemService.getSystemStatistician()
					.getDefaultUserGroupId();
			group = groupService.getGroupById(defaultUserGroupId);
		}
		User user = createUser(rgtForm, fromAdmin, group);

		FormFile image = rgtForm.getImage();
		if (image != null && image.getFileSize() != 0) {
			setUpAvatar(user, image);
		} else
			setUpAvatar(user, "defaultAvatar.jpeg");
		GroupType groupType = group.getGroupType();
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		if (groupType == GroupType.GENERIC_MDR) {
			userService.saveModerator((Moderator) user);
		} else
			userService.saveUser(user);

		boolean requireAuthEmail = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
				.getUserConfig().isRequireNewUserAuthEmail();
		if (requireAuthEmail) {
			sendActivationMail(request, user);

			request.setAttribute("registration", true);
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.jsp.activate.acct.msg"));
			saveMessages(request, messages);
			return mapping.findForward("MessagePage");
		} else {
			userService.activateUser(user.getId());
			if (fromAdmin) {
				return mapping.findForward("AdminUserList");
			} else {
				response.sendRedirect("userLogin.do?userId=" + user.getEmail()
						+ "&&password=" + rgtForm.getPwd()
						+ "&&registrationMark=true");
				return null;
			}
		}
	}

	private User createUser(RegistrationForm rgtForm, boolean fromAdmin,
			Group group) {
		String userName = rgtForm.getUserName();
		String password = rgtForm.getPwd();
		char gender = rgtForm.getGender();
		String email = rgtForm.getEmail();
		String signature = rgtForm.getSignature();

		User user = null;
		GroupType groupType = group.getGroupType();
		if (groupType == GroupType.GENERIC_MDR)
			user = new Moderator();
		else if (groupType == GroupType.GENERIC_ADMIN)
			user = new Admin();
		else
			user = new User();
		user.addGroup(group);

		user.setName(userName);
		user.setPassword(password);
		user.setGender(gender);
		user.setEmail(email);
		user.setSignature(signature);
		UserMsgStatistician statistician = new UserMsgStatistician();
		statistician.setUser(user);
		user.setStatistician(statistician);

		AttachmentConfig config = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
				.getAttachmentConfig();
		user.setAttmEnabled(config.isAttmtEnabled());
		user.setCanDownloadAttms(config.isDownloadEnabled());

		return user;
	}

	private void setUpAvatar(User user, FormFile image)
			throws FileNotFoundException, IOException {
		RuntimeConfig config = SystemConfig.getConfig().getRuntimeConfig();
		String avatarRoot = config.getAvatarRootFolder();
		avatarRoot = config.getUploadRootFolder() + "/" + avatarRoot;

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date uploadDate = new Date();
		String realFileName = RandomStringUtils.randomAlphabetic(6) + "_"
				+ image.getFileName();

		String phisicalPath = avatarRoot + "/" + format.format(uploadDate)
				+ "/" + realFileName;
		Date startTime = new Date();
		String downloadableUrl = ForumCommonUtil.uploadBcsObject(image,
				phisicalPath);
		Date endTime = new Date();
		long elapsedTime = endTime.getTime() - startTime.getTime();

		AttachmentInfo attmInfo = new AttachmentInfo();
		attmInfo.setAttachmentType(AttachmentType.BCS);
		attmInfo.setMimeType(image.getContentType());
		attmInfo.setFilesize(image.getFileSize());
		attmInfo.setRealFilename(realFileName);
		attmInfo.setPhysicalFilename(phisicalPath);
		attmInfo.setDownloadUrl(downloadableUrl);
		attmInfo.setUploadTime(uploadDate);
		attmInfo.setUploadTimeInMillis(elapsedTime);

		Attachment attm = new Attachment();
		attm.setUploader(user);
		attm.setAttmInfo(attmInfo);

		user.setAvatar(attm);
	}

	private void setUpAvatar(User user, String defaultAvatar) {
		RuntimeConfig config = SystemConfig.getConfig().getRuntimeConfig();
		String filePath = config.getUploadRootFolder()
				+ config.getAvatarRootFolder() + "/" + defaultAvatar;

		AttachmentInfo attmInfo = new AttachmentInfo();
		attmInfo.setAttachmentType(AttachmentType.LOCAL);
		attmInfo.setRealFilename(defaultAvatar);
		attmInfo.setPhysicalFilename(filePath);
		attmInfo.setDownloadUrl(filePath);

		Attachment attm = new Attachment();
		attm.setUploader(user);
		attm.setAttmInfo(attmInfo);

		user.setAvatar(attm);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendActivationMail(HttpServletRequest request, User user)
			throws IOException, TemplateException, EmailException {
		Map data = new HashMap();
		data.put("user", user);
		SystemConfig systemConfig = SystemConfig.getConfig();
		String forumName = systemConfig.getGlobalConfig().getForumName();
		data.put("forumName", forumName);
		String host = request.getLocalName();
		int port = request.getLocalPort();
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(host);
		if (port != 80) {
			sb.append(":");
			sb.append(port);
		}
		sb.append(request.getContextPath());
		sb.append("/accountActivation.do?authCode=");
		int id = user.getId();
		String authCode = SystemContextService.getService()
				.addNewAcctActivationSession(id);
		sb.append(authCode);
		sb.append("&userId=");
		sb.append(id);
		data.put("url", sb.toString());
		System.out.println(sb.toString());
		data.put("activationKey", authCode);

		TemplateService templateService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.TEMPLATE_SERVICE);
		MailService mailService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.MAIL_SERVICE);
		String msg = templateService.getProcessedTxt(
				mailService.getActivateAcctNotificationFile(), data);
		Map addresses = new HashMap();
		addresses.put(user.getName(), user.getEmail());
		mailService.sendMail(
				forumName + "--"
						+ mailService.getActivateAcctNotificationSubject(),
				msg, addresses);
	}
}