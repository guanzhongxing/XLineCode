package com.vertonur.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Forumzone;
import com.vertonur.bean.User;
import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.context.SystemContextService.SystemState;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;
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
import com.vertonur.util.PermissionUtils;
import com.vertonur.util.ForumCommonUtil.PageType;

@Controller
@RequestMapping({ "/", "/forums" })
public class ForumController {

	@RequestMapping(method = RequestMethod.GET)
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
	public String getAdminForumList(HttpServletRequest request) {

		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute(Constants.FORUMZONES, forumzones);
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
}
