package com.vertonur.admin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

public class RankingConfigForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5123212679503555038L;
	private int id;
	private int[] ids;
	private String name;
	private int points;
	private double limitHours;
	private boolean timeRanking;
	private int[] groupIds;
	private int topicPoints;
	private int rspPoints;
	private int uploadAttmPoints;
	private boolean pointConfig;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public double getLimitHours() {
		return limitHours;
	}

	public void setLimitHours(double limitHours) {
		this.limitHours = limitHours;
	}

	public boolean isTimeRanking() {
		return timeRanking;
	}

	public void setTimeRanking(boolean timeRanking) {
		this.timeRanking = timeRanking;
	}

	public int[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(int[] groupIds) {
		this.groupIds = groupIds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}

	public int getTopicPoints() {
		return topicPoints;
	}

	public void setTopicPoints(int topicPoints) {
		this.topicPoints = topicPoints;
	}

	public int getRspPoints() {
		return rspPoints;
	}

	public void setRspPoints(int rspPoints) {
		this.rspPoints = rspPoints;
	}

	public int getUploadAttmPoints() {
		return uploadAttmPoints;
	}

	public void setUploadAttmPoints(int uploadAttmPoints) {
		this.uploadAttmPoints = uploadAttmPoints;
	}

	@Override
	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = super.validate(arg0, arg1);
		if (!pointConfig) {
			if (points <= 0)
				errors.add("points", new ActionMessage(
						"error.form.field.required"));

			if (name == null || "".equals(name))
				errors.add("name", new ActionMessage(
						"error.form.field.required"));
			else if (name.length() > 20)
				errors.add("name", new ActionMessage(
						"error.form.exceed.max.length.20"));
		} else {
			if (topicPoints <= 0)
				errors.add("topicPoints", new ActionMessage(
						"error.form.field.required"));
			if (rspPoints <= 0)
				errors.add("rspPoints", new ActionMessage(
						"error.form.field.required"));
			if (uploadAttmPoints <= 0)
				errors.add("uploadAttmPoints", new ActionMessage(
						"error.form.field.required"));
		}

		if (errors != null)
			return errors;
		return null;
	}

	public boolean isPointConfig() {
		return pointConfig;
	}

	public void setPointConfig(boolean pointConfig) {
		this.pointConfig = pointConfig;
	}
}
