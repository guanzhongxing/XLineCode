package com.vertonur.user.message.action;

import java.util.Date;

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
import com.vertonur.pojo.PrivateMessage;
import com.vertonur.pojo.User;
import com.vertonur.pojo.UserReadPrivateMessage;

public class DisplayPrivateMessageAction extends OperactionCheckAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return super.execute(mapping, form, request, response);
	}

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int pmId = Integer.parseInt(request.getParameter("pmId"));
		InfoService infoService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.INFO_SERVICE);
		PrivateMessage thePm = infoService.getPrivateMessageById(pmId);

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		User theCurrentUser = userService.getUserById(userSession.getUserId());
		boolean read = userService.confirmReadPrivateMsg(theCurrentUser, thePm);
		if (!read) {
			UserReadPrivateMessage userReadPm = new UserReadPrivateMessage(
					theCurrentUser, thePm, new Date());
			infoService.saveUserReadPriateMsg(userReadPm);
		}

		request.setAttribute("thePm", thePm);

		String iFrame = request.getParameter("iFrame");
		if (iFrame != null && iFrame.equals("true"))
			return mapping.findForward("ToPrivateMsgReviewPage");
		else
			return mapping.findForward("ToDisplayPrivateMsgPage");
	}
}
