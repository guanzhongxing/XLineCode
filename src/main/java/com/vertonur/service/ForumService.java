package com.vertonur.service;

import java.util.List;

import com.vertonur.bean.Forum;
import com.vertonur.bean.ForumStatistician;
import com.vertonur.bean.User;
import com.vertonur.context.SystemContextService;
import com.vertonur.dms.CategoryService;
import com.vertonur.dms.constant.ServiceEnum;
import com.vertonur.dms.exception.CategoryModerationListNotEmptyException;
import com.vertonur.pojo.Category;
import com.vertonur.util.ForumCommonUtil;

public class ForumService {

	private CategoryService service;

	public ForumService() {
		service = SystemContextService.getService().getDataManagementService(
				ServiceEnum.CATEGORY_SERVICE);
	}

	public Forum getForumById(int forumzoneId, int forumId) {
		return new Forum(service.getCategoryById(forumzoneId, forumId));
	}

	public Forum getForumById(int forumzoneId, int forumId, boolean useCache) {
		return new Forum(
				service.getCategoryById(forumzoneId, forumId, useCache));
	}

	public Integer saveForum(int forumzoneId, Forum forum) {
		return service.saveCategory(forumzoneId, forum.getCore());
	}

	public boolean deleteForum(Forum forum) {
		return service.deleteCategory(forum.getCore());
	}

	public List<Forum> getForums() {
		List<Category> categories = service.getCategories();
		return ForumCommonUtil.convertCategoriesToForums(categories);
	}

	public List<Forum> getForumzoneForums(int forumzoneId) {
		List<Category> categories = service.getDeptCategories(forumzoneId);
		return ForumCommonUtil.convertCategoriesToForums(categories);
	}

	public void updateForum(Forum forum)
			throws CategoryModerationListNotEmptyException {
		service.updateCategory(forum.getCore());
	}

	public void hasNewTopicsToUser(Forum forum, User user) {
		service.hasNewInfosToUser(forum.getCore(), user.getCore());
	}

	public void updateStatistician(int forumzoneId, int forumId,
			ForumStatistician statistician) {
		service.updateStatistician(forumzoneId, forumId, statistician.getCore());
	}

	public void changeForumzone(int forumzoneId, Forum forum) {
		service.changeDepartment(forumzoneId, forum.getCore());
	}
}
