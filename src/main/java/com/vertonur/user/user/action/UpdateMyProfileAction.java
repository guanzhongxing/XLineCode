package com.vertonur.user.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.AttachmentInfo;
import com.vertonur.pojo.User;
import com.vertonur.pojo.UserPreferences;
import com.vertonur.session.UserSession;
import com.vertonur.user.user.form.MyProfileForm;

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

		Attachment attachment = user.getAvatar();
		AttachmentInfo attmInfo = attachment.getAttmInfo();
		String filePath = attmInfo.getFilePath();
		if (castedForm.isDelAvatar() == 1
				&& !filePath.contains(Constants.DEFAULT_AVATAR))
			userService.setUpDefaultAvatar(user);

		FormFile image = castedForm.getImage();
		if (image != null && image.getFileSize() != 0)
			userService.setUpAvatar(image.getInputStream(),
					image.getContentType(), image.getFileName(),
					image.getFileSize(), user);

		userService.updateUser(user);
		request.setAttribute("userId", userId);
		resetToken(request);
		return mapping.findForward("DisplayMyProfile");
	}
}