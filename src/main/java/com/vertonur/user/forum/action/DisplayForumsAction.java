package com.vertonur.user.forum.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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

public class DisplayForumsAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// used to set taken for the login area of forum_list.jsp
		saveToken(request);
		// end

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
		return mapping.findForward("ToForumListPage");
	}
}
