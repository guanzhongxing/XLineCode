package com.vertonur.user.topic.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Forumzone;
import com.vertonur.bean.Topic;
import com.vertonur.bean.User;
import com.vertonur.bean.config.RuntimeConfig;
import com.vertonur.bean.config.SystemConfig;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.AttachmentService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.Info.InfoType;
import com.vertonur.pojo.ModerationLog.ModerationStatus;
import com.vertonur.security.exception.InsufficientPermissionException;
import com.vertonur.service.ForumService;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.user.topic.form.UserTopicForm;

public class CreateUserTopicAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.findForward("DisplayTopics");
		}
		resetToken(request);

		InfoService infoService = new InfoService();
		UserTopicForm castedForm = (UserTopicForm) form;
		int forumId = castedForm.getForumId();
		int topicId = castedForm.getTopicId();
		ForumService forumService = new ForumService();
		Forum forum = forumService.getForumById(castedForm.getForumzoneId(),
				forumId, false);
		Forumzone forumzone = forum.getForumzone();
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = new UserService();
		User user = userService.getUserById(userSession.getUserId());

		InfoType infoType = InfoType.valueOf(castedForm.getInfoType());
		int[] attachmentIds = castedForm.getAttachmentIds();
		AttachmentService attachmentService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);
		if (castedForm.isEditted()) {
			Topic topic = infoService.getTopicById(forumId, topicId, false);
			String originalSubject = topic.getSubject();
			String originalContent = topic.getContent();
			InfoType originalInfoType = topic.getInfoType();
			topic.setSubject(castedForm.getTitle());
			topic.setContent(castedForm.getContent());
			topic.setInfoType(infoType);

			try {
				if (castedForm.isFromModeration()) {
					infoService.modifyTopic(originalContent, topic,
							userSession.getUserId(),
							castedForm.getModerationReason());
				} else {
					ModerationStatus status = infoService.updateTopic(topic);
					if (ModerationStatus.PENDING.equals(status)
							|| ModerationStatus.DEFERRED.equals(status)) {
						request.setAttribute("container", forum.getName());
						request.setAttribute(
								"dispatchPath",
								"displayTopics.do?forumzoneId="
										+ forumzone.getId() + "&&forumId="
										+ forum.getId() + "&&forumName="
										+ forum.getName());
						return mapping.findForward("MessagePage");
					}
				}

				FormFile uploadedFile = castedForm.getUpload();
				if (uploadedFile != null) {
					RuntimeConfig config = SystemConfig.getConfig()
							.getRuntimeConfig();
					attachmentService.uploadAttchment(
							config.getUploadFileSystem(),
							uploadedFile.getInputStream(),
							uploadedFile.getContentType(),
							config.getUploadRootFolder(),
							uploadedFile.getFileName(),
							uploadedFile.getFileSize(),
							castedForm.getAttmComment(), user.getCore(),
							topic.getCore());
				}

				if (attachmentIds != null)
					for (int attachmentId : attachmentIds)
						attachmentService.confirmEmbeddedImageUpload(
								topic.getCore(), attachmentId);

			} catch (InsufficientPermissionException ex) {
				// do nothing,just get an fresh topic from db to override
				// the cached one
				topic = infoService.getTopicById(forumId, topicId, false);
				topic.setSubject(originalSubject);
				topic.setContent(originalContent);
				topic.setInfoType(originalInfoType);

				request.setAttribute("insufficientPermission", true);
				return mapping.findForward("MessagePage");
			}

			response.sendRedirect("displayResponses.do?forumId=" + forumId
					+ "&topicId=" + topicId);
			return null;
		} else {
			String title = castedForm.getTitle();
			String content = castedForm.getContent();

			Topic topic = new Topic();
			topic.setSubject(title);
			topic.setContent(content);
			topic.setCreatedTime(new Date());
			topic.setAuthor(user);
			topic.setInfoType(infoType);
			topic.setForum(forum);
			ModerationStatus status = infoService.saveTopic(topic);

			FormFile uploadedFile = castedForm.getUpload();
			try {
				if (uploadedFile != null){
					RuntimeConfig config = SystemConfig.getConfig()
							.getRuntimeConfig();
					attachmentService.uploadAttchment(
							config.getUploadFileSystem(),
							uploadedFile.getInputStream(),
							uploadedFile.getContentType(),
							config.getUploadRootFolder(),
							uploadedFile.getFileName(),
							uploadedFile.getFileSize(),
							castedForm.getAttmComment(), user.getCore(),
							topic.getCore());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (attachmentIds != null)
				for (int attachmentId : attachmentIds)
					attachmentService.confirmEmbeddedImageUpload(
							topic.getCore(), attachmentId);

			if (ModerationStatus.PENDING.equals(status)
					|| ModerationStatus.DEFERRED.equals(status)) {
				request.setAttribute("container", forum.getName());
				request.setAttribute("dispatchPath",
						"displayTopics.do?forumzoneId=" + forumzone.getId()
								+ "&&forumId=" + forum.getId() + "&&forumName="
								+ forum.getName());
				return mapping.findForward("MessagePage");
			}

			userSession.setLastInfoDate(new Date());

			response.sendRedirect("displayTopics.do?forumId=" + forumId
					+ "&forumzoneId=" + forumzone.getId());
			return null;
		}
	}
}
