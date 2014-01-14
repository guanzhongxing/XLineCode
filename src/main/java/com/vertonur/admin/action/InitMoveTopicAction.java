package com.vertonur.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.Forumzone;
import com.vertonur.service.ForumzoneService;

public class InitMoveTopicAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveToken(request);

		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute("forumzones", forumzones);

		return mapping.findForward("MoveTopicPage");
	}
}
