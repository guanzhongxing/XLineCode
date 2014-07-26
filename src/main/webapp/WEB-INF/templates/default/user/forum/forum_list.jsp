<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<table width="100%" align="center">
	<tr>
		<td width="100%" height="318" valign="top"><c:choose>
				<c:when test="${not empty requestScope.allForumzones}">
					<c:forEach var="forumzone" items="${requestScope.allForumzones}">
						<table class="forumline" cellspacing="1" cellpadding="2"
							width="100%" border="0">
							<tr>
								<th class="thcornerl" colspan="2" height="25" align="left"
									valign="middle">&nbsp;${forumzone.name}<c:if
										test="${not empty forumzone.description}"> -- ${forumzone.description}</c:if>&nbsp;
								</th>
								<th class="thtop" nowrap="nowrap" width="50">&nbsp;<fmt:message
										key="forum_list.jsp.totalTopics" />&nbsp;
								</th>
								<th class="thtop" nowrap="nowrap" width="55">&nbsp;<fmt:message
										key="forum_list.jsp.totalResponses" />&nbsp;
								</th>
								<th class="thcornerr" nowrap="nowrap">&nbsp;<fmt:message
										key="forum_list.jsp.latestTopic" />&nbsp;
								</th>
							</tr>

							<!-- START FORUM LISTING -->
							<tr>
								<td class="catleft" colspan="2" height="28" align="center"><span
									class="cattitle"><fmt:message
											key="forum_list.jsp.forums" /></span></td>
								<td class="catright" align="right" colspan="3">&nbsp;</td>
							</tr>

							<c:choose>
								<c:when test="${not empty forumzone.forums}">
									<c:forEach var="forum" items="${forumzone.forums}">
										<c:if test="${!forum.deprecated}">
											<c:set var="statistician" value="${forum.statistician}" />
											<c:set var="latestTopic" value="${statistician.latestTopic}" />
											<tr>
												<td class="row1" valign="middle" align="center" height="50">
													<c:choose>
														<c:when test="${forum.hasNewTopic}">
															<img
																src="${resourcesHost}/images/new.gif" />
														</c:when>
														<c:otherwise>
															<img
																src="${resourcesHost}/images/folder_big.gif"
																alt="[Folder]" />
														</c:otherwise>
													</c:choose>
												</td>
												<td class="row1" width="100%" height="50"><span
													class="forumlink"><a class="forumlink"
														href="${contextPath}/forums/${forum.id}">${forum.name}</a></span><br />
													<span class="genmed"> ${forum.description} </span> <br />
												</td>
												<td class="row2" valign="middle" align="center" height="50">
													<span class="gensmall"> <c:choose>
															<c:when test="${statistician.topicNum!=0}">
																<c:set var="noTopics" value="false" />
																		${statistician.topicNum}
															</c:when>
															<c:otherwise>
																<c:set var="noTopics" value="true" />
																<fmt:message key="forum_list.jsp.noTopics" />
															</c:otherwise>
														</c:choose>
												</span>
												</td>
												<td class="row2" valign="middle" align="center" height="50">
													<c:choose>
														<c:when test="${noTopics!=true}">
															<c:set var="responseNum"
																value="${forum.statistician.responseNum}" />
															<span class="gensmall"> <c:choose>
																	<c:when test="${responseNum!=0}">
																		${responseNum}
																	</c:when>
																	<c:otherwise>
																		<fmt:message key="forum_list.jsp.noResponses" />
																	</c:otherwise>
																</c:choose>
															</span>
														</c:when>
														<c:otherwise>
															<span class="gensmall"> <fmt:message
																	key="forum_list.jsp.noResponses" />
															</span>
														</c:otherwise>
													</c:choose>
												</td>
												<td class="row2" valign="middle" nowrap="nowrap"
													align="center" height="50"><span class="postdetails">
														<c:choose>
															<c:when test="${not empty latestTopic}">
																<fmt:formatDate value="${latestTopic.createdTime}"
																	dateStyle="medium" />
																<fmt:formatDate value="${latestTopic.createdTime}"
																	pattern="hh:mm" />
																<br />
																<a
																	href="${contextPath}/users/${latestTopic.author.id}">${latestTopic.author.name}</a>
																<a
																	href="${contextPath}/forums/topics/${latestTopic.id}">
																	<img
																	src="${resourcesHost}/images/icon_latest_reply.gif"
																	border="0" alt="[Latest Reply]" />
																</a>
															</c:when>
															<c:otherwise>
																<fmt:message key="forum_list.jsp.noLatestTopic" />
															</c:otherwise>
														</c:choose>
												</span></td>
											</tr>
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td class="catleft" colspan="5" height="28"><span
											class="cattitle"><fmt:message
													key="forum_list.jsp.noForumYet" /></span></td>
									</tr>
								</c:otherwise>
							</c:choose>
							<!-- END OF FORUM LISTING -->
						</table>

						<table cellspacing="0" cellpadding="2" width="100%" align="center"
							border="0">
							<tr>
								<td align="left"><span class="gensmall">&nbsp;</span></td>
							</tr>
						</table>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<table class="forumline" cellspacing="1" cellpadding="2"
						width="100%" border="0">
						<tr>
							<th class="thcornerl" nowrap="nowrap" colspan="5" height="25"
								align="left" valign="middle">&nbsp;<fmt:message
									key="forum_list.jsp.noForumzoneYet" />&nbsp;
							</th>
						</tr>
					</table>

					<table cellspacing="0" cellpadding="2" width="100%" align="center"
						border="0">
						<tr>
							<td align="left"><span class="gensmall">&nbsp;</span></td>
						</tr>
					</table>
				</c:otherwise>
			</c:choose>

			<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
				border="0">
				<tr>
					<td class="cathead" colspan="2" height="28"><span
						class="cattitle"> <fmt:message
								key="forum_list.jsp.whoIsOnline" />
					</span></td>
				</tr>

				<c:set var="systemState" value="${requestScope.systemState}" />
				<tr>
					<td class="row1" valign="middle" align="center" rowspan="2"><img
						src="${resourcesHost}/images/whosonline.gif"
						alt="[Who's Online]" /></td>
					<td class="row1 gensmall" align="left" width="100%"><fmt:message
							key="forum_list.jsp.totalresponsenum">
							<fmt:param value="${systemState.systemCommentNum}" />
						</fmt:message> <br /> <fmt:message key="forum_list.jsp.registeredUserNum">
							<fmt:param value="${systemState.registeredUserNum}" />
						</fmt:message> <br /> <fmt:message key="forum_list.jsp.thelatestuser" /><a
						href="${contextPath}/users/${systemState.latestRegisteredUser.id}">&nbsp;${systemState.latestRegisteredUser.name}</a>
					</td>
				</tr>

				<tr>
					<td class="row1 gensmall" align="left"><fmt:message
							key="forum_list.jsp.numberOfUsersOnline">
							<fmt:param value="${systemState.onlineUserNum}" />
							<fmt:param value="${systemState.loginUserNum}" />
							<fmt:param value="${systemState.guestNum}" />
						</fmt:message>&nbsp;&nbsp; <br /> <c:choose>
							<c:when test="${systemState.peakOnlineUserNum!=0}">
								<fmt:message
									key="forum_list.jsp.mostUsersEverOnlineStatistician">
									<fmt:param value="${systemState.peakOnlineUserNum}" />
									<fmt:param value="${systemState.peakOnlineUserDate}" />
								</fmt:message>
							</c:when>
							<c:otherwise>
								<fmt:message key="forum_list.jsp.noWebsiteStatisticianDataYet" />
							</c:otherwise>
						</c:choose> <br /> <fmt:message key="forum_list.jsp.connectedUsers" />: <c:set
							var="userSessions" value="${requestScope.loginUserSessions}" />
						<c:choose>
							<c:when test="${not empty userSessions}">
								<c:forEach var="userSession" items="${userSessions}">
									<a
										href="${contextPath}/users/${userSession.value.userId}">
										<c:choose>
											<c:when test="${userSession.value.admin}">
												<span class="admin">${userSession.value.username}</span>
											</c:when>
											<c:otherwise>
												<span class="">${userSession.value.username}</span>
											</c:otherwise>
										</c:choose>
									</a>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<span><fmt:message key="all.jsp.anonymous" /></span>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table> <br />

			<table cellspacing="3" cellpadding="0" align="center" border="0">
				<tr>
					<td align="center" width="20"><img
						src="${resourcesHost}/images/new.gif" /></td>
					<td><span class="gensmall"><fmt:message
								key="forum_list.jsp.newTopics" /></span></td>
					<td>&nbsp;&nbsp;</td>
					<td align="center" width="20"><img
						src="${resourcesHost}/images/folder.gif"
						alt="[Folder]" /></td>
					<td><span class="gensmall"><fmt:message
								key="forum_list.jsp.noNewTopics" /></span></td>
					<td>&nbsp;&nbsp;</td>
					<td align="center" width="20"><img
						src="${resourcesHost}/images/folder_lock.gif"
						alt="[Lock Folder]" /></td>
					<td><span class="gensmall"><fmt:message
								key="forum_list.jsp.blockedForum" /></span></td>
				</tr>
			</table></td>
	</tr>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>