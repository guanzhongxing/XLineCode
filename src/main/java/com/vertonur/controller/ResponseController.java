package com.vertonur.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Forumzone;
import com.vertonur.bean.Response;
import com.vertonur.bean.Topic;
import com.vertonur.bean.TopicStatistician;
import com.vertonur.bean.User;
import com.vertonur.bean.UserReadTopic;
import com.vertonur.bean.config.GlobalConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.AttachmentService;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.AttachmentSizeExceedException;
import com.vertonur.dms.exception.SavingCommentToLockedInfoException;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.ModerationLog.ModerationStatus;
import com.vertonur.pojo.config.AttachmentConfig;
import com.vertonur.pojo.config.UserConfig;
import com.vertonur.security.exception.InsufficientPermissionException;
import com.vertonur.service.ForumzoneService;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;
import com.vertonur.util.ForumCommonUtil.PageType;
import com.vertonur.util.PermissionUtils;

@Controller
public class ResponseController {

	@RequestMapping(value = "/forums/topics/{topicId}", method = RequestMethod.GET)
	public String getResponseList(@PathVariable int topicId,
			@RequestParam(required = false) boolean iFrame,
			@RequestParam(required = false) boolean recordStatistic,
			@RequestParam(defaultValue = "0") int start,
			HttpServletRequest request) throws IllegalAccessException,
			InstantiationException, InvocationTargetException,
			NoSuchMethodException {

		InfoService infoService = new InfoService();
		Topic topic;
		if (topicId != 0) {
			topic = infoService.getTopicById(topicId);
		} else {
			topic = (Topic) request.getAttribute(Constants.CURRENT_TOPIC);
		}

		List<Response> responses = infoService
				.getResponsesByTopic(topic, start);
		request.setAttribute(Constants.RESPONSES, responses);
		request.setAttribute(Constants.CURRENT_TOPIC, topic);

		if (iFrame)
			return "default/user/response/topic_review_list";
		else {
			HttpSession session = request.getSession(false);
			UserSession userSession = (UserSession) session
					.getAttribute(Constants.USER_SESSION);
			boolean isUser = userSession.isLogin();

			Forum forum = topic.getForum();
			request.setAttribute("forum", forum);
			request.setAttribute("forumzoneId", forum.getForumzone().getId());
			SystemContextService service = SystemContextService.getService();
			request.setAttribute("loginUserSessions",
					service.getLoginSessions());

			if (!recordStatistic) {
				TopicStatistician statistician = topic.getStatistician();
				statistician.addClickThroughRate();
				infoService
						.updateTopicStatistician(topic.getId(), statistician);

				if (isUser) {
					UserService userService = new UserService();
					User user = userService
							.getUserById(userSession.getUserId());
					boolean read = userService.isReadTopic(user, topic);
					if (!read) {
						UserReadTopic userReadTopic = new UserReadTopic(user,
								topic, new Date());
						infoService.saveUserReadTopic(userReadTopic);
					}
				}
			}

			ForumzoneService forumzoneService = new ForumzoneService();
			List<Forumzone> forumzones = forumzoneService.getForumzones();
			request.setAttribute("forumzones", forumzones);

			long paginationSize = infoService.getResponseNumByTopic(topic);
			PaginationContext pageCxt = new PaginationContext(paginationSize,
					start, request.getRequestURI() + "?", CxtType.RESPONSE);
			request.setAttribute("pageCxt", pageCxt);

			AttachmentConfig attmConfig = service.getDataManagementService(
					ServiceEnum.RUNTIME_PARAMETER_SERVICE)
					.getAttachmentConfig();
			request.setAttribute("displayThumbShowBox",
					attmConfig.getThumbEnabled());
			int userId = userSession.getUserId();
			request.setAttribute("userId", userId);
			int forumId = topic.getForum().getId();
			checkPermissions(forumId, request, topic);

			boolean enableTopicLock;
			if (topic.getAuthor().getId() == userId)
				enableTopicLock = true;
			else
				enableTopicLock = PermissionUtils.checkTopicLockPermission(
						request.getServletContext(), forumId);
			request.setAttribute("enableTopicLock", enableTopicLock);

			GlobalConfig globalConfig = SystemConfig.getConfig()
					.getGlobalConfig();
			request.setAttribute("downloadCaptchaEnabled",
					globalConfig.isDownloadCaptchaEnabled());

			ForumCommonUtil.setPageSeo(request, topic.getSubject(),
					topic.getContent(), PageType.INFO_PAGE);

			RuntimeParameterService runtimeParameterService = SystemContextService
					.getService().getDataManagementService(
							ServiceEnum.RUNTIME_PARAMETER_SERVICE);
			UserConfig config = runtimeParameterService.getUserConfig();
			request.setAttribute("avatarHeight", config.getAvatarHeight());
			request.setAttribute("avatarWidth", config.getAvatarWidth());
			return "default/user/response/response_list";
		}
	}

