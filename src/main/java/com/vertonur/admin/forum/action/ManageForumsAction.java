package com.vertonur.admin.forum.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.Forumzone;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.constants.Constants;
import com.vertonur.service.ForumzoneService;

public final class ManageForumsAction extends OperactionCheckAction {

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		saveToken(request);
		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		request.setAttribute(Constants.FORUMZONES, forumzones);
		
		return mapping.findForward("ToForumListPage");
	}
}