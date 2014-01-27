package com.vertonur.user.topic.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.config.AttachmentConfig;

public class UserTopicForm extends ValidatorForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int topicId;
	private String title;
	private String content;
	private boolean editted;
	private String attmComment;
	private String moderationReason;
	private boolean fromModeration;
	private String infoType = "NORMAL";
	private String CKEditorFuncNum;
	private int[] attachmentIds;

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

	private int forumId;
	private String forumName;
	private int forumzoneId;

	private FormFile upload;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getForumName() {
		return forumName;
	}

	public void setAttmComment(String attmComment) {
		this.attmComment = attmComment;
	}

	public String getAttmComment() {
		return attmComment;
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		AttachmentConfig attachmentConfig = SystemContextService
				.getService()
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE)
				.getAttachmentConfig();
		long maxAttmSize = attachmentConfig.getMaxSize();
		ActionErrors errors = super.validate(arg0, arg1);
		if (forumId == -1)
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.admin.topic.form.TopicForm.forumId.required"));
		if (upload != null && upload.getFileSize() != 0
				&& upload.getFileSize() > maxAttmSize) {
			arg1.setAttribute("fileInvalid", true);
			arg1.setAttribute("maxSize", maxAttmSize / 1024 / 1024);
			errors = new ActionErrors();
			errors.add("mock", new ActionMessage("mock"));
		}

		if (fromModeration
				&& (moderationReason == null || moderationReason.equals("")))
			errors.add("moderationReason", new ActionMessage(
					"error.form.field.required"));

		if (errors != null)
			return errors;
		return null;
	}

	public int getForumzoneId() {
		return forumzoneId;
	}

	public void setForumzoneId(int forumzoneId) {
		this.forumzoneId = forumzoneId;
	}

	public boolean isEditted() {
		return editted;
	}

	public void setEditted(boolean editted) {
		this.editted = editted;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getCKEditorFuncNum() {
		return CKEditorFuncNum;
	}

	public void setCKEditorFuncNum(String cKEditorFuncNum) {
		CKEditorFuncNum = cKEditorFuncNum;
	}

	public FormFile getUpload() {
		return upload;
	}

	public void setUpload(FormFile upload) {
		this.upload = upload;
	}

	public int[] getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(int[] attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

}