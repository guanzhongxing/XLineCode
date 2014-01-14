package com.vertonur.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.vertonur.bean.Forum;
import com.vertonur.bean.Response;
import com.vertonur.bean.Topic;
import com.vertonur.bean.TopicStatistician;
import com.vertonur.bean.User;
import com.vertonur.bean.UserReadTopic;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.ModerationService;
import com.vertonur.dms.RuntimeParameterService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.SavingCommentToLockedInfoException;
import com.vertonur.pojo.AbstractInfo;
import com.vertonur.pojo.Comment;
import com.vertonur.pojo.Info;
import com.vertonur.pojo.ModerationLog.ModerationStatus;
import com.vertonur.session.UserSession;
import com.vertonur.util.ForumCommonUtil;

public class InfoService {
	private com.vertonur.dms.InfoService service;
	private ModerationService moderationService;
	private SystemContextService contextService;
	private long newTopicInterval;
	private long newRspInterval;

	public InfoService() {
		SystemContextService systemContextService = SystemContextService
				.getService();
		service = systemContextService
				.getDataManagementService(ServiceEnum.INFO_SERVICE);
		moderationService = SystemContextService.getService()
				.getDataManagementService(ServiceEnum.MODERATION_SERVICE);
		contextService = SystemContextService.getService();
		RuntimeParameterService parameterService = systemContextService
				.getDataManagementService(ServiceEnum.RUNTIME_PARAMETER_SERVICE);
		newTopicInterval = parameterService.getInfoConfig()
				.getNewInfoInterval();
		newRspInterval = parameterService.getCommentConfig()
				.getNewCmtInterval();

	}

	public void saveUserReadTopic(UserReadTopic userReadTopic) {
		service.saveUserReadInfo(userReadTopic.getCore());
	}

	public List<Topic> getRecentTopicsByForum(int userId, Forum forum) {
		List<Info> infos = service.getRecentInfosByCategory(userId,
				forum.getId());
		return ForumCommonUtil.convertInfosToTopics(infos);
	}

	public List<Response> getResponsesByUser(User theUser, int paginationStart) {
		List<Comment> comments = service.getCommentsByUser(theUser.getCore(),
				paginationStart);
		return ForumCommonUtil.convertCommentsToResponses(comments);
	}

	public long getResponseNumByUser(User theUser) {
		return service.getCommentNumByUser(theUser.getCore());
	}

	public List<Topic> getTopicsByUser(User theUser, int paginationStart) {
		List<Info> infos = service.getInfosByUser(theUser.getCore(),
				paginationStart);
		return ForumCommonUtil.convertInfosToTopics(infos);
	}

	public Topic getTopicById(int forumId, int topicId) {
		return new Topic(service.getInfoById(forumId, topicId));
	}

	public Topic getTopicById(int forumId, int topicId, boolean useCache) {
		return new Topic(service.getInfoById(forumId, topicId, useCache));
	}

	public long getTopicNumByCreator(User theUser) {
		return service.getInfoNumByCreator(theUser.getCore());
	}

	public List<Response> getResponsesByTopic(Topic topic, int paginationStart) {
		List<Comment> comments = service.getCommentsByInfo(
				(com.vertonur.pojo.Info) topic.getCore(), paginationStart);
		return ForumCommonUtil.convertCommentsToResponses(comments);
	}

	public long getResponseNumByTopic(Topic topic) {
		return service.getCommentNumByInfo((com.vertonur.pojo.Info) topic
				.getCore());
	}

	public Response getResponseById(int parseInt) {
		return new Response(service.getCommentById(parseInt));
	}

	public long getResponseNumOfAllTopics() {
		return service.getCommentNumOfAllInfos();
	}

	public void updateTopicStatistician(int topicId,
			TopicStatistician statistician) {
		service.updateInfoStatistician(topicId, statistician.getCore());
	}

	public List<Topic> getHottestTopicsByForum(int userId, Forum forum) {
		List<Info> infos = service.getHottestInfosByCategory(userId,
				forum.getId());
		return ForumCommonUtil.convertInfosToTopics(infos);
	}

	public List<Topic> getNewTopicsByForum(int forumId, int startPoint) {
		List<Info> infos = service.getNewInfosByCategory(forumId, startPoint);
		return ForumCommonUtil.convertInfosToTopics(infos);
	}

