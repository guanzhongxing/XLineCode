package com.vertonur.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.vertonur.pojo.AbstractInfo;
import com.vertonur.pojo.Attachment;
import com.vertonur.pojo.Comment;
import com.vertonur.pojo.UserPreferences;
import com.vertonur.pojo.security.Group;

public class User {

	private com.vertonur.pojo.User user;

	public User(com.vertonur.pojo.User user) {
		this.user = user;
	}

	public List<Attachment> getAttachments() {
		return getCore().getAttachments();
	}

	public Attachment getAvatar() {
		return user.getAvatar();
	}

	public com.vertonur.pojo.User getCore() {
		return user;
	}

	public String getEmail() {
		return user.getEmail();
	}

	public int getGender() {
		return user.getGender();
	}

	public int getId() {
		return user.getId();
	}

	public String getInterests() {
		return user.getInterests();
	}

	public Date getLastLoginDate() {
		return user.getLastLoginDate();
	}

	public String getLocation() {
		return user.getLocation();
	}

	public String getMsn() {
		return user.getMsn();
	}

	public String getName() {
		return user.getName();
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getQq() {
		return user.getQq();
	}

	public Date getRegTime() {
		return user.getRegTime();
	}

	public Set<Info> getResponses() {
		Set<AbstractInfo> comments = user.getComments();
		Iterator<AbstractInfo> iterator = comments.iterator();
		Set<Info> infos = new HashSet<Info>();
		while (iterator.hasNext()) {
			AbstractInfo comment = iterator.next();
			Response rsp = new Response((Comment) comment);
			infos.add(rsp);
		}
		return infos;
	}

	public String getSignature() {
		return user.getSignature();
	}

	public UserMsgStatistician getStatistician() {
		return new UserMsgStatistician(user.getStatistician());
	}

	public Set<Info> getTopics() {
		Set<AbstractInfo> infos = user.getInfos();
		Iterator<AbstractInfo> iterator = infos.iterator();
		Set<Info> topics = new HashSet<Info>();
		while (iterator.hasNext()) {
			AbstractInfo info = iterator.next();
			Topic topic = new Topic((com.vertonur.pojo.Info) info);
			topics.add(topic);
		}
		return topics;
	}

	public UserPreferences getUserPres() {
		return user.getUserPres();
	}

	public String getWebSite() {
		return user.getWebSite();
	}

	public boolean isAttmEnabled() {
		return user.isAttmEnabled();
	}

	public boolean isCanDownloadAttms() {
		return user.isCanDownloadAttms();
	}

	public void removeInfo(Info info) {
		user.removeInfo((com.vertonur.pojo.Info) info.getCore());
	}

	public void removeTopic(Topic topic) {
		user.removeInfo((com.vertonur.pojo.Info) topic.getCore());
	}

	public void setAttmEnabled(boolean attmEnabled) {
		user.setAttmEnabled(attmEnabled);
	}

	public void setAvatar(Attachment avatar) {
		user.setAvatar(avatar);
	}

	public void setCanDownloadAttms(boolean canDownloadAttms) {
		user.setCanDownloadAttms(canDownloadAttms);
	}

	public void setCore(com.vertonur.pojo.User user) {
		this.user = user;
	}

	public void setEmail(String email) {
		user.setEmail(email);
	}

	public void setGender(int gender) {
		user.setGender(gender);
	}

	public void setId(int id) {
		user.setId(id);
	}

	public void setInterests(String interests) {
		user.setInterests(interests);
	}

	public void setLastLoginDate(Date lastLoginDate) {
		user.setLastLoginDate(lastLoginDate);
	}

	public void setLocation(String location) {
		user.setLocation(location);
	}

	public void setMsn(String msn) {
		user.setMsn(msn);
	}

	public void setName(String name) {
		user.setName(name);
	}

	public void setPassword(String password) {
		user.setPassword(password);
	}

	public void setQq(String qq) {
		user.setQq(qq);
	}

	public void setRegTime(Date regTime) {
		user.setRegTime(regTime);
	}

	public void setResponses(Set<Info> responses) {
		Iterator<Info> iterator = responses.iterator();
		Set<AbstractInfo> comments = new HashSet<AbstractInfo>();
		while (iterator.hasNext()) {
			Info rsp = iterator.next();
			comments.add(rsp.getCore());
		}
		user.setComments(comments);
	}

	public void setSignature(String signature) {
		user.setSignature(signature);
	}

	public void setStatistician(UserMsgStatistician statistician) {
		user.setStatistician(statistician.getCore());
	}

	public void setTopics(Set<Info> infos) {
		Iterator<Info> iterator = infos.iterator();
		Set<AbstractInfo> coreInfos = new HashSet<AbstractInfo>();
		while (iterator.hasNext()) {
			Info topic = iterator.next();
			coreInfos.add(topic.getCore());
		}
		user.setInfos(coreInfos);
	}

	public void setUserPres(UserPreferences userPres) {
		user.setUserPres(userPres);
	}

	public void setWebSite(String webSite) {
		user.setWebSite(webSite);
	}

	public boolean isLocked() {
		return user.isLocked();
	}

	public void setLocked(boolean isLocked) {
		user.setLocked(isLocked);
	}

	public Set<Group> getGroups() {
		return user.getGroups();
	}

	public void setGroups(Set<Group> groups) {
		user.setGroups(groups);
	}

	public void addGroup(Group group) {
		user.addGroup(group);
	}

	public void addGroups(Set<Group> newGroups) {
		user.addGroups(newGroups);
	}
}
