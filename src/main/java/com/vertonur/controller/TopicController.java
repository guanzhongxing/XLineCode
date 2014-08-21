package com.vertonur.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Forumzone;
import com.vertonur.bean.Topic;
import com.vertonur.bean.User;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.AttachmentService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.ModerationLog.ModerationStatus;
import com.vertonur.pojo.config.AttachmentConfig;
import com.vertonur.service.ForumService;
import com.vertonur.service.ForumzoneService;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;
import com.vertonur.util.ForumCommonUtil.PageType;
import com.vertonur.util.PermissionUtils;

@Controller
public class TopicController {

	@RequestMapping(value = "/forums/{forumId}", method = RequestMethod.GET)
	public String getTopicList(@PathVariable int forumId,
			@RequestParam(required = false) boolean intervalLimit,
			@RequestParam(defaultValue = "0") int start,
			HttpServletRequest request) throws IllegalAccessException,
			InstantiationException, InvocationTargetException,
			NoSuchMethodException {

		ForumService forumService = new ForumService();
		Forum forum = forumService.getForumById(forumId);

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		InfoService infoService = new InfoService();
		List<Topic> topics = infoService.getTopicsByForum(
				userSession.getUserId(), forumId, start);

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
				start, request.getRequestURI() + "?", CxtType.TOPIC);
		request.setAttribute("pageCxt", pageCxt);

		request.setAttribute("intervalLimit", intervalLimit);
		return "default/user/topic/forum_topic_list";
	}

	@RequestMapping(value = { "/forums/topics" }, method = RequestMethod.GET)
	public String getTopicStatisticList(
			@RequestParam(defaultValue = "recent") String mode,
			HttpServletRequest request) {

		ForumService forumService = new ForumService();
		InfoService infoService = new InfoService();
		List<Forum> forums = forumService.getForums();
		List<Forum> subForums = new ArrayList<Forum>();
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		for (Forum forum : forums) {
			List<Topic> topics;
			if ("recent".equals(mode))
				topics = infoService.getRecentTopicsByForum(
						userSession.getUserId(), forum);
			else
				topics = infoService.getHottestTopicsByForum(
						userSession.getUserId(), forum);
			if (topics.size() != 0) {
				if ("recent".equals(mode))
					forum.setRecentTopics(topics);
				else
					forum.setHotTopics(topics);
			} else
				subForums.add(forum);
		}

		if (subForums.size() != 0)
			forums.removeAll(subForums);

		ForumCommonUtil.setPageSeo(request, null, null, PageType.HOME_PAGE);
		request.setAttribute("forums", forums);
		request.setAttribute("mode", mode);

		return "default/user/topic/topic_statistic_list";
	}

	@RequestMapping(value = { "/forums/topics" }, method = RequestMethod.GET, params = "userId")
	public String getUserSpecifiedTopicList(@RequestParam int userId,
			@RequestParam(defaultValue = "0") int start,
			HttpServletRequest request) {

		UserService userService = new UserService();
		User theUser = userService.getUserById(userId);
		request.setAttribute("displayedUser", theUser);

		// override system default pagination offset while offset of user
		// specified response is available
		InfoService infoService = new InfoService();
		List<Topic> topics = infoService.getTopicsByUser(theUser, start);
		request.setAttribute("userSpecifiedTopics", topics);

		long paginationSize = infoService.getTopicNumByCreator(theUser);
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				start, request.getRequestURI() + "?userId=" + userId,
				CxtType.TOPIC);
		request.setAttribute(PaginationContext.PAGE_CXT, pageCxt);

		return "default/user/topic/user_specified_topics";
	}

	@RequestMapping(value = "/forums/{forumId}/topic", method = RequestMethod.GET)
	public String showTopicForm(@PathVariable int forumId,
			@RequestParam(required = false) boolean edit,
			@RequestParam(defaultValue = "0") int topicId,
			HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		InfoService infoService = new InfoService();
		if (infoService.isInNewTopicInterval(userSession.getSessionId()))
			return "redirect:/forums/" + forumId + "?intervalLimit=true";

		if (edit) {
			Topic topic = infoService.getTopicById(topicId);
			request.setAttribute("edittedTopic", topic);
			request.setAttribute("editted", true);
			if (userSession.getUserId() != topic.getAuthor().getId()
					&& userSession.isModerator())
				request.setAttribute("fromModeration", true);
		} else {
			request.setAttribute("editted", false);
		}

		SystemContextService systemContextService = SystemContextService
				.getService();
		com.vertonur.dms.UserService userService = systemContextService
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		com.vertonur.pojo.User user = userService.getUserById(userSession
				.getUserId());
		if (PermissionUtils.checkAttachmentUploadPermission(
				request.getServletContext(), forumId)
				&& user.isAttmEnabled()) {
			request.setAttribute("attachmentsEnabled", true);

			AttachmentConfig attachmentConfig = systemContextService
					.getDataManagementService(
							ServiceEnum.RUNTIME_PARAMETER_SERVICE)
					.getAttachmentConfig();
			request.setAttribute("maxAttachments",
					attachmentConfig.getMaxAttmtNum());
			request.setAttribute("maxAttmSize", attachmentConfig.getMaxSize());
		}

		ForumService service = new ForumService();
		Forum forum = service.getForumById(forumId);
		request.setAttribute("forumName", forum.getName());
		request.setAttribute("forumId", forumId);

		if (userSession.isAdmin()) {
			request.setAttribute("showTopicType", true);
			request.setAttribute("enableAnnouncement", true);
		}

		return "default/user/topic/topic_form";
	}

	@RequestMapping(value = "/forums/{forumId}/topic", method = RequestMethod.POST)
	public String createTopic(@Valid Topic topic, @PathVariable int forumId,
			@RequestParam(required = false) Integer[] attachmentIds,
			@RequestParam(required = false) MultipartFile upload,
			@RequestParam(required = false) String comment,
			HttpServletRequest request) {

		InfoService infoService = new InfoService();
		ForumService forumService = new ForumService();
		Forum forum = forumService.getForumById(forumId);
		Forumzone forumzone = forum.getForumzone();
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = new UserService();
		User user = userService.getUserById(userSession.getUserId());

		AttachmentService attachmentService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);

		topic.setCreatedTime(new Date());
		topic.setAuthor(user);
		topic.setForum(forum);
		ModerationStatus status = infoService.saveTopic(topic);

		try {
			if (upload != null) {
				attachmentService.uploadAttachment(upload.getInputStream(),
						upload.getContentType(), upload.getOriginalFilename(),
						upload.getSize(), comment, user.getCore(),
						topic.getCore());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (attachmentIds != null)
			for (int attachmentId : attachmentIds)
				attachmentService.confirmEmbeddedImageUpload(topic.getCore(),
						attachmentId);

//		TODO:调试审核功能
		if (ModerationStatus.PENDING.equals(status)
				|| ModerationStatus.DEFERRED.equals(status)) {
			request.setAttribute("container", forum.getName());
			request.setAttribute("dispatchPath",
					"displayTopics.do?forumzoneId=" + forumzone.getId()
							+ "&&forumId=" + forum.getId() + "&&forumName="
							+ forum.getName());
			return "redirect:/messages?topicModeration";
		}

		userSession.setLastInfoDate(new Date());

		return "redirect:/forums/" + forumId;
	}
}
