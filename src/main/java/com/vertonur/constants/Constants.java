package com.vertonur.constants;

import java.io.File;

public final class Constants {
	// a class used to specify constant variables for session/request
	// keys,messages that shown in a jsp file and variables that used in java
	// files
	// or strings that widely used in java file but might be changed,so they are
	// added to be managed easily

	public static final String ONLINE_USER_IP_SET = "onlineUserIpSet";

	public static final String FORUMZONES = "allForumzones";
	public static final String SELECTEDFORUMZONE = "selectedForumzone";
	public static final String FOURMZONEIDS = "forumzoneIds";
	public static final String FORUMZONENAMESET_KEY = "forumzoneNameSet";
	public static final String EDIT_FORUMZONEID_KEY = "editForumzoneId";
	public static final String EDIT_FORUMZONENAME_KEY = "editForumzoneName";
	public static final String EDIT_FORUMZONEDESCRIPTION_KEY = "editFourmzoneDescription";

	public static final String CURRENT_FORUM = "currentForum";
	public static final String FORUMS = "forums";
	public static final String FORUMID = "forumId";
	public static final String FORUMNAME = "forumName";
	public static final String EDIT_FORUMID_KEY = "editForumId";
	public static final String EDIT_FORUMNAME_KEY = "editForumName";

	// topic related
	public static final String CURRENT_TOPIC = "currentTopic";
	public static final String CURRENT_TOPIC_ID = "currentTopicId";
	public static final String TOTAL_TOPIC_NUM = "totalTopicNum";
	public static final String TOPICS = "topics";
	public static final String DELETEDTOPIC = "topicToBeDeleted";
	public static final String SEARCH_TOPIC_TITLE = "topicTitle";
	public static final String TOPICID = "topicId";

	// Response related
	public static final String RESPONSES = "responses";
	public static final String CURRENT_RESPONSE = "currentResponse";
	public static final String CURRENT_RESPONSE_ID = "currentResponseId";
	public static final String TOTAL_RESPONSE_NUM = "totalResponsesNum";
	// end

	public static final String LOGGED = "logged";
	public static final String ADMINS = "allAdmins";
	public static final String IS_ADMIN = "isAdmin";
	public static final String SELECTEDADMINS = "selectedAdmins";
	public static final String USERS = "users";
	public static final String EDITTEDUSER = "userToBeEditted";
	public static final String DELETEDUSER = "userToBeDeleted";
	public static final String THE_LATEST_USER = "theLatestRegisteredUserName";
	public static final String CURRENT_USERID = "currentUserId";
	public static final String USERIDSET = "userIdSet";
	public static final String LOGIN_USER_SET = "loginUserSet";
	public static final String REGISTED_USER_NUM = "registeredUserNum";
	public static final String CURRENT_USERNAME = "currentLoginUsername";

	public static final String USERID_SESSIONOBJ = "userId_requestObj";
	public static final String SESSIONIDSET = "sessionIdSet";
	public static final String ONLINE_USER_NUM = "onlineUserNum";
	public static final String ONLINE_GUEST_NUM = "onlineGuestNum";
	public static final String LOGIN_USER_NUM = "loginUserNum";
	public static final String File_Separator = "Separator";

	public static final String IMAGE_UPLOAD_SUCCESS = "success";
	public static final String APPLICATIONRESOURCE_ZH_PROPERTY_FILE = "com"
			+ File.separator + "vertonur" + File.separator
			+ "ApplicationResources_zh.properties";

	public static final String SPRING_APP_CXT = "springAppContext";

	// Avatar related
	public static final String DEFAULT_AVATAR = "defaultAvatar.jpeg";
	public static final String DEFAULT_AVATAR_DOWNLOAD_URL = "defaultAvatar.jpeg";
	// end

	// Attachment related
	public static final String ATTM_ENABLED = "attmEnabled";
	public static final String CAN_DOWNLOAD_ATTMS = "canDownloadAttms";
	public static final String DISPLAY_THUMB_SHOW_BOX = "displayThumbShowBox";

	public static final String USER_LOCALE = "specifiedLocale";
	public static final String DEFAULT_LOCALE = "locale";

	// User session related
	public static final String USER_SESSION = "userSession";
	public static final String SESSION_TIMING_KEY = "SESSION_TIMING";
}