	private void checkPermissions(int forumId, HttpServletRequest request,
			Topic topic) {
		ServletContext servletContext = request.getServletContext();
		request.setAttribute("enableNewRsp", PermissionUtils
				.checkResponsePostPermission(servletContext, topic));

		request.setAttribute("enableDownloadAttm", PermissionUtils
				.checkAttachmentDownloadPermission(servletContext, forumId));

		request.setAttribute("enableModeratorEdition", PermissionUtils
				.checkModeratorEditionPermission(servletContext, forumId));
		request.setAttribute("enableModeratorDeletion", PermissionUtils
				.checkModeratorDeletionPermission(servletContext, forumId));
		request.setAttribute("enableTopicMovement", PermissionUtils
				.checkMoveTopicPermission(servletContext, forumId));
	}

	@RequestMapping(value = "/forums/topics/{topicId}/response", method = RequestMethod.GET)
	public String showResponseForm(@PathVariable int topicId,
			@RequestParam(defaultValue = "0") int responseId,
			@RequestParam(required = false) boolean quote,
			@RequestParam(required = false) boolean edit,
			HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		InfoService infoService = new InfoService();

		// check new rsp interval
		if (infoService.isInNewRspInterval(userSession.getSessionId())) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.rsp.post.within.time.limition"));
			// result.addError(error);
			return "default/user/response/response_list";
		}
		// end

		Topic topic = infoService.getTopicById(topicId);
		Forum forum = topic.getForum();

		request.setAttribute("topic", topic);
		request.setAttribute("forum", forum);

		Response rsp = null;
		if (responseId != 0)
			rsp = infoService.getResponseById(responseId);

		if (quote) {
			if (responseId != 0)
				request.setAttribute("quotedMsg", rsp);
			else
				request.setAttribute("quotedMsg", topic);

		} else if (edit && responseId != 0) {
			request.setAttribute("edittedRsp", rsp);
			request.setAttribute("editted", true);

			if (userSession.isModerator()
					&& (userSession.getUserId() != rsp.getAuthor().getId()))
				request.setAttribute("fromModeration", true);
		}

		UserService userService = new UserService();
		User theCurrentUser = userService.getUserById(userSession.getUserId());
		int forumId = forum.getId();
		if (PermissionUtils.checkAttachmentUploadPermission(
				request.getServletContext(), forumId)
				&& theCurrentUser.isAttmEnabled()) {
			request.setAttribute("attachmentsEnabled", true);

			AttachmentConfig config = SystemContextService
					.getService()
					.getDataManagementService(
							ServiceEnum.RUNTIME_PARAMETER_SERVICE)
					.getAttachmentConfig();
			int maxAttachments = config.getMaxAttmtNum();
			request.setAttribute("maxAttachments", maxAttachments);
			request.setAttribute("maxAttmSize", config.getMaxSize());
		}

