package com.vertonur.user.response.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.Response;
import com.vertonur.bean.User;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;
import com.vertonur.util.ForumCommonUtil;

public class DisplayUserSpecifiedResponsesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int userId = Integer.parseInt(request.getParameter("userId"));
		UserService userService = new UserService();
		User theUser = userService.getUserById(userId);
		request.setAttribute("displayedUser", theUser);

		String startStr = (String) request.getParameter("start");
		int paginationStart = ForumCommonUtil
				.strToIntTransitionBuffer(startStr);

		// override system default pagination offset while offset of user
		// specified response is available
		InfoService infoService = new InfoService();
		List<Response> responses = infoService.getResponsesByUser(theUser,
				paginationStart);
		request.setAttribute("userSpecifiedResponses", responses);

		long paginationSize = infoService.getResponseNumByUser(theUser);
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				paginationStart, mapping.getPath().split("/")[1]
						+ ".do?userId=" + theUser.getId(), CxtType.RESPONSE);
		request.setAttribute(PaginationContext.PAGE_CXT, pageCxt);
		return mapping.findForward("ToDisplayUserSpecifiedResponsesPage");
	}
}
