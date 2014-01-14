package com.vertonur.admin.forum.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.Forumzone;
import com.vertonur.constants.Constants;
import com.vertonur.service.ForumzoneService;

public final class InitCreateForumAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveToken(request);

		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute(Constants.FORUMZONES, forumzones);

		request.setAttribute("servicePath", "/admin/forum/create");
		return mapping.findForward("CreateForumPage");
	}
}