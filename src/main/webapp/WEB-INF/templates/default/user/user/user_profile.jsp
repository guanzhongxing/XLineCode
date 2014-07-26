<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="user_profile.jsp.profile" />
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<c:set var="displayedUser" value="${requestScope.displayedUser}" />
<table cellspacing="2" cellpadding="2" width="100%" align="center"
	border="0">
	<tr>
		<td align="right"><span class="nav"><a class="nav"
				href="${contextPath}/forums">&gt;&gt;&gt;<fmt:message
						key="forum_list.jsp.forums" /></a></span></td>
	</tr>
</table>

<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
	align="center" border="0">
	<tr>
		<th class="thhead" nowrap="nowrap" colspan="2" height="25"><fmt:message
				key="displayprofile.jsp.profile">
				<fmt:param value="${displayedUser.name}" />
			</fmt:message></th>
	</tr>

	<tr>
		<td class="catleft" align="center" width="40%" height="28"><b><span
				class="gen"><fmt:message key="displayprofile.jsp.avatar" /></span></b></td>
		<td class="catright" align="center" width="60%"><b><span
				class="gen"><fmt:message key="all.jsp.user.username" /></span></b></td>
	</tr>

	<tr>
		<td class="row1" valign="top" align="center" height="6"><c:set
				var="attmInfo" value="${requestScope.displayedUser.avatar.attmInfo}" />
			<c:set var="avatar" value="${requestScope.displayedUser.avatar}" />
			<c:choose>
				<c:when test="${attmInfo.attachmentType=='LOCAL'}">
					<c:set var="avatarUrl"
						value="${contextPath}/local/image.do?id=${avatar.id}" />
				</c:when>
				<c:otherwise>
					<c:set var="avatarUrl" value="${attmInfo.downloadUrl}" />
				</c:otherwise>
			</c:choose> <img src="${avatarUrl}" border="0" width="${avatarWidth}"
			height="${avatarHeight}" /><br /> <span class="postdetails">
				ranking </span></td>

		<td class="row1" valign="top" rowspan="3">
			<table cellspacing="1" cellpadding="3" width="100%" border="0">
				<tr>
					<td valign="middle" nowrap="nowrap" align="right"><span
						class="gen"><fmt:message
								key="displayprofile.jsp.registrationDate" />:&nbsp;</span></td>
					<td width="100%"><b> <span class="gen"> <fmt:formatDate
									value="${displayedUser.regTime}" dateStyle="medium" /> <fmt:formatDate
									value="${displayedUser.regTime}" pattern="hh:mm" />
						</span>
					</b></td>
				</tr>

				<tr>
					<td valign="top" nowrap="nowrap" align="right"><span
						class="gen"><fmt:message
								key="displayprofile.jsp.totalResponses" />:&nbsp;</span></td>
					<td valign="top"><c:choose>
							<c:when test="${requestScope.rsNum!=0}">
								<b><a class="gen"
									href="displayUserSpecifiedResponses.do?userId=${displayedUser.id}">
										<fmt:message key="displayprofile.jsp.postedResponseNum">
											<fmt:param value="${requestScope.rsNum}" />
											<fmt:param value="${displayedUser.name}" />
										</fmt:message>
								</a></b>
							</c:when>
							<c:otherwise>
								<span class="gen"> <fmt:message
										key="displayprofile.jsp.noUserCreatedResponses" />
								</span>
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<td valign="top" nowrap="nowrap" align="right"><span
						class="gen"><fmt:message
								key="displayprofile.jsp.userCreatedTopics" />:</span></td>
					<td><c:choose>
							<c:when test="${requestScope.topicNum!=0}">
								<b><a class="gen"
									href="displayUserSpecifiedTopics.do?userId=${displayedUser.id}">
										<fmt:message key="displayprofile.jsp.postedTopicNum">
											<fmt:param value="${requestScope.topicNum}" />
											<fmt:param value="${displayedUser.name}" />
										</fmt:message>
								</a></b>
							</c:when>
							<c:otherwise>
								<span class="gensmall"> <fmt:message
										key="displayprofile.jsp.noUserCreatedTopics" />
								</span>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</td>
	</tr>

	<c:if test="${sessionScope.userSession.userId!=displayedUser.id}">
		<tr>
			<td class="catleft" align="center" height="28"><b><span
					class="gen"> <fmt:message key="displayprofile.jsp.contact">
							<fmt:param value="${displayedUser.name}" />
						</fmt:message>
				</span></b></td>
		</tr>


		<tr>
			<td class="row1" valign="top">
				<table cellspacing="1" cellpadding="3" width="100%" border="0">


					<tr>
						<td valign="middle" nowrap="nowrap" align="right"><span
							class="gen"><fmt:message key="all.jsp.privateMsg" />:</span></td>
						<td class="row1" valign="middle"><b><span class="gen">
									<a href="initCreatePrivateMsg.do?userId=${displayedUser.id}"
									class="icon_pm"><img
										src="${resourcesHost}/images/${locale}/icon_pm.gif"
										alt="" /></a>
							</span></b></td>
					</tr>
				</table>
			</td>
		</tr>
	</c:if>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>