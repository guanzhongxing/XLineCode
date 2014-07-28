package com.vertonur.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.ModerationService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.ModerationLog;
import com.vertonur.pojo.ModerationLog.ModerationStatus;
import com.vertonur.session.UserSession;

@Controller
public class ModerationController {

	@RequestMapping(value = { "/moderations/log" }, method = RequestMethod.GET)
	public String getLogList(@RequestParam(defaultValue = "0") int start,
			HttpServletRequest request) {
		HttpSession session = ((HttpServletRequest) request).getSession(true);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		int userId = userSession.getUserId();

		ModerationService service = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.MODERATION_SERVICE);
		List<ModerationLog> logs = service.getLogs(start, userId,
				ModerationStatus.MODIFIED, ModerationStatus.DELETED,
				ModerationStatus.MOVED, ModerationStatus.LOCKED,
				ModerationStatus.UNLOCKED);
		long paginationSize = service.getLogNum(userId,
				ModerationStatus.MODIFIED, ModerationStatus.DELETED,
				ModerationStatus.MOVED, ModerationStatus.LOCKED,
				ModerationStatus.UNLOCKED);
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				start, request.getRequestURI() + "?", CxtType.MDR_LOG);
		request.setAttribute("pageCxt", pageCxt);

		request.setAttribute("logs", logs);
		return "default/admin/moderation_log_list";
	}
}
