<%-- <%@ include file="scrolls_ads.jsp" %> --%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<meta name="description" content="${description}">
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/${locale}.css" />

<script type="text/javascript" src="${resourcesHost}/javascripts/jquery.js"></script>
<title>${title}</title>
</head>
<body class="${locale}"
	background="${resourcesHost}/images/light.jpg">
	<table width="1000px" border="0" align="center">
		<tr>
			<td>
				<table cellspacing="0" cellpadding="0" width="100%" border="0">
					<tr>
						<td></td>

						<td width="100%" align="center" valign="middle"><span
							class="boardtitle">${applicationScope.globalConfig.forumPageTitle}</span>
							<table cellspacing="0" cellpadding="2" border="0" width="100%">
								<tr>
									<td valign="buttom" nowrap="nowrap" align="right" width="10%"></td>
									<td valign="top" nowrap="nowrap" align="center" width="80%">&nbsp;
										<img
										src="${resourcesHost}/images/icon_mini_search.gif"
										alt="[Search]" /> <span class="mainmenu"><a
											id="search" class="mainmenu" href="#" rel="nofollow"><b><fmt:message
														key="header.jsp.search" /></b></a> &nbsp; <img
											src="${resourcesHost}/images/icon_mini_recentTopics.gif"
											alt="[Recent Topics]" /> <a id="latest" class="mainmenu"
											href="${contextPath}/topicList.do?mode=recent" rel="nofollow"><fmt:message
													key="header.jsp.recentTopic" /></a> &nbsp; <img
											src="${resourcesHost}/images/icon_mini_recentTopics.gif"
											alt="[Hottest Topics]" /> <a id="hottest" class="mainmenu"
											href="${contextPath}/topicList.do?mode=hottest"
											rel="nofollow"><fmt:message
													key="header.jsp.hottestTopics" /></a> &nbsp; <img
											src="${resourcesHost}/images/icon_mini_members.gif"
											alt="[Members]" />&nbsp; <a id="latest2" class="mainmenu"
											href="${contextPath}/displayMembers.do" rel="nofollow"><fmt:message
													key="header.jsp.members" /></a> &nbsp;</span> <span class="mainmenu">
											<img
											src="${resourcesHost}/images/icon_mini_groups.gif"
											alt="[Groups]" />&nbsp; <a id="backtosite" class="mainmenu"
											href="${applicationScope.globalConfig.homepageLink}"><fmt:message
													key="header.jsp.homePage" /></a>&nbsp;
									</span> <br> <c:choose>
											<c:when test="${sessionScope.userSession.login}">
												<span class="mainmenu"> <c:if
														test="${sessionScope.userSession.moderator}">
														<a id="moderationlog" class="mainmenu" rel="nofollow"
															href="${contextPath}/do/admin/moderation?action=moderatorLogList"><img
															src="${resourcesHost}/images/icon_mini_members.gif"
															alt="[Moderation Log]"> <fmt:message
																key="header.jsp.mdr.log" /></a>&nbsp;
												</c:if> <a id="myprofile" class="mainmenu" rel="nofollow"
													href="${contextPath}/displayMyProfile.do?userId=${sessionScope.userSession.userId}">
														<img
														src="${resourcesHost}/images/icon_mini_profile.gif"
														border="0" alt="[Profile]" /> <fmt:message
															key="header.jsp.myProfile" />
												</a>&nbsp; <a id="privatemessages" class="mainmenu"
													rel="nofollow"
													href="${contextPath}/displayPrivateMsgInbox.do?userId=${sessionScope.userSession.userId}">
														<img
														src="${resourcesHost}/images/icon_mini_message.gif"
														border="0" alt="[Message]" /> <fmt:message
															key="all.jsp.privateMsg" />
												</a>&nbsp; <a id="logout" class="mainmenu" rel="nofollow"
													href="${contextPath}/logout.do"> <img
														src="${resourcesHost}/images/icon_mini_login.gif"
														border="0" alt="[Login]" /> <fmt:message
															key="header.jsp.logout" />&nbsp;[${sessionScope.userSession.username}]
												</a>
												</span>
											</c:when>
											<c:otherwise>
												<span class="mainmenu"> <a id="register"
													class="mainmenu" href="${contextPath}/showAgreement.do"
													rel="nofollow"> <img
														src="${resourcesHost}/images/icon_mini_register.gif"
														border="0" alt="[Register]" /> <fmt:message
															key="header.jsp.register" />
												</a>&nbsp; <a id="login" class="mainmenu"
													href="${contextPath}/user/form"
													rel="nofollow"> <img
														src="${resourcesHost}/images/icon_mini_login.gif"
														border="0" alt="[Login]" /> <fmt:message
															key="header.jsp.login" />
												</a>&nbsp;
												</span>
											</c:otherwise>
										</c:choose> <br>
									</td>
									<td valign="buttom" nowrap="nowrap" align="right"
										style="padding-right: 3px"><a
										href="http://weibo.com/u/3905573711" target="_blank"><img
											src="${resourcesHost}/images/sina_weibo.jpg"></a>&nbsp;<a
										target="_blank"
										href="http://wp.qq.com/wpa/qunwpa?idkey=7ec66e261a75d5469307699b80d5595e8b5f62a1c3db0c2c1ce0da621ea164ed"><img
											border="0"
											src="${resourcesHost}/images/qq_group.jpg"
											alt="info-forum" title="info-forum"></a></td>
								</tr>
							</table></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>