	public List<Topic> getTopicsByForum(int userId, int forumId, int start)
			throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException {
		List<Info> infos = service.getInfosByCategory(userId, forumId, start);
		return ForumCommonUtil.convertInfosToTopics(infos);
	}

	public List<Topic> getForumAnnouncements(int forumId)
			throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException {
		List<Info> infos = service.getCategoryAnnouncements(forumId);
		return ForumCommonUtil.convertInfosToTopics(infos);
	}

	public List<Topic> getForumzoneAnnouncements(int forumzoneId)
			throws IllegalAccessException, InstantiationException,
			InvocationTargetException, NoSuchMethodException {
		List<Info> infos = service.getDeptAnnouncements(forumzoneId);
		return ForumCommonUtil.convertInfosToTopics(infos);
	}

	public List<Topic> getSystemAnnouncements() throws IllegalAccessException,
			InstantiationException, InvocationTargetException,
			NoSuchMethodException {
		List<Info> infos = service.getSystemAnnouncements();
		return ForumCommonUtil.convertInfosToTopics(infos);
	}

	public long getTopicNumByForum(int forumId) {
		return service.getInfoNumByCategory(forumId);
	}

	public long getAllTopicNum() {
		return service.getAllInfoNum();
	}

	public Object getTopicsByTitle(String topicTitle) {
		List<Info> infos = service.getInfosByTitle(topicTitle);
		return ForumCommonUtil.convertInfosToTopics(infos);
	}

	public ModerationStatus saveTopic(Topic topic) {
		return moderationService.saveInfo(topic.getCore());
	}

	public ModerationStatus updateTopic(Topic topic) {
		return moderationService.updateInfo(topic.getCore());
	}

	public ModerationStatus saveResponse(Response rsp)
			throws SavingCommentToLockedInfoException {
		return moderationService.saveComment(rsp.getCore());
	}

	public ModerationStatus updateResponse(Response rsp) {
		return moderationService.updateComment(rsp.getCore());
	}

	public boolean isInNewTopicInterval(String userSessionId) {
		return contextService.isInNewInfoInterval(userSessionId,
				newTopicInterval);
	}

	public boolean isInNewRspInterval(String userSessionId) {
		return contextService.isInNewCmtInterval(userSessionId, newRspInterval);
	}

	public void approveRsp(int rspId, int logId) {
		moderationService.approveComment(rspId, logId);
	}

	public void approveTopic(int topicId, int logId) {
		moderationService.approveInfo(topicId, logId);
	}

	public void rejectRsp(int rspId, int logId) {
		moderationService.rejectComment(rspId, logId);
	}

	public void rejectTopic(int topicId, int logId) {
		moderationService.rejectInfo(topicId, logId);
	}

	public void modifyTopic(String originalContent, Topic topic,
			int moderatorId, String reason) {
		moderationService.modifyContent(originalContent,
				(AbstractInfo) topic.getCore(), moderatorId, reason);
	}

	public void modifyRsp(String originalContent, Response rsp,
			int moderatorId, String reason) {
		moderationService.modifyContent(originalContent,
				(AbstractInfo) rsp.getCore(), moderatorId, reason);
	}

	public void deleteRsp(int rspId, UserSession userSession, String reason) {
		if (userSession.isModerator())
			moderationService.deleteComment(rspId, userSession.getUserId(),
					reason);
		else
			service.deleteComment(rspId);
	}

	public void deleteTopic(int topicId, UserSession userSession, String reason) {
		if (userSession.isModerator())
			moderationService.deleteInfo(topicId, userSession.getUserId(),
					reason);
		else
			service.deleteInfo(topicId);
	}

	public int getRspPageIndex(int rpsId) {
		return service.getCommentPageIndex(rpsId);
	}

	public void moveTopic(int topicId, int newForumId, int moderatorId,
			String reason) {
		moderationService.moveInfo(topicId, newForumId, moderatorId, reason);
	}

	public void lockTopic(int topicId, UserSession userSession, String reason) {
		if (userSession.isModerator())
			moderationService
					.lockInfo(topicId, userSession.getUserId(), reason);
		else
			service.lockInfo(topicId);
	}

	public void unlockTopic(int topicId, UserSession userSession, String reason) {
		if (userSession.isModerator())
			moderationService.unlockInfo(topicId, userSession.getUserId(),
					reason);
		else
			service.unlockInfo(topicId);
	}
}
