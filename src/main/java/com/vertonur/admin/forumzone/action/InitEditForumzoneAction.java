package com.vertonur.admin.forumzone.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.Forumzone;
import com.vertonur.service.ForumzoneService;

public class InitEditForumzoneAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveToken(request);

		String forumzoneId = request.getParameter("id");
		if (forumzoneId != null && !"".equals(forumzoneId)) {
			forumzoneId = request.getParameter("id");
			ForumzoneService forumzoneService = new ForumzoneService();
			Forumzone forumzone = forumzoneService.getForumzoneById(Integer
					.parseInt(forumzoneId));
			request.setAttribute("forumzone", forumzone);
		}
		
		request.setAttribute("servicePath", "/admin/forumzone/edit");
		return mapping.findForward("ToEditForumzonePage");
	}
}