package com.vertonur.user.response.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.vertonur.bean.Response;
import com.vertonur.bean.Topic;
import com.vertonur.bean.User;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.AttachmentService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.SavingCommentToLockedInfoException;
import com.vertonur.pojo.ModerationLog.ModerationStatus;
import com.vertonur.security.exception.InsufficientPermissionException;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.user.response.form.ResponseForm;

public class RespondTopicAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!isTokenValid(request)) {
			return mapping.findForward("DisplayResponses");
		}
		resetToken(request);

		InfoService infoService = new InfoService();
		ResponseForm castedForm = (ResponseForm) form;
		boolean editted = castedForm.isEditted();
		int topicId = castedForm.getTopicId();
		int forumId = castedForm.getForumId();
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = new UserService();
		User user = userService.getUserById(userSession.getUserId());
		SystemContextService systemContextService = SystemContextService
				.getService();
		AttachmentService attachmentService = systemContextService
				.getDataManagementService(ServiceEnum.ATTACHMENT_SERVICE);
		if (editted) {
			int rspId = castedForm.getResponseId();
			Response rsp = infoService.getResponseById(rspId);
			rsp.setSubject(castedForm.getTitle());
			String originalContent = rsp.getContent();
			rsp.setContent(castedForm.getContent());

			try {
				if (castedForm.isFromModeration()) {
					infoService.modifyRsp(originalContent, rsp,
							userSession.getUserId(),
							castedForm.getModerationReason());
				} else {
					ModerationStatus status = infoService.updateResponse(rsp);
					if (ModerationStatus.PENDING.equals(status)
							|| ModerationStatus.DEFERRED.equals(status)) {
						Topic topic = rsp.getTopic();
						setModerationInfo(topic, request);
						return mapping.findForward("MessagePage");
					}
				}

				FormFile uploadedFile = castedForm.getUpload();
				if (uploadedFile != null)
					attachmentService.uploadAttachment(
							uploadedFile.getInputStream(),
							uploadedFile.getContentType(),
							uploadedFile.getFileName(),
							new Long(uploadedFile.getFileSize()).longValue(),
							castedForm.getAttmComment(), user.getCore(),
							rsp.getCore());

			} catch (InsufficientPermissionException ex) {
				systemContextService.rollbackTransaction();
				request.setAttribute("insufficientPermission", true);
				return mapping.findForward("MessagePage");
			}
		} else {
			String title = castedForm.getTitle();
			String content = castedForm.getContent();

			// set up data of a new response
			Response rsp = new Response();
			rsp.setSubject(title);
			rsp.setContent(content);
			rsp.setCreatedTime(new Date());
			rsp.setLatestOne(true);
			rsp.setAuthor(user);

			Topic topic = infoService.getTopicById(topicId);
			rsp.setTopic(topic);
			// end

			ModerationStatus status = null;
			try {
				status = infoService.saveResponse(rsp);
				FormFile uploadedFile = castedForm.getUpload();
				if (uploadedFile != null)
					attachmentService.uploadAttachment(
							uploadedFile.getInputStream(),
							uploadedFile.getContentType(),
							uploadedFile.getFileName(),
							new Long(uploadedFile.getFileSize()).longValue(),
							castedForm.getAttmComment(), user.getCore(),
							rsp.getCore());

			} catch (SavingCommentToLockedInfoException e) {
				systemContextService.rollbackTransaction();
				request.setAttribute("saveCmtToLockedInfo", true);
				return mapping.findForward("MessagePage");
			}

			if (ModerationStatus.PENDING.equals(status)
					|| ModerationStatus.DEFERRED.equals(status)) {
				setModerationInfo(topic, request);
				return mapping.findForward("MessagePage");
			}
			userSession.setLastCmtDate(new Date());
		}

		response.sendRedirect("displayResponses.do?recordStatistic=false&topicId="
				+ topicId + "&forumId=" + forumId);
		return null;
	}

	private void setModerationInfo(Topic topic, HttpServletRequest request) {
		request.setAttribute("container", topic.getSubject());
		request.setAttribute("dispatchPath", "displayResponses.do?forumId="
				+ topic.getForum().getId() + "&&topicId=" + topic.getId());
	}
}
