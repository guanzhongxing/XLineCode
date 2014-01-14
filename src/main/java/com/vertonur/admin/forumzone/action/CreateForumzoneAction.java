package com.vertonur.admin.forumzone.action;

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

import com.vertonur.admin.forumzone.form.ForumzoneForm;
import com.vertonur.bean.Admin;
import com.vertonur.bean.Forumzone;
import com.vertonur.constants.Constants;
import com.vertonur.service.ForumzoneService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;

public class CreateForumzoneAction extends Action {

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
		String forumzoneName = forumzoneForm.getForumzoneName();
		String forumzoneDescription = forumzoneForm.getForumzoneDescription();
		boolean moderated = forumzoneForm.isModerate();
		int priority = forumzoneForm.getPriority();

		Forumzone forumzone = new Forumzone();
		forumzone.setDescription(forumzoneDescription);
		forumzone.setName(forumzoneName);
		forumzone.setCreatedTime(new Date());
		forumzone.setModerated(moderated);
		forumzone.setPriority(priority);

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService service = new UserService();
		Admin author = (Admin) service.getUserById(userSession.getUserId());
		forumzone.setCreator(author);

		ForumzoneService forumzoneService = new ForumzoneService();
		if (forumzoneService.saveForumzone(forumzone) != null) {
			resetToken(request);
			ActionMessages messages = new ActionMessages();
			messages.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage(
							"message.admin.forumzone.action.CreateForumzoneAction.create.succeed"));
			saveMessages(request, messages);
			return mapping.findForward("ForunzoneList");
		} else {
			ActionMessages errors = new ActionMessages();
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage(
							"error.admin.forumzone.action.CreateForumzoneAction.create.failed"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}
	}
}