package com.vertonur.admin.forumzone.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.admin.forumzone.form.ForumzoneForm;
import com.vertonur.bean.Forumzone;
import com.vertonur.dms.exception.CategoryModerationListNotEmptyException;
import com.vertonur.dms.exception.DeptModerationListNotEmptyException;
import com.vertonur.service.ForumzoneService;

public class EditForumzoneAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		ForumzoneForm forumzoneForm = (ForumzoneForm) form;
		int forumzoneId = forumzoneForm.getForumzoneId();
		String forumzoneName = forumzoneForm.getForumzoneName();
		String forumzoneDescription = forumzoneForm.getForumzoneDescription();
		boolean moderated = forumzoneForm.isModerate();
		int priority = forumzoneForm.getPriority();

		ForumzoneService forumzoneService = new ForumzoneService();
		Forumzone forumzone = forumzoneService.getForumzoneById(forumzoneId);
		forumzone.setName(forumzoneName);
		forumzone.setDescription(forumzoneDescription);
		forumzone.setModerated(moderated);
		forumzone.setPriority(priority);

		try {
			forumzoneService.updateForumzone(forumzone);
		} catch (DeptModerationListNotEmptyException e) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"msg.pending.to.modernate"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		} catch (CategoryModerationListNotEmptyException e) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"msg.pending.to.modernate"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		resetToken(request);
		ActionMessages messages = new ActionMessages();
		messages.add(
				ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage(
						"message.admin.forumzone.action.EditForumzoneAction.edit.succeed"));
		saveMessages(request, messages);
		return mapping.findForward("ForunzoneList");
	}
}
