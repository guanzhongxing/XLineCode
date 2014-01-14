package com.vertonur.bean;

import com.vertonur.pojo.Info;
import com.vertonur.pojo.statistician.CategoryStatistician;

public class ForumStatistician {

	private CategoryStatistician statistician;

	public ForumStatistician() {
		statistician = new CategoryStatistician();
	}

	public ForumStatistician(CategoryStatistician statistician) {
		this.statistician = statistician;
	}

	public void increaseRspNum() {
		statistician.increaseCommentNum();
	}

	public CategoryStatistician getCore() {
		return statistician;
	}

	public Topic getLatestTopic() {
		Info info = statistician.getLatestInfo();
		if (info == null)
			return null;

		return new Topic(info);
	}

	public int getTopicNum() {
		return statistician.getInfoNum();
	}

	public void setLatestTopic(Topic topic) {
		statistician.setLatestInfo(topic.getCore());
	}

	public void increaseToModerateNum() {
		statistician.increaseToModerateNum();
	}

	public void decreaseToModerateNum() {
		statistician.decreaseToModerateNum();
	}

	public int getToModerateNum() {
		return statistician.getToModerateNum();
	}

	public int getResponseNum() {
		return statistician.getCommentNum();
	}
}
