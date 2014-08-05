package com.vertonur.admin.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Forumzone;
import com.vertonur.common.OperactionCheckAction;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.ModerationService;
import com.vertonur.dms.UserService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pagination.PaginationContext;
import com.vertonur.pagination.PaginationContext.CxtType;
import com.vertonur.pojo.ModerationLog;
import com.vertonur.pojo.ModerationLog.ModerationStatus;
import com.vertonur.pojo.Moderator;
import com.vertonur.service.ForumzoneService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;

public class ModerationLogListAction extends OperactionCheckAction {

	public ActionForward processRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String action = request.getParameter("action");
		if ("list".equals(action))
			return processListRq(mapping, request);
		else if ("view".equals(action))
			return processForumLogListRq(mapping, request, action);
		else if ("moderatorLogList".equals(action))
			return processModeratorLogListRq(mapping, request, action);
		return mapping.getInputForward();
	}

	@Deprecated
	private ActionForward processListRq(ActionMapping mapping,
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
		return mapping.findForward("ToModerationListPage");
	}

	private ActionForward processForumLogListRq(ActionMapping mapping,
			HttpServletRequest request, String action) {

		HttpSession session = ((HttpServletRequest) request).getSession(true);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		ModerationService service = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.MODERATION_SERVICE);
		int forumId = Integer.parseInt(request.getParameter("forumId"));

		int userId = userSession.getUserId();
		String startStr = (String) request.getParameter("start");
		int paginationStart = ForumCommonUtil
				.strToIntTransitionBuffer(startStr);
		List<ModerationLog> logs = service.getLogs(forumId, paginationStart,
				userId, ModerationStatus.DEFERRED);
		long paginationSize = service.getLogNum(forumId, userId,
				ModerationStatus.DEFERRED);
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				paginationStart, this.getServlet().getServletContext()
						.getContextPath()
						+ "/do"
						+ mapping.getPath()
						+ "?action="
						+ action
						+ "&forumId=" + forumId, CxtType.MDR_LOG);
		request.setAttribute("pageCxt", pageCxt);

		request.setAttribute("logs", logs);
		request.setAttribute("approved", ModerationStatus.APPROVED);
		request.setAttribute("deferred", ModerationStatus.DEFERRED);
		request.setAttribute("rejected", ModerationStatus.REJECTED);
		return mapping.findForward("ToModerationViewPage");
	}

	@Deprecated
	private ActionForward processModeratorLogListRq(ActionMapping mapping,
			HttpServletRequest request, String action) {

		HttpSession session = ((HttpServletRequest) request).getSession(true);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		int userId = userSession.getUserId();

		String startStr = (String) request.getParameter("start");
		int paginationStart = ForumCommonUtil
				.strToIntTransitionBuffer(startStr);
		ModerationService service = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.MODERATION_SERVICE);
		List<ModerationLog> logs = service.getLogs(paginationStart, userId,
				ModerationStatus.MODIFIED, ModerationStatus.DELETED,
				ModerationStatus.MOVED, ModerationStatus.LOCKED,
				ModerationStatus.UNLOCKED);
		long paginationSize = service.getLogNum(userId,
				ModerationStatus.MODIFIED, ModerationStatus.DELETED,
				ModerationStatus.MOVED, ModerationStatus.LOCKED,
				ModerationStatus.UNLOCKED);
		PaginationContext pageCxt = new PaginationContext(paginationSize,
				paginationStart, this.getServlet().getServletContext()
						.getContextPath()
						+ "/do" + mapping.getPath() + "?action=" + action,
				CxtType.MDR_LOG);
		request.setAttribute("pageCxt", pageCxt);

		request.setAttribute("logs", logs);
		return mapping.findForward("ToModeratorLogListPage");
	}
}
