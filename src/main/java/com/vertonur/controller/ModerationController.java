package com.vertonur.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Forumzone;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.ModerationService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.ModerationLog;
import com.vertonur.pojo.Moderator;
import com.vertonur.pojo.ModerationLog.ModerationStatus;
import com.vertonur.service.ForumzoneService;
import com.vertonur.session.UserSession;

@Controller
public class ModerationController {

	@RequestMapping(value = { "/moderations/logs" }, method = RequestMethod.GET)
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
	
	@RequestMapping(value = { "/moderations" }, method = RequestMethod.GET)
	public String getModerationList(@RequestParam(defaultValue = "0") int start,
			HttpServletRequest request) {
		
		HttpSession session = ((HttpServletRequest) request).getSession(true);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		UserService userService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.USER_SERVICE);
		Moderator moderator = userService.getModeratorById(userSession
				.getUserId());

		ModerationService service = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.MODERATION_SERVICE);
		int pendingLogNum = 0;
		ForumzoneService forumzoneService = new ForumzoneService();
		List<Forumzone> forumzones = forumzoneService.getForumzones();
		Map<Forumzone, Map<Forum, Integer>> toModerateMap = new LinkedHashMap<Forumzone, Map<Forum, Integer>>();
		for (Forumzone forumzone : forumzones) {
			List<Forum> forums = forumzone.getForums();
			Map<Forum, Integer> toModerateForums = new HashMap<Forum, Integer>();
			for (Forum forum : forums) {
				int forumId = forum.getId();
				pendingLogNum += service.getPendingLogNum(forumId);

				int num = moderator.getCategoryDigestingNum(forumId);
				if (num != 0)
					toModerateForums.put(forum, num);
			}

			if (toModerateForums.size() != 0)
				toModerateMap.put(forumzone, toModerateForums);
		}

		request.setAttribute("toModerateMap", toModerateMap);
		request.setAttribute("pendingLogNum", pendingLogNum);
		return "default/admin/moderation_list";
	}
}
