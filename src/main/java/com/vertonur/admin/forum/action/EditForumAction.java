package com.vertonur.admin.forum.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.admin.forum.form.ForumForm;
import com.vertonur.bean.Forum;
import com.vertonur.dms.exception.CategoryModerationListNotEmptyException;
import com.vertonur.service.ForumService;

public final class EditForumAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		ForumForm castedForm = (ForumForm) form;
		int forumId = castedForm.getForumId();
		String forumName = castedForm.getForumName();
		String desc = castedForm.getForumDescription();
		boolean moderated = castedForm.isModerated();
		int priority = castedForm.getPriority();

		ForumService forumService = new ForumService();
		Forum forum = (Forum) forumService.getForumById(forumId);
		forum.setName(forumName);
		forum.setModerated(moderated);
		forum.setDescription(desc);
		forum.setPriority(priority);

		int forumzoneId = castedForm.getNewForumzoneId();
		if (forumzoneId != -1) {
			forumService.changeForumzone(forumzoneId, forum);
		}

		try {
			forumService.updateForum(forum);
		} catch (CategoryModerationListNotEmptyException e) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"msg.pending.to.modernate"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		resetToken(request);
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"message.admin.forum.action.EditForumAction.edit.succeed"));
		saveMessages(request, messages);
		return mapping.findForward("ForumList");
	}
}