package com.vertonur.user.message.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.context.SystemContextService;
import com.vertonur.dms.InfoService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.UserService;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.pojo.PrivateMessage;
import com.vertonur.pojo.User;

public class InitCreatePrivateMessageAction extends OperactionCheckAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return super.execute(mapping, form, request, response);
	}

	@Override
	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveToken(request);
		String tmp = request.getParameter("pmId");
		if (tmp != null) {
			InfoService infoService = SystemContextService.getService()
					.getDataManagementService(ServiceEnum.INFO_SERVICE);
			PrivateMessage pm = infoService.getPrivateMessageById(Integer
					.parseInt(tmp));
			request.setAttribute("reviewedPm", pm);
		} else {
			UserService userService = SystemContextService.getService()
					.getDataManagementService(ServiceEnum.USER_SERVICE);
			tmp = request.getParameter("userId");
			if (tmp != null) {
				int userId = Integer.parseInt(tmp);
				User receiver = userService.getUserById(userId);
				request.setAttribute("receiver", receiver);
			} else {
				List<User> users = userService.getUsers(0);
				request.setAttribute("users", users);
			}
		}

		return mapping.findForward("ToCreatePrivateMessagePage");
	}
}