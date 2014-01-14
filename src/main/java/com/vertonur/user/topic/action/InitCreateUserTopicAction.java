package com.vertonur.user.topic.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Topic;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.User;
import com.vertonur.pojo.config.AttachmentConfig;
import com.vertonur.service.ForumService;
import com.vertonur.service.InfoService;
import com.vertonur.session.UserSession;
import com.vertonur.util.PermissionUtils;

public final class InitCreateUserTopicAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		saveToken(request);
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		InfoService infoService = new InfoService();
		if (infoService.isInNewTopicInterval(userSession.getSessionId())) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.topic.post.within.time.limition"));
			saveMessages(request, messages);

			return mapping.getInputForward();
		}

		int forumId = Integer.parseInt(request.getParameter("forumId"));
		String edit = request.getParameter("edit");
		if ("true".equals(edit)) {
			Topic topic = infoService.getTopicById(forumId,
					Integer.parseInt(request.getParameter("topicId")));
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
		UserService userService = systemContextService
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		User user = userService.getUserById(userSession.getUserId());
		if (PermissionUtils.checkAttachmentUploadPermission(getServlet()
				.getServletContext(), forumId)
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

		int forumzoneId = Integer.parseInt(request.getParameter("forumzoneId"));
		ForumService service = new ForumService();
		Forum forum = service.getForumById(forumzoneId, forumId);
		request.setAttribute("forumName", forum.getName());

		if (userSession.isAdmin()) {
			request.setAttribute("showTopicType", true);
			request.setAttribute("enableAnnouncement", true);
		}
		return mapping.findForward("ToCreateUserTopicPage");
	}
}
