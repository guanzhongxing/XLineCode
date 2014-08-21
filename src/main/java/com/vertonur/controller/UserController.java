package com.vertonur.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vertonur.bean.User;
import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.GroupService;
import com.vertonur.dms.MailService;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.SystemService;
import com.vertonur.dms.TemplateService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.AttachmentSizeExceedException;
import com.vertonur.dms.exception.InvalidOldPasswordException;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.Admin;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.AttachmentInfo;
import com.vertonur.pojo.Moderator;
import com.vertonur.pojo.UserPreferences;
import com.vertonur.pojo.config.AttachmentConfig;
import com.vertonur.pojo.config.UserConfig;
import com.vertonur.pojo.security.Group;
import com.vertonur.pojo.security.Group.GroupType;
import com.vertonur.pojo.statistician.UserMsgStatistician;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;
import com.vertonur.util.ForumCommonUtil.PageType;
import com.vertonur.util.TxtFileReader;

import freemarker.template.TemplateException;

@Controller
public class UserController {

	@RequestMapping(value = "/users/form", method = RequestMethod.GET)
	public String showHomePage(HttpServletRequest request) {

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		request.setAttribute("loginCaptchaEnabled",
				config.isLoginCaptchaEnabled());

		return "default/user/user/user_login";
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public String showUserProfile(@PathVariable int userId,
			@RequestParam(required = false) boolean mine,
			HttpServletRequest request) {

		UserService userService = new UserService();
		User user = userService.getUserById(userId);
		request.setAttribute("displayedUser", user);

		if (!mine) {
			InfoService infoService = new InfoService();
			long topicNum = infoService.getTopicNumByCreator(user);
			request.setAttribute("topicNum", topicNum);

			long rsNum = infoService.getResponseNumByUser(user);
			request.setAttribute("rsNum", rsNum);
		}

		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		UserConfig config = service.getUserConfig();
		request.setAttribute("avatarHeight", config.getAvatarHeight());
		request.setAttribute("avatarWidth", config.getAvatarWidth());

		if (mine)
			return "default/user/user/user_form";
		else
			return "default/user/user/user_profile";
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
	public String updateUserProfile(@PathVariable int userId,
			@Valid com.vertonur.pojo.User user, BindingResult bindingResult,
			@RequestParam(defaultValue = "0") byte attachSignature,
			@RequestParam(defaultValue = "0") byte hideOnlineStatus,
			@RequestParam(defaultValue = "0") byte showEmailAddr,
			@RequestParam(defaultValue = "0") byte delAvatar,
			@RequestParam(required = false) MultipartFile image,
			@RequestParam(defaultValue = "default") String locale,
			HttpServletRequest request) throws IOException, URISyntaxException,
			AttachmentSizeExceedException {

		if (bindingResult.hasErrors()) {
			return showUserProfile(userId, true, request);
		}

		com.vertonur.dms.UserService userService = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		com.vertonur.pojo.User savedUser = userService.getUserById(userId);
		request.setAttribute("displayedUser", savedUser);

		savedUser.setName(user.getName());
		savedUser.setEmail(user.getEmail());
		savedUser.setQq(user.getQq());
		savedUser.setMsn(user.getMsn());
		savedUser.setWebSite(user.getWebSite());
		savedUser.setLocation(user.getLocation());
		savedUser.setInterests(user.getInterests());
		savedUser.setSignature(user.getSignature());

		UserPreferences userPres = savedUser.getUserPres();
		if (userPres == null) {
			userPres = new UserPreferences();
			savedUser.setUserPres(userPres);
		}
		userPres.setAttachSignature(attachSignature);
		userPres.setHideOnlineStatus(hideOnlineStatus);
		userPres.setShowEmailAddr(showEmailAddr);

		if (locale.equals("default"))
			locale = request.getLocale().toString();
		userPres.setLocale(locale);
		HttpSession session = request.getSession(true);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		userSession.setLocale(locale);

		Attachment attachment = savedUser.getAvatar();
		AttachmentInfo attmInfo = attachment.getAttmInfo();
		String filePath = attmInfo.getFilePath();
		if (delAvatar == 1 && !filePath.contains(Constants.DEFAULT_AVATAR))
			userService.setUpDefaultAvatar(savedUser);

		if (image != null && image.getSize() != 0)
			userService.setUpAvatar(image.getInputStream(),
					image.getContentType(), image.getOriginalFilename(),
					image.getSize(), savedUser);

		userService.updateUser(savedUser);
		request.setAttribute("userId", userId);

		return "redirect:" + request.getServletPath() + "?" + "mine=true";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUserList(@RequestParam(defaultValue = "0") int start,
			HttpServletRequest request) {

		UserService userService = new UserService();
		long paginationSize = userService.getUserNum();
		List<User> members = userService.getUsers(start);

		String requestPath = request.getRequestURI() + "?";
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				start, requestPath, CxtType.MEMBER);
		request.setAttribute(PaginationContext.PAGE_CXT, pageCxt);
		request.setAttribute("members", members);

		HttpSession session = ((HttpServletRequest) request).getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		if (userSession.isAdmin())
			request.setAttribute("showEmail", true);
		else
			request.setAttribute("showEmail", false);

		ForumCommonUtil.setPageSeo(request, null, null, PageType.HOME_PAGE);

		return "default/user/user/members_list";
	}

	@RequestMapping(value = "/users", params = "admin")
	public String getAdminUserList(@RequestParam(defaultValue = "0") int start,
			@RequestParam(required = false) String username,
			@RequestParam(defaultValue = "0") int groupId,
			HttpServletRequest request) {

		long paginationSize;
		List<User> members;
		UserService userService = new UserService();
		if ((!"".equals(username) && null != username) || groupId != 0) {
			paginationSize = userService.getUserNumByNameAndGroupId_AM(
					username, groupId);
			members = userService.getUsersByNameAndGroupId_AM(username, start,
					groupId);
		} else {
			paginationSize = userService.getUserNum();
			members = userService.getUsers(start);
		}
		request.setAttribute("members", members);

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		List<Group> groups = groupService.getTopLevelGroups();
		request.setAttribute("groups", groups);

		String requestPath = request.getRequestURI() + "?admin";
		if (!"".equals(username) && null != username) {
			requestPath += "&username=" + username;
			request.setAttribute("username", username);
		}
		if (groupId != 0) {
			requestPath += "&groupId=" + groupId;
			request.setAttribute("groupId", groupId);
		}
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				start, requestPath, CxtType.MEMBER);
		request.setAttribute(PaginationContext.PAGE_CXT, pageCxt);

		return "default/admin/user_list";
	}

	@RequestMapping(value = "/users/{userId}/groups")
	public String getAdminUserGroupList(@PathVariable int userId,
			HttpServletRequest request) {

		GroupService groupService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		List<Group> groups = groupService.getTopLevelGroups();
		request.setAttribute("groups", groups);

		UserService userService = new UserService();
		User user = userService.getUserById(userId);
		request.setAttribute("user", user);

		return "default/admin/user_groups";
	}

	@RequestMapping(value = "/users/register")
	public String showRegistrationAgreement(HttpServletRequest request)
			throws IOException {

		UserConfig config = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
				.getUserConfig();
		boolean registrationEnabled = config.isRegistrationEnabled();
		if (registrationEnabled) {
			request.setAttribute("registrationEnabled", registrationEnabled);

			URL filePath = request.getServletContext().getResource(
					"/WEB-INF/templates/common/txt/terms_zh_CN.txt");
			String content = TxtFileReader.readTxtFile(filePath.openStream());
			request.setAttribute("agreementContent", content);
			String fromAdmin = request.getParameter("fromAdmin");
			if ("true".equals(fromAdmin))
				request.setAttribute("fromAdmin", true);
		}

		return "default/user/registration/show_agreement";
	}

	@RequestMapping(value = "/users/register/form")
	public String showRegistrationForm(
			@RequestParam(required = false) boolean fromAdmin,
			HttpServletRequest request) throws IOException {

		if (fromAdmin) {
			request.setAttribute("fromAdmin", true);
			GroupService groupService = SystemContextService.getService()
					.getDataManagementService(ServiceEnum.GROUP_SERVICE);
			List<Group> groups = groupService.getTopLevelGroups();
			request.setAttribute("groups", groups);
		}

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		request.setAttribute("registrationCaptchaEnabled",
				config.isRegistrationCaptchaEnabled());
		return "default/user/registration/registration_form";
	}

	@RequestMapping(value = "/users/register", method = RequestMethod.POST)
	public String registerUser(@Valid com.vertonur.pojo.User user,
			BindingResult bindingResult,
			@RequestParam(defaultValue = "0") int groupId,
			@RequestParam String confirmPwd,
			@RequestParam(required = false) MultipartFile image,
			@RequestParam(required = false) boolean fromAdmin,
			HttpServletRequest request) throws IOException, TemplateException,
			EmailException, AttachmentSizeExceedException, URISyntaxException {

		if (bindingResult.hasErrors()) {
			return "default/user/registration/registration_form";
		}

		SystemContextService service = SystemContextService.getService();
		com.vertonur.dms.UserService userService = service
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		com.vertonur.pojo.User tmp = userService
				.getUserByEmail(user.getEmail());
		if (tmp != null) {
			request.setAttribute("emailExist", true);
			return "default/user/registration/registration_form";
		}

		UserConfig userConfig = service.getDataManagementService(
				ServiceEnum.RUNTIME_PARAMETER_SERVICE).getUserConfig();
		int avatarSize = userConfig.getAvatarSize();
		int size = avatarSize * 1024;
		if (image != null && image.getSize() != 0 && image.getSize() > size) {
			request.setAttribute("fileInvalid", true);
			return "default/user/registration/registration_form";
		}

		GlobalConfig globalConfig = SystemConfig.getConfig().getGlobalConfig();
		if (globalConfig.isRegistrationCaptchaEnabled()) {
			if (!"true".equals(ForumCommonUtil.checkCaptchaResult(request))) {
				request.setAttribute("wrongCaptcha", true);
				request.setAttribute("requireCaptcha", true);
				return "default/user/registration/registration_form";
			}
		}

		SystemService systemService = service
				.getDataManagementService(ServiceEnum.SYSTEM_SERVICE);
		GroupService groupService = service
				.getDataManagementService(ServiceEnum.GROUP_SERVICE);
		Group group = null;
		if (fromAdmin) {
			group = groupService.getGroupById(groupId);
		} else {
			int defaultUserGroupId = systemService.getSystemStatistician()
					.getDefaultUserGroupId();
			group = groupService.getGroupById(defaultUserGroupId);
		}
		createUser(user, image, group, userService);

		boolean requireAuthEmail = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
				.getUserConfig().isRequireNewUserAuthEmail();
		if (requireAuthEmail) {
			sendActivationMail(request, user);

			request.setAttribute("registration", true);
			return "default/user/message";
		} else {
			userService.activateUser(user.getId());
			if (fromAdmin) {
				return "redirect:/users?admin";
			} else {
				return "redirect:/users/login?userId=" + user.getEmail()
						+ "&password=" + confirmPwd + "&registrationMark=true";
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendActivationMail(HttpServletRequest request,
			com.vertonur.pojo.User user) throws IOException, TemplateException,
			EmailException {
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

	private void createUser(com.vertonur.pojo.User user, MultipartFile image,
			Group group, com.vertonur.dms.UserService userService)
			throws FileNotFoundException, AttachmentSizeExceedException,
			IOException, URISyntaxException {

		GroupType groupType = group.getGroupType();
		com.vertonur.pojo.User tmp;
		if (groupType == GroupType.GENERIC_MDR) {
			tmp = new Moderator();
			BeanUtils.copyProperties(user, tmp);
			user = tmp;
		} else if (groupType == GroupType.GENERIC_ADMIN) {
			tmp = new Admin();
			BeanUtils.copyProperties(user, tmp);
			user = tmp;
		}
		user.addGroup(group);

		UserMsgStatistician statistician = new UserMsgStatistician();
		statistician.setUser(user);
		user.setStatistician(statistician);

		AttachmentConfig config = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
				.getAttachmentConfig();
		user.setAttmEnabled(config.isAttmtEnabled());
		user.setCanDownloadAttms(config.isDownloadEnabled());
		if (groupType == GroupType.GENERIC_MDR) {
			userService.saveModerator((Moderator) user);
		}

		if (image != null && image.getSize() != 0)
			userService.setUpAvatar(image.getInputStream(),
					image.getContentType(), image.getOriginalFilename(),
					image.getSize(), user);
		else
			userService.setUpDefaultAvatar(user);
		if (groupType == GroupType.GENERIC_MDR) {
			userService.updateUser(user);
		} else
			userService.saveUser(user);
	}

	@RequestMapping(value = "/users/register/{userId}")
	public String showRegistrationCompletion(@PathVariable int userId,
			Model model) {

		model.addAttribute(userId);
		return "default/user/registration/registration_completion";
	}

	@RequestMapping(value = "/users/{userId}/password")
	public String showPasswordForm(@PathVariable int userId,
			HttpServletRequest request) {

		return "default/user/user/user_change_pwd";
	}

	@RequestMapping(value = "/users/{userId}/password", method = RequestMethod.PUT)
	public String updatePassword(@PathVariable int userId,
			@RequestParam String currentPwd, @RequestParam String newPwd,
			HttpServletRequest request) {

		SystemContextService service = SystemContextService.getService();
		com.vertonur.dms.UserService userService = service
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		try {
			userService.changePassword(userId, currentPwd, newPwd);
			return "redirect:/messages?password";
		} catch (InvalidOldPasswordException e) {
			request.setAttribute("invalidPwd", "true");
			return "default/user/user/user_change_pwd";
		}
	}
}
