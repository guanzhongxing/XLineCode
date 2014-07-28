package com.vertonur.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.InfoService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.PrivateMessage;
import com.vertonur.pojo.User;
import com.vertonur.pojo.UserReadPrivateMessage;
import com.vertonur.session.UserSession;

@Controller
@RequestMapping("/pms")
public class PrivateMessageController {

	@RequestMapping(method = RequestMethod.GET)
	public String getMessageList(
			@RequestParam(defaultValue = "inbox") String boxType,
			@RequestParam(defaultValue = "0") int start,
			HttpServletRequest request) {
		request.setAttribute("boxType", boxType);

		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		User theCurrentUser = userService.getUserById(userSession.getUserId());
		InfoService infoService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.INFO_SERVICE);
		List<PrivateMessage> pms;
		String url = request.getRequestURI() + "?";
		long paginationSize = 0;
		if ("inbox".equals(boxType)) {
			pms = infoService.getPrivateMsgsByReceiver(theCurrentUser, start);
			paginationSize = infoService
					.getPrivateMsgNumByReceiver(theCurrentUser);
		} else {
			pms = infoService.getPrivateMsgsByAuthor(theCurrentUser, start);
			for (PrivateMessage pm : pms)
				pm.setNewToReceiver(false);
			url += "boxType=sentbox";
			paginationSize = infoService
					.getPrivateMsgNumByAuthor(theCurrentUser);
		}
		request.setAttribute("privateMsgs", pms);

		PaginationContext pageCxt = new PaginationContext(paginationSize,
				start, url, CxtType.PRIVATE_MSG);
		request.setAttribute(PaginationContext.PAGE_CXT, pageCxt);
		return "default/user/message/pm_list";
	}

	@RequestMapping(value = "/{pmId}", method = RequestMethod.GET)
	public String getMessage(@PathVariable int pmId,
			@RequestParam(required = false) boolean iFrame,
			HttpServletRequest request) {

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

		if (iFrame)
			return "default/user/message/pm_review_message";
		else
			return "default/user/message/pm_read_message";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String showMessageForm(@RequestParam(defaultValue = "0") int pmId,
			@RequestParam(defaultValue = "0") int userId,
			HttpServletRequest request) {

		if (pmId != 0) {
			InfoService infoService = SystemContextService.getService()
					.getDataManagementService(ServiceEnum.INFO_SERVICE);
			PrivateMessage pm = infoService.getPrivateMessageById(pmId);
			request.setAttribute("reviewedPm", pm);
		} else {
			UserService userService = SystemContextService.getService()
					.getDataManagementService(ServiceEnum.USER_SERVICE);
			if (userId != 0) {
				User receiver = userService.getUserById(userId);
				request.setAttribute("receiver", receiver);
			} else {
				List<User> users = userService.getUsers(0);
				request.setAttribute("users", users);
			}
		}

		return "default/user/message/pm_form";
	}
}
