package com.vertonur.admin.forum.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.admin.forum.form.ForumForm;
import com.vertonur.bean.Forum;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.dms.exception.CategoryModerationListNotEmptyException;
import com.vertonur.service.ForumService;

public final class DeleteForumAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		resetToken(request);
		ForumForm castedForm = (ForumForm) form;
		String[] forumIds = castedForm.getForumIds();

		ForumService forumService = new ForumService();
		for (String forumIdStr : forumIds) {
			String[] tmp = forumIdStr.split("_");
			Forum forum = forumService.getForumById(Integer.parseInt(tmp[0]),
					Integer.parseInt(tmp[1]),false);
			forum.setDeprecated(true);
			try {
				forumService.updateForum(forum);
			} catch (CategoryModerationListNotEmptyException e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"msg.pending.to.modernate"));
				saveErrors(request, errors);
				return mapping.findForward("ForumList");
			}
		}

		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"message.admin.forum.action.DeleteForumAction.delete.succeed"));
		saveMessages(request, messages);
		return mapping.findForward("ForumList");
	}
}