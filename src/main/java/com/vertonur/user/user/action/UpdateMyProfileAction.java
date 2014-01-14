package com.vertonur.user.user.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.vertonur.bean.config.RuntimeConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.AttachmentInfo;
import com.vertonur.pojo.AttachmentInfo.AttachmentType;
import com.vertonur.pojo.User;
import com.vertonur.pojo.UserPreferences;
import com.vertonur.session.UserSession;
import com.vertonur.user.user.form.MyProfileForm;
import com.vertonur.util.ForumCommonUtil;

public class UpdateMyProfileAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.findForward("DisplayMyProfile");
		}

		int userId = Integer.parseInt(request.getParameter("userId"));
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		User user = userService.getUserById(userId);
		request.setAttribute("displayedUser", user);

		MyProfileForm castedForm = (MyProfileForm) form;
		user.setName(castedForm.getName());
		user.setEmail(castedForm.getEmail());
		user.setQq(castedForm.getQq());
		user.setMsn(castedForm.getMsn());
		user.setWebSite(castedForm.getWebSite());
		user.setLocation(castedForm.getLocation());
		user.setInterests(castedForm.getInterests());
		user.setSignature(castedForm.getSignature());

		UserPreferences userPres = user.getUserPres();
		if (userPres == null) {
			userPres = new UserPreferences();
			user.setUserPres(userPres);
		}
		userPres.setAttachSignature(castedForm.isAttachSignature());
		userPres.setHideOnlineStatus(castedForm.isHideOnlineStatus());
		userPres.setShowEmailAddr(castedForm.isShowEmailAddr());

		String locale = castedForm.getLanguage();
		if (locale.equals("default"))
			locale = request.getLocale().toString();
		userPres.setLocale(locale);
		HttpSession session = request.getSession(true);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		userSession.setLocale(locale);

		RuntimeConfig config = SystemConfig.getConfig().getRuntimeConfig();
		String avatarRoot = config.getAvatarRootFolder();
		avatarRoot = config.getUploadRootFolder() + avatarRoot;

		boolean isDeleted = false;
		AttachmentInfo attmInfo = user.getAvatar().getAttmInfo();
		String filePath = attmInfo.getPhysicalFilename();
		if (castedForm.isDelAvatar() == 1
				&& !filePath.contains(Constants.DEFAULT_AVATAR)) {
			ForumCommonUtil.deleteBcsObject(filePath);

			attmInfo.setPhysicalFilename(avatarRoot + "/"
					+ Constants.DEFAULT_AVATAR);
			attmInfo.setDownloadUrl(avatarRoot + "/" + Constants.DEFAULT_AVATAR);
			attmInfo.setAttachmentType(AttachmentType.LOCAL);
			isDeleted = true;
		}

		FormFile image = castedForm.getImage();
		if (image != null && image.getFileSize() != 0) {
			if (!isDeleted && !filePath.contains(Constants.DEFAULT_AVATAR))
				ForumCommonUtil.deleteBcsObject(filePath);

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date uploadDate = new Date();
			String realFileName = RandomStringUtils.randomAlphabetic(6) + "_"
					+ image.getFileName();

			String phisicalPath = avatarRoot + "/" + format.format(uploadDate)
					+ "/" + realFileName;
			String downloadUrl = ForumCommonUtil.uploadBcsObject(image,
					phisicalPath);
			attmInfo.setMimeType(image.getContentType());
			attmInfo.setAttachmentType(AttachmentType.BCS);
			attmInfo.setFilesize(image.getFileSize());
			attmInfo.setUploadTime(uploadDate);
			attmInfo.setRealFilename(realFileName);
			attmInfo.setPhysicalFilename(phisicalPath);
			attmInfo.setDownloadUrl(downloadUrl);
		}

		userService.updateUser(user);
		request.setAttribute("userId", userId);
		resetToken(request);
		return mapping.findForward("DisplayMyProfile");
	}
}