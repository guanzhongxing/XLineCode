package com.vertonur.user.response.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.config.AttachmentConfig;

public class ResponseForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	private int topicId;
	private int responseId;
	private String topicName;
	private String title;
	private String content;
	private int forumId;
	private String forumName;
	private boolean editted;
	private String moderationReason;
	private boolean fromModeration;

	private String attmComment;
	private FormFile upload;

	public ResponseForm() {
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getTopicId() {
		return topicId;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public void setAttmComment(String attmComment) {
		this.attmComment = attmComment;
	}

	public String getAttmComment() {
		return attmComment;
	}

	public boolean isEditted() {
		return editted;
	}

	public void setEditted(boolean editted) {
		this.editted = editted;
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = super.validate(arg0, arg1);
		AttachmentConfig config = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
				.getAttachmentConfig();
		long maxSize = config.getMaxSize();
		if (upload != null && upload.getFileSize() != 0
				&& upload.getFileSize() > maxSize) {
			arg1.setAttribute("fileInvalid", true);
			arg1.setAttribute("maxSize", maxSize / 1024 / 1024);
			errors = new ActionErrors();
			errors.add("mock", new ActionMessage("mock"));
		}

		if (editted && fromModeration) {
			if (moderationReason == null || "".equals(moderationReason)
					|| moderationReason.length() < 5)
				errors.add("moderationReason", new ActionMessage(
						"error.form.mdr.reason.mix.length"));
			else if (moderationReason.length() > 50)
				errors.add("moderationReason", new ActionMessage(
						"error.form.mdr.reason.max.length"));
		}

		if (errors != null)
			return errors;
		return null;
	}

	public String getModerationReason() {
		return moderationReason;
	}

	public void setModerationReason(String moderationReason) {
		this.moderationReason = moderationReason;
	}

	public boolean isFromModeration() {
		return fromModeration;
	}

	public void setFromModeration(boolean fromModeration) {
		this.fromModeration = fromModeration;
	}

	public FormFile getUpload() {
		return upload;
	}

	public void setUpload(FormFile upload) {
		this.upload = upload;
	}
}