package com.vertonur.user.message.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.InfoService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.constants.Constants;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.PrivateMessage;
import com.vertonur.pojo.User;
import com.vertonur.util.ForumCommonUtil;

public class DisplayPrivateMessageSentboxAction extends OperactionCheckAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return super.execute(mapping, form, request, response);
	}

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("boxType", "sentbox");

		HttpSession session = request.getSession(false);
		String startStr = (String) request.getParameter("start");
		int paginationStart = ForumCommonUtil
				.strToIntTransitionBuffer(startStr);

		InfoService infoService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.INFO_SERVICE);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		User theCurrentUser = userService.getUserById(userSession.getUserId());
		List<PrivateMessage> pms = infoService.getPrivateMsgsByAuthor(
				theCurrentUser, paginationStart);
		for (PrivateMessage pm : pms)
			pm.setNewToReceiver(false);
		request.setAttribute("privateMsgs", pms);

		long paginationSize = infoService
				.getPrivateMsgNumByReceiver(theCurrentUser);
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				paginationStart, mapping.getPath().split("/")[1] + ".do?",
				CxtType.PRIVATE_MSG);
		request.setAttribute(PaginationContext.PAGE_CXT, pageCxt);
		return mapping.findForward("DisplayMessagePage");
	}
}