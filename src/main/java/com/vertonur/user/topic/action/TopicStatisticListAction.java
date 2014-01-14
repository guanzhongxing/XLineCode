package com.vertonur.user.topic.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Topic;
import com.vertonur.constants.Constants;
import com.vertonur.service.ForumService;
import com.vertonur.service.InfoService;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;
import com.vertonur.util.ForumCommonUtil.PageType;

public class TopicStatisticListAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ForumService forumService = new ForumService();
		InfoService infoService = new InfoService();
		List<Forum> forums = forumService.getForums();
		List<Forum> subForums = new ArrayList<Forum>();
		String mode = request.getParameter("mode");
		HttpSession session = request.getSession(false);
		UserSession userSession = (UserSession) session
				.getAttribute(Constants.USER_SESSION);
		for (Forum forum : forums) {
			List<Topic> topics;
			if ("recent".equals(mode))
				topics = infoService.getRecentTopicsByForum(
						userSession.getUserId(), forum);
			else
				topics = infoService.getHottestTopicsByForum(
						userSession.getUserId(), forum);
			if (topics.size() != 0) {
				if ("recent".equals(mode))
					forum.setRecentTopics(topics);
				else
					forum.setHotTopics(topics);
			} else
				subForums.add(forum);
		}

		if (subForums.size() != 0)
			forums.removeAll(subForums);

		ForumCommonUtil.setPageSeo(request, null, null, PageType.HOME_PAGE);
		request.setAttribute("forums", forums);
		request.setAttribute("mode", mode);
		return mapping.findForward("ToTopicListPage");
	}
}
