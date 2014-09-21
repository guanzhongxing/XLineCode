package com.vertonur.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.vertonur.admin.form.SystemConfigForm;
import com.vertonur.bean.Admin;
import com.vertonur.bean.Forum;
import com.vertonur.bean.ForumStatistician;
import com.vertonur.bean.Forumzone;
import com.vertonur.bean.User;
import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.context.SystemContextService.SystemState;
import com.vertonur.dao.api.ConfigDAO;
import com.vertonur.dao.manager.DAOManager;
import com.vertonur.dms.ModerationService;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.CategoryModerationListNotEmptyException;
import com.vertonur.pojo.config.CommentConfig;
import com.vertonur.pojo.config.EmailConfig;
import com.vertonur.pojo.config.InfoConfig;
import com.vertonur.pojo.config.ModerationConfig;
import com.vertonur.pojo.config.SystemContextConfig;
import com.vertonur.pojo.config.UserConfig;
import com.vertonur.service.ForumService;
import com.vertonur.service.ForumzoneService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;
import com.vertonur.util.ForumCommonUtil.PageType;
import com.vertonur.util.PermissionUtils;

@Controller
public class ForumController {

	@RequestMapping(value = { "/", "/forums" }, method = RequestMethod.GET)
	public String getForumList(HttpServletRequest request) {
		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute(Constants.FORUMZONES, forumzones);

		UserService userService = new UserService();
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		User user = null;
		if (userSession.isLogin())
			user = userService.getUserById(userSession.getUserId());
		if (user != null) {
			ForumService forumService = new ForumService();
			for (Forumzone forumzone : forumzones) {
				List<Forum> forums = forumzone.getForums();
				for (Forum forum : forums) {
					forumService.hasNewTopicsToUser(forum, user);
				}
			}
		}

		SystemContextService service = SystemContextService.getService();
		SystemState state = service.getState();
		ForumCommonUtil.setPageSeo(request, null, null, PageType.HOME_PAGE);
		request.setAttribute("systemState", state);
		request.setAttribute("loginUserSessions", service.getLoginSessions());
		return "default/user/forum/forum_list";
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String showAdminHome(HttpServletRequest request) {

		request.setAttribute("servicePath", "admin/statistic");
		HttpSession session = request.getSession();
		ServletContext context = request.getServletContext();
		session.setAttribute("enableOperateGroup",
				PermissionUtils.checkOperateGroupPermission(context));
		session.setAttribute("enableOperateUser",
				PermissionUtils.checkOperateUserPermission(context));
		session.setAttribute("enableOperateForumzone",
				PermissionUtils.checkOperateForumzonePermission(context));
		session.setAttribute("enableOperateForum",
				PermissionUtils.checkOperateForumPermission(context));
		session.setAttribute("enableConfigRuntimeParameter",
				PermissionUtils.checkConfigRuntimeParameterPermission(context));
		session.setAttribute("enableOperateRanking",
				PermissionUtils.checkOperateRankingPermission(context));
		session.setAttribute("enableConfigPoints",
				PermissionUtils.checkConfigPointsPermission(context));
		session.setAttribute("enableConfigBackendPermission",
				PermissionUtils.checkConfigPointsPermission(context));
		return "default/admin/admin_index";
	}

	@RequestMapping(value = "/admin/statistic", method = RequestMethod.GET)
	public String getForumStatistic(HttpServletRequest request) {

		SystemContextService service = SystemContextService.getService();
		SystemState state = service.getState();

		request.setAttribute("rspNum", state.getCommentNum());
		request.setAttribute("rspsPerDay", state.getCommentPerDay());

		request.setAttribute("topicNum", state.getInfoNum());
		request.setAttribute("topicsPerDay", state.getInfoPerDay());

		request.setAttribute("userNum", state.getUserNum());
		request.setAttribute("usersPerDay", state.getUserPerDay());

		SystemContextService contextService = SystemContextService.getService();
		request.setAttribute("userSessions", contextService.getLoginSessions()
				.values());
		return "default/admin/admin_welcome";
	}

	@RequestMapping(value = "/admin/menu", method = RequestMethod.GET)
	public String showAdminMenu() {
		return "default/admin/menu";
	}

	@RequestMapping(value = "/admin/forums", method = RequestMethod.GET)
	public String getAdminForumList(
			@RequestParam(required = false) boolean created,
			@RequestParam(required = false) boolean updated,
			@RequestParam(required = false) boolean deleted,
			@RequestParam(required = false) boolean msgToBeModerated,

			HttpServletRequest request) {

		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute(Constants.FORUMZONES, forumzones);
		request.setAttribute("created", created);
		request.setAttribute("updated", updated);
		request.setAttribute("deleted", deleted);
		request.setAttribute("msgToBeModerated", msgToBeModerated);
		return "default/admin/forum_list";
	}

	@RequestMapping(value = "/forums/form", method = RequestMethod.GET)
	public String showForumForm(@RequestParam(defaultValue = "0") int forumId,
			HttpServletRequest request) {

		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute(Constants.FORUMZONES, forumzones);

		if (forumId != 0) {
			ForumService forumService = new ForumService();
			Forum forum = forumService.getForumById(forumId);
			request.setAttribute("forum", forum);

			request.setAttribute("servicePath", "/admin/forum/edit");
		} else
			request.setAttribute("servicePath", "/admin/forum/create");

		return "default/admin/forum_form";
	}

	@RequestMapping(value = "/forums/form", method = RequestMethod.POST)
	public String createForum(@Valid Forum forum,
			@RequestParam int forumzoneId, HttpServletRequest request) {

		forum.setCreatedTime(new Date());
		forum.setStatistician(new ForumStatistician());

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService service = new UserService();
		Admin admin = (Admin) service.getUserById(userSession.getUserId());
		forum.setCreator(admin);

		ForumService forumService = new ForumService();
		if (forumService.saveForum(forumzoneId, forum) != null) {
			return "redirect:/admin/forums?created=true";
		} else {
			request.setAttribute("failed", true);
			return "default/admin/forum_form";
		}
	}

	@RequestMapping(value = "/forums", method = RequestMethod.DELETE)
	public String deleteForum(@RequestParam int[] forumIds,
			HttpServletRequest request) {

		ForumService forumService = new ForumService();
		for (int forumId : forumIds) {
			Forum forum = forumService.getForumById(forumId);
			forum.setDeprecated(true);
			try {
				forumService.updateForum(forum);
			} catch (CategoryModerationListNotEmptyException e) {
				return "redirect:/admin/forums?msgToBeModerated=true";
			}
		}

		return "redirect:/admin/forums?deleted=true";
	}

	@RequestMapping(value = "/forums/{forumId}", method = RequestMethod.PUT)
	public String updateForum(@Valid Forum forum, @PathVariable int forumId,
			@RequestParam int forumzoneId, HttpServletRequest request) {

		ForumService forumService = new ForumService();
		Forum savedForum = (Forum) forumService.getForumById(forumId);
		savedForum.setName(forum.getName());
		savedForum.setModerated(forum.isModerated());
		savedForum.setDescription(forum.getDescription());
		savedForum.setPriority(forum.getPriority());

		forumService.changeForumzone(forumzoneId, savedForum);
		try {
			forumService.updateForum(savedForum);
		} catch (CategoryModerationListNotEmptyException e) {
			request.setAttribute("msgToBeModerated", true);
			return "default/admin/forum_form";
		}

		return "redirect:/admin/forums?updated=true";
	}

	@RequestMapping(value = "/system", method = RequestMethod.GET)
	public String showSystemConfigForm(HttpServletRequest request) {

		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		InfoConfig infoConfig = service.getInfoConfig();
		request.setAttribute("topicsPerPage", infoConfig.getInfoPgnOffset());
		request.setAttribute("newTopicInterval",
				infoConfig.getNewInfoInterval());
		request.setAttribute("hotTopicDef", infoConfig.getHottestInfoGeCmts());
		request.setAttribute("recentTopicPageNum",
				infoConfig.getRecentInfoPgnOffset());

		CommentConfig commentConfig = service.getCommentConfig();
		request.setAttribute("rspsPerPage", commentConfig.getCmtPgnOffset());
		request.setAttribute("newRspInterval",
				commentConfig.getNewCmtInterval());
		UserConfig userConfig = service.getUserConfig();
		request.setAttribute("usersPerPage", userConfig.getUsrPgnOffset());
		request.setAttribute("registrationEnabled",
				userConfig.isRegistrationEnabled());
		request.setAttribute("avatarSize", userConfig.getAvatarSize());
		request.setAttribute("avatarHeight", userConfig.getAvatarHeight());
		request.setAttribute("avatarWidth", userConfig.getAvatarWidth());
		request.setAttribute("notifyUserOnNewRsp",
				userConfig.isNotifyAuthorOnNewCmt());
		request.setAttribute("requireAuthEmail",
				userConfig.isRequireNewUserAuthEmail());

		SystemContextConfig systemContextConfig = service
				.getSystemContextConfig();
		int sessionTiming = systemContextConfig.getSessionTiming();
		long loginSessionTiming = systemContextConfig.getLoginSessionTiming();
		request.setAttribute("sessionTiming", sessionTiming);
		request.setAttribute("loginSessionTimeout", loginSessionTiming);

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		EmailConfig emailConfig = service.getEmailConfig();
		request.setAttribute("globalConfig", config);
		request.setAttribute("emailConfig", emailConfig);

		ModerationConfig moderationConfig = service.getModerationConfig();
		request.setAttribute("mdrDigestionNum",
				moderationConfig.getDigestionNum());
		request.setAttribute("assignPendingLogInterval",
				moderationConfig.getAssignPendingLogInterval());

		return "default/admin/config_list";
	}

	@RequestMapping(value = "/system", method = RequestMethod.POST)
	public String updateSystemConfig(SystemConfigForm form,
			HttpServletRequest request) throws IOException, URISyntaxException {

		int topicsPerPage = form.getTopicsPerPage();
		int rspsPerPage = form.getRspsPerPage();
		int usersPerPage = form.getUsersPerPage();
		int hotTopicDef = form.getHotTopicDef();
		int recentTopicPageNum = form.getRecentTopicPageNum();
		long newCmtInterval = form.getNewRspInterval();
		long newInfoInterval = form.getNewTopicInterval();

		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		InfoConfig infoConfig = service.getInfoConfig();
		infoConfig.setInfoPgnOffset(topicsPerPage);
		infoConfig.setHottestInfoGeCmts(hotTopicDef);
		infoConfig.setRecentInfoPgnOffset(recentTopicPageNum);
		infoConfig.setNewInfoInterval(newInfoInterval);
		service.updateInfoConfig(infoConfig);

		CommentConfig commentConfig = service.getCommentConfig();
		commentConfig.setCmtPgnOffset(rspsPerPage);
		commentConfig.setNewCmtInterval(newCmtInterval);
		service.updateCommentConfig(commentConfig);

		boolean registrationEnabled = form.isRegistrationEnabled();
		int avatarHeight = form.getAvatarHeight();
		int avatarWidth = form.getAvatarWidth();
		int avatarSize = form.getAvatarSize();
		UserConfig userConfig = service.getUserConfig();
		userConfig.setUsrPgnOffset(usersPerPage);
		userConfig.setRequireNewUserAuthEmail(form.isRequireAuthEmail());
		userConfig.setNotifyAuthorOnNewCmt(form.isNotifyUserOnNewRsp());
		userConfig.setRegistrationEnabled(registrationEnabled);
		userConfig.setAvatarHeight(avatarHeight);
		userConfig.setAvatarWidth(avatarWidth);
		userConfig.setAvatarSize(avatarSize);
		service.updateUserConfig(userConfig);

		SystemContextConfig systemContextConfig = service
				.getSystemContextConfig();
		int sessionTimeout = form.getSessionTimeout();
		systemContextConfig.setSessionTiming(sessionTimeout);
		systemContextConfig
				.setLoginSessionTiming(form.getLoginSessionTimeout());
		service.updateSystemContextConfig(systemContextConfig);

		int mdrDigestionNum = form.getMdrDigestionNum();
		SystemContextService systemContextService = SystemContextService
				.getService();
		ModerationService moderationService = systemContextService
				.getDataManagementService(ServiceEnum.MODERATION_SERVICE);
		moderationService.setDigestionNum(mdrDigestionNum);
		int assignPendingLogInterval = form.getAssignPendingLogInterval();
		moderationService.setAssignPendingLogInterval(assignPendingLogInterval);

		processGlobalConfig(form);
		processEmailConfig(form);

		return "redirect:/system";
	}

	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void sendTestMail(SystemConfigForm form) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(form.getSmtpHost());
		email.addTo(form.getAddress(), form.getAddress());
		email.setFrom(form.getSender(), form.getSmtpUsername());
		boolean requireSSL = form.isRequireSSL();
		if (requireSSL)
			email.setSSL(requireSSL);

		if (form.isRequireAuth())
			email.setAuthentication(form.getSmtpUsername(), form.getSmtpPwd());
		email.setSubject("Forum test mail");
		email.setMsg("This is a test mail that send by admin to see if the config setting of a"
				+ " forum is correct,if mail is received, it's said that mail "
				+ "service in this forum is function well");
		email.send();
	}

