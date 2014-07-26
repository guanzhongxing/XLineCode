package com.vertonur.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Forumzone;
import com.vertonur.bean.User;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.context.SystemContextService.SystemState;
import com.vertonur.service.ForumService;
import com.vertonur.service.ForumzoneService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;
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
}
