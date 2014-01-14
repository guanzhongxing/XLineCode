package com.vertonur.user.response.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.vertonur.bean.Response;
import com.vertonur.bean.Topic;
import com.vertonur.bean.User;
import com.vertonur.constants.Constants;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.pojo.config.AttachmentConfig;
import com.vertonur.service.InfoService;
import com.vertonur.service.UserService;
import com.vertonur.session.UserSession;
import com.vertonur.util.PermissionUtils;

public class InitRespondTopicAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		saveToken(request);
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		InfoService infoService = new InfoService();

		// check new rsp interval
		if (infoService.isInNewRspInterval(userSession.getSessionId())) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.rsp.post.within.time.limition"));
			saveMessages(request, messages);
			return mapping.getInputForward();
		}
		// end

		int topicId = Integer.parseInt(request.getParameter("topicId"));
		int forumId = Integer.parseInt(request.getParameter("forumId"));
		Topic topic = infoService.getTopicById(forumId, topicId);
		request.setAttribute("topicTitle", topic.getSubject());
		request.setAttribute("forumName", topic.getForum().getName());

		String responseId = request.getParameter("responseId");
		Response rsp = null;
		if (responseId != null && !"".equals(responseId))
			rsp = infoService.getResponseById(Integer.parseInt(responseId));

		String quote = request.getParameter("quote");
		String edit = request.getParameter("edit");
		if ("true".equals(quote)) {
			if (responseId != null)
				request.setAttribute("quotedMsg", rsp);
			else
				request.setAttribute("quotedMsg", topic);

		} else if ("true".equals(edit) && responseId != null) {
			request.setAttribute("edittedRsp", rsp);
			request.setAttribute("editted", true);

			if (userSession.isModerator()
					&& (userSession.getUserId() != rsp.getAuthor().getId()))
				request.setAttribute("fromModeration", true);
		}

		UserService userService = new UserService();
		User theCurrentUser = userService.getUserById(userSession.getUserId());
		if (PermissionUtils.checkAttachmentUploadPermission(getServlet()
				.getServletContext(), forumId)
				&& theCurrentUser.isAttmEnabled()) {
			request.setAttribute("attachmentsEnabled", true);

			AttachmentConfig config = SystemContextService
					.getService()
					.getDataManagementService(
							ServiceEnum.RUNTIME_PARAMETER_SERVICE)
					.getAttachmentConfig();
			int maxAttachments = config.getMaxAttmtNum();
			request.setAttribute("maxAttachments", maxAttachments);
			request.setAttribute("maxAttmSize", config.getMaxSize());
		}

		return mapping.findForward("ToRespondTopicPage");
	}
}