	private void processGlobalConfig(SystemConfigForm form) throws IOException,
			URISyntaxException {
		String homepageLink = form.getHomepageLink();
		String forumName = form.getForumName();
		String pageTitle = form.getForumPageTitle();
		String forumDesc = form.getForumDesc();
		boolean downloadCaptchaEnabled = form.isDownloadCaptchaEnabled();
		boolean loginCaptchaEnabled = form.isLoginCaptchaEnabled();
		boolean registrationCaptchaEnabled = form
				.isRegistrationCaptchaEnabled();

		GlobalConfig config = SystemConfig.getConfig().getGlobalConfig();
		config.setHomepageLink(homepageLink);
		config.setForumName(forumName);
		config.setForumPageTitle(pageTitle);
		config.setForumDescription(forumDesc);
		config.setDownloadCaptchaEnabled(downloadCaptchaEnabled);
		config.setLoginCaptchaEnabled(loginCaptchaEnabled);
		config.setRegistrationCaptchaEnabled(registrationCaptchaEnabled);

		SystemContextService systemContextService = SystemContextService
				.getService();
		DAOManager daoManager = systemContextService.getDaoManager();
		ConfigDAO<GlobalConfig, Integer> dao = daoManager
				.getExtendedConfigDAO(GlobalConfig.class);
		dao.updateConfig(config);
	}

	private void processEmailConfig(SystemConfigForm form) throws IOException,
			URISyntaxException {
		RuntimeParameterService service = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		EmailConfig config = service.getEmailConfig();
		config.setSender(form.getSender());
		config.setSmtpHost(form.getSmtpHost());
		config.setSmtpPort(form.getSmtpPort());
		config.setRequireAuth(form.isRequireAuth());
		config.setRequireSSL(form.isRequireSSL());
		config.setSmtpUsername(form.getSmtpUsername());
		config.setSmtpPwd(form.getSmtpPwd());
		config.setEmailFormat(form.getEmailFormat());

		service.updateEmailConfig(config);
	}
}
