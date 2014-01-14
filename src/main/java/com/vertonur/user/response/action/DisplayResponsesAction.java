package com.vertonur.user.response.action;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.config.AttachmentConfig;
import com.vertonur.pojo.config.UserConfig;
import com.vertonur.service.ForumzoneService;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;
import com.vertonur.util.PermissionUtils;
import com.vertonur.util.ForumCommonUtil.PageType;

public final class DisplayResponsesAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String topicIdStr = request.getParameter("topicId");
		String forumIdStr = request.getParameter("forumId");
		int forumId = Integer.parseInt(forumIdStr);
		InfoService infoService = new InfoService();
		Topic topic;
		if (topicIdStr != null && !"".equals(topicIdStr)) {
			topic = infoService.getTopicById(forumId,
					Integer.parseInt(topicIdStr));
		} else {
			topic = (Topic) request.getAttribute(Constants.CURRENT_TOPIC);
		}

		String rspIdStr = request.getParameter("rspId");
		int paginationStart = 0;
		if (rspIdStr != null) {
			paginationStart = infoService.getRspPageIndex(Integer
					.parseInt(rspIdStr));
		} else {
			String startStr = (String) request.getParameter("start");
			paginationStart = ForumCommonUtil
					.strToIntTransitionBuffer(startStr);
		}

		List<Response> responses = infoService.getResponsesByTopic(topic,
				paginationStart);
		request.setAttribute(Constants.RESPONSES, responses);
		request.setAttribute(Constants.CURRENT_TOPIC, topic);

		String iFrame = request.getParameter("iFrame");
		if ("true".equals(iFrame))
			return mapping.findForward("ToTopicReviewPage");
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

			String recordStatistic = request.getParameter("recordStatistic");
			if (!"false".equals(recordStatistic)) {
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
					paginationStart, mapping.getPath().split("/")[1]
							+ ".do?forumId=" + forum.getId() + "&topicId="
							+ topic.getId(), CxtType.RESPONSE);
			request.setAttribute("pageCxt", pageCxt);

			AttachmentConfig attmConfig = service.getDataManagementService(
					ServiceEnum.RUNTIME_PARAMETER_SERVICE)
					.getAttachmentConfig();
			request.setAttribute("displayThumbShowBox",
					attmConfig.getThumbEnabled());
			int userId = userSession.getUserId();
			request.setAttribute("userId", userId);
			checkPermissions(forumId, request, topic);

			boolean enableTopicLock;
			if (topic.getAuthor().getId() == userId)
				enableTopicLock = true;
			else
				enableTopicLock = PermissionUtils.checkTopicLockPermission(
						getServlet().getServletContext(), forumId);
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
			return mapping.findForward("ToDisplayResponsesPage");
		}
	}

	private void checkPermissions(int forumId, HttpServletRequest request,
			Topic topic) {
		ServletContext servletContext = getServlet().getServletContext();
		request.setAttribute("enableNewRsp", PermissionUtils
				.checkResponsePostPermission(servletContext, topic));

		request.setAttribute("enableDownloadAttm", PermissionUtils
				.checkAttachmentDownloadPermission(servletContext, forumId));

		request.setAttribute("enableModeratorEdition", PermissionUtils
				.checkModeratorEditionPermission(getServlet()
						.getServletContext(), forumId));
		request.setAttribute("enableModeratorDeletion", PermissionUtils
				.checkModeratorDeletionPermission(getServlet()
						.getServletContext(), forumId));
		request.setAttribute("enableTopicMovement", PermissionUtils
				.checkMoveTopicPermission(getServlet().getServletContext(),
						forumId));
	}
}
