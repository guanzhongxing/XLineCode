package com.vertonur.user.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.User;
import com.vertonur.constants.Constants;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;
import com.vertonur.util.ForumCommonUtil.PageType;

public class DisplayMembersAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String startStr = (String) request.getParameter("start");
		int paginationStart = ForumCommonUtil
				.strToIntTransitionBuffer(startStr);

		UserService userService = new UserService();
		long paginationSize = userService.getUserNum();
		List<User> members = userService.getUsers(paginationStart);

		String requestPath = request.getRequestURI() + "?";
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				paginationStart, requestPath, CxtType.MEMBER);
		request.setAttribute(PaginationContext.PAGE_CXT, pageCxt);
		request.setAttribute("members", members);

		HttpSession session = ((HttpServletRequest) request).getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		if (userSession.isAdmin())
			request.setAttribute("showEmail", true);
		else
			request.setAttribute("showEmail", false);
		
		ForumCommonUtil.setPageSeo(request, null, null, PageType.HOME_PAGE);
		return mapping.findForward("ToDisplayMembersPage");
	}
}
