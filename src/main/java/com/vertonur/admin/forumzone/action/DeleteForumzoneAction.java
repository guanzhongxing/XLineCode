package com.vertonur.admin.forumzone.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.admin.forumzone.form.ForumzoneForm;
import com.vertonur.bean.Forumzone;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.dms.exception.CategoryModerationListNotEmptyException;
import com.vertonur.dms.exception.DeptModerationListNotEmptyException;
import com.vertonur.service.ForumzoneService;

public class DeleteForumzoneAction extends OperactionCheckAction {

	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		ForumzoneForm forumzoneForm = (ForumzoneForm) form;
		int[] ids = forumzoneForm.getForumzoneIds();

		ForumzoneService forumzoneService = new ForumzoneService();
		for (int id : ids) {
			Forumzone forumzone = forumzoneService.getForumzoneById(id, false);
			forumzone.setDeprecated(true);
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
		}

		ActionMessages messages = new ActionMessages();
		messages.add(
				ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage(
						"message.admin.forumzone.action.DeleteForumzoneAction.delete.succeed"));
		saveMessages(request, messages);
		return mapping.findForward("ForunzoneList");
	}
}