package com.vertonur.bean;

import com.vertonur.pojo.Comment;
import com.vertonur.pojo.statistician.InfoStatistician;

public class TopicStatistician {

	private InfoStatistician statistician;

	public TopicStatistician() {
		this.statistician = new InfoStatistician();
	}

	public TopicStatistician(InfoStatistician statistician) {
		this.statistician = statistician;
	}

	public void addClickThroughRate() {
		statistician.addClickThroughRate();
	}

	public int getClickThroughRate() {
		return statistician.getClickThroughRate();
	}

	public InfoStatistician getCore() {
		return statistician;
	}

	public Response getLatestResponse() {
		Comment comment = statistician.getLatestComment();
		if (comment == null)
			return null;
		return new Response(comment);
	}

	public int getResponseNum() {
		return statistician.getCommentNum();
	}

	public void setLatestOne(boolean isLatestOne) {
		statistician.setLatestOne(isLatestOne);
	}

	public void setLatestResponse(Response latestResponse) {
		statistician.setLatestComment(latestResponse.getCore());
	}
	
	public void increaseToModerateNum() {
		statistician.increaseToModerateNum();
	}
}
