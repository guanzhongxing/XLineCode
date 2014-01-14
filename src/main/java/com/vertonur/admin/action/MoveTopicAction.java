package com.vertonur.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.admin.form.MoveTopicForm;
import com.vertonur.constants.Constants;
import com.vertonur.service.InfoService;
import com.vertonur.session.UserSession;

public class MoveTopicAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		if (!isTokenValid(request)) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.doublesubmit"));
			saveErrors(request, errors);
			return mapping.getInputForward();
		}

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);

		MoveTopicForm castedForm = (MoveTopicForm) form;
		int topicId = castedForm.getTopicId();
		int newForumId = castedForm.getNewForumId();
		InfoService service = new InfoService();
		service.moveTopic(topicId, newForumId, userSession.getUserId(),
				castedForm.getReason());
		return mapping.findForward("DisplayForums");
	}
}
