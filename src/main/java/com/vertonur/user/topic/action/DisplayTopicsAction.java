package com.vertonur.user.topic.action;

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
import com.vertonur.bean.Topic;
import com.vertonur.constants.Constants;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.service.ForumService;
import com.vertonur.service.ForumzoneService;
import com.vertonur.service.InfoService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;
import com.vertonur.util.ForumCommonUtil.PageType;
import com.vertonur.util.PermissionUtils;

public final class DisplayTopicsAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int forumId = Integer.parseInt(request.getParameter("forumId"));
		int forumzoneId = Integer.parseInt(request.getParameter("forumzoneId"));
		ForumService forumService = new ForumService();
		Forum forum = forumService.getForumById(forumzoneId, forumId);

		String startStr = (String) request.getParameter("start");
		int paginationStart = ForumCommonUtil
				.strToIntTransitionBuffer(startStr);

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		InfoService infoService = new InfoService();
		List<Topic> topics = infoService.getTopicsByForum(
				userSession.getUserId(), forumId, paginationStart);

		List<Topic> announcements = infoService.getSystemAnnouncements();
		announcements
				.addAll(infoService.getForumzoneAnnouncements(forumzoneId));
		announcements.addAll(infoService.getForumAnnouncements(forumId));
		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();

		request.setAttribute("forumzones", forumzones);
		request.setAttribute(Constants.CURRENT_FORUM, forum);
		request.setAttribute("announcements", announcements);
		request.setAttribute(Constants.TOPICS, topics);
		long paginationSize = infoService.getTopicNumByForum(forumId);

		// seo related
		ForumCommonUtil.setPageSeo(request, forum.getName(),
				forum.getDescription(), PageType.INFO_PAGE);
		// end

		request.setAttribute("enableNewTopic", PermissionUtils
				.checkTopicPostPermission(getServlet().getServletContext(),
						forumId));
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				paginationStart, mapping.getPath().split("/")[1]
						+ ".do?forumzoneId=" + forumzoneId + "&forumId="
						+ forum.getId(), CxtType.TOPIC);
		request.setAttribute("pageCxt", pageCxt);
		return mapping.findForward("ToDisplayTopicsPage");
	}
}