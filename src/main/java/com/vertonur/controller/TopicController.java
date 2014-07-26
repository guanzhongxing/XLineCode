package com.vertonur.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

@Controller
public class TopicController {

	@RequestMapping(value = "/forums/{forumId}", method = RequestMethod.GET)
	public String getTopicList(@PathVariable int forumId,
			HttpServletRequest request) throws IllegalAccessException,
			InstantiationException, InvocationTargetException,
			NoSuchMethodException {

		ForumService forumService = new ForumService();
		Forum forum = forumService.getForumById(forumId);

		String startStr = (String) request.getParameter("start");
		int paginationStart = ForumCommonUtil
				.strToIntTransitionBuffer(startStr);

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		InfoService infoService = new InfoService();
		List<Topic> topics = infoService.getTopicsByForum(
				userSession.getUserId(), forumId, paginationStart);

		int forumzoneId = forum.getForumzone().getId();
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

		request.setAttribute(
				"enableNewTopic",
				PermissionUtils.checkTopicPostPermission(
						request.getServletContext(), forumId));
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				paginationStart, request.getRequestURI()+"?", CxtType.TOPIC);
		request.setAttribute("pageCxt", pageCxt);

		return "default/user/topic/forum_topic_list";
	}
}
