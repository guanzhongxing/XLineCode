package com.vertonur.admin.forum.action;

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

import com.vertonur.admin.forum.form.ForumForm;
import com.vertonur.bean.Admin;
import com.vertonur.bean.Forum;
import com.vertonur.bean.ForumStatistician;
import com.vertonur.constants.Constants;
import com.vertonur.service.ForumService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;

public final class CreateForumAction extends Action {

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
		String forumName = castedForm.getForumName();
		String forumDescription = castedForm.getForumDescription();
		boolean moderated = castedForm.isModerated();
		int priority = castedForm.getPriority();

		Forum forum = new Forum();
		forum.setName(forumName);
		forum.setDescription(forumDescription);
		forum.setCreatedTime(new Date());
		forum.setModerated(moderated);
		forum.setStatistician(new ForumStatistician());
		forum.setPriority(priority);

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService service = new UserService();
		Admin admin = (Admin) service.getUserById(userSession.getUserId());
		forum.setCreator(admin);

		ForumService forumService = new ForumService();
		int forumzoneId = castedForm.getNewForumzoneId();
		if (forumService.saveForum(forumzoneId, forum) != null) {
			resetToken(request);
			ActionMessages messages = new ActionMessages();
			messages.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage(
							"message.admin.forum.action.CreateForumAction.create.succeed"));
			saveMessages(request, messages);
			return mapping.findForward("ForumList");
		} else {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.admin.forum.action.CreateForumAction.create.failed"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
	}
}