		return "default/user/response/response_form";
	}

	@RequestMapping(value = "/forums/topics/{topicId}/response", method = RequestMethod.POST)
	public String createResponse(@PathVariable int topicId,
			@Valid Response response,
			@RequestParam(required = false) MultipartFile upload,
			@RequestParam(required = false) String comment,
			@RequestParam(required = false) Integer[] attachmentIds,
			HttpServletRequest request) throws AttachmentSizeExceedException,
			IOException {

		InfoService infoService = new InfoService();
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = new UserService();
		User user = userService.getUserById(userSession.getUserId());
		SystemContextService systemContextService = SystemContextService
				.getService();
		AttachmentService attachmentService = systemContextService
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);

		// set up data of a new response
		response.setCreatedTime(new Date());
		response.setLatestOne(true);
		response.setAuthor(user);

		Topic topic = infoService.getTopicById(topicId);
		response.setTopic(topic);
		// end

		ModerationStatus status = null;
		try {
			status = infoService.saveResponse(response);
			if (upload != null)
				attachmentService.uploadAttachment(upload.getInputStream(),
						upload.getContentType(), upload.getOriginalFilename(),
						new Long(upload.getSize()).longValue(), comment,
						user.getCore(), response.getCore());

		} catch (SavingCommentToLockedInfoException e) {
			systemContextService.rollbackTransaction();
			request.setAttribute("saveCmtToLockedInfo", true);
			return "default/user/message";
		}

		if (attachmentIds != null)
			for (int attachmentId : attachmentIds)
				attachmentService.confirmEmbeddedImageUpload(topic.getCore(),
						attachmentId);

		// TODO:调试审核功能
		if (ModerationStatus.PENDING.equals(status)
				|| ModerationStatus.DEFERRED.equals(status)) {
			setModerationInfo(topic, request);
			return "redirect:/messages?responseModeration";
		}
		userSession.setLastCmtDate(new Date());

		return "redirect:/forums/topics/" + topicId + "?recordStatistic=false";
	}

	private void setModerationInfo(Topic topic, HttpServletRequest request) {
		request.setAttribute("container", topic.getSubject());
		request.setAttribute("dispatchPath", "displayResponses.do?forumId="
				+ topic.getForum().getId() + "&&topicId=" + topic.getId());
	}

	@RequestMapping(value = "/forums/topics/{topicId}/{responseId}", method = RequestMethod.PUT)
	public String updateResponse(@PathVariable int topicId,
			@PathVariable int responseId, @Valid Response response,
			@RequestParam(required = false) MultipartFile upload,
			@RequestParam(required = false) String comment,
			@RequestParam(required = false) Integer[] attachmentIds,
			@RequestParam(required = false) boolean fromModeration,
			@RequestParam(required = false) String moderationReason,
			HttpServletRequest request) throws AttachmentSizeExceedException,
			IOException {

		InfoService infoService = new InfoService();
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = new UserService();
		User user = userService.getUserById(userSession.getUserId());
		SystemContextService systemContextService = SystemContextService
				.getService();
		AttachmentService attachmentService = systemContextService
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);
		Response rsp = infoService.getResponseById(responseId);
		rsp.setSubject(response.getSubject());
		String originalContent = rsp.getContent();
		rsp.setContent(response.getContent());

		try {
			if (fromModeration) {
				infoService.modifyRsp(originalContent, rsp,
						userSession.getUserId(), moderationReason);
			} else {
				// TODO:调试审核功能
				ModerationStatus status = infoService.updateResponse(rsp);
				if (ModerationStatus.PENDING.equals(status)
						|| ModerationStatus.DEFERRED.equals(status)) {
					Topic topic = rsp.getTopic();
					setModerationInfo(topic, request);
					return "redirect:/messages?responseModeration";
				}
			}

			if (upload != null)
				attachmentService.uploadAttachment(upload.getInputStream(),
						upload.getContentType(), upload.getOriginalFilename(),
						new Long(upload.getSize()).longValue(), comment,
						user.getCore(), rsp.getCore());

			if (attachmentIds != null)
				for (int attachmentId : attachmentIds)
					attachmentService.confirmEmbeddedImageUpload(rsp.getCore(),
							attachmentId);

		} catch (InsufficientPermissionException ex) {
			systemContextService.rollbackTransaction();
			request.setAttribute("insufficientPermission", true);
			return "default/user/message";
		}

		return "redirect:/forums/topics/" + topicId + "?recordStatistic=false";
	}

	@RequestMapping(value = "/forums/topics/responses", method = RequestMethod.GET)
	public String getUserSpecifiedResponseList(@RequestParam int userId,
			@RequestParam(defaultValue = "0") int start,
			HttpServletRequest request) {

		UserService userService = new UserService();
		User theUser = userService.getUserById(userId);
		request.setAttribute("displayedUser", theUser);

		// override system default pagination offset while offset of user
		// specified response is available
		InfoService infoService = new InfoService();
		List<Response> responses = infoService.getResponsesByUser(theUser,
				start);
		request.setAttribute("userSpecifiedResponses", responses);

		long paginationSize = infoService.getResponseNumByUser(theUser);
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				start, request.getRequestURI() + "?userId=" + userId,
				CxtType.RESPONSE);
		request.setAttribute(PaginationContext.PAGE_CXT, pageCxt);

		return "default/user/response/user_specified_responses";
	}

	@RequestMapping(value = "/forums/topics/{topicId}/{responseId}", method = RequestMethod.GET)
	public String getResponse(@PathVariable int topicId,
			@PathVariable int responseId, HttpServletRequest request)
			throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException {

		InfoService infoService = new InfoService();
		int start = infoService.getRspPageIndex(responseId);

		return getResponseList(topicId, false, true, start, request);
	}

	@RequestMapping(value = "/forums/topics/{topicId}/{responseId}", method = RequestMethod.DELETE)
	public String deleteResponse(@PathVariable int topicId,
			@PathVariable int responseId, String reason,
			HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		InfoService infoService = new InfoService();
		infoService.deleteRsp(responseId, userSession, reason);
		return "redirect:/forums/topics/" + topicId;
	}
}
