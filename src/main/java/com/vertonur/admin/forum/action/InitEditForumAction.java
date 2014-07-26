package com.vertonur.admin.forum.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Forumzone;
import com.vertonur.constants.Constants;
import com.vertonur.service.ForumService;
import com.vertonur.service.ForumzoneService;

public final class InitEditForumAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveToken(request);

		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute(Constants.FORUMZONES, forumzones);

		String forumIdStr = request.getParameter("forumId");
		if (forumIdStr != null && !"".equals(forumIdStr)) {
			int forumId = Integer.parseInt(forumIdStr);
			ForumService forumService = new ForumService();
			Forum forum = forumService.getForumById(forumId);
			request.setAttribute("forum", forum);

			request.setAttribute("servicePath", "/admin/forum/edit");
		}

		return mapping.findForward("EditForumPage");
	}
}