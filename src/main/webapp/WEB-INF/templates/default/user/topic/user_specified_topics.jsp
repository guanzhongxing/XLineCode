<%@ include file="/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="user_specified_topics.jsp.title" />
<%@ include file="/templates/default/user/common/header.jsp"%>

<c:set var="displayedUser" value="${requestScope.displayedUser}" />
<table cellspacing="0" cellpadding="10" width="100%" align="center"
	border="0">
	<tr>
		<td class="bodyline" valign="top">
		<table cellspacing="0" cellpadding="2" width="100%" align="center"
			border="0">
			<tr>
				<td valign="bottom" align="left"><span class="nav"
					style="color: #DD6900"> <fmt:message
					key="user_specified_topics.jsp.userTopics">
					<fmt:param value="${displayedUser.name}" />
				</fmt:message> </span> &raquo; <a class="nav"
					href="displayProfile.do?userId=${displayedUser.id}"> <fmt:message
					key="user_specified_topics.jsp.profileFor">
					<fmt:param value="${displayedUser.name}" />
				</fmt:message> </a> &raquo; <a class="nav" href="displayForums.do"><fmt:message
					key="user_specified_topics.jsp.forums" /></a></td>
				<td align="right">
					<%@ include file="/templates/common/pagination/pagination.jsp" %>
				</td>
			</tr>
		</table>

		<table class="forumline" cellspacing="1" cellpadding="4" width="100%"
			border="0">
			<tr>
				<th class="thcornerl" nowrap="nowrap" align="center" colspan="2"
					height="25">&nbsp;<fmt:message
					key="user_specified_topics.jsp.topic" />&nbsp;</th>
				<th class="thtop" nowrap="nowrap" align="center" width="50">&nbsp;<fmt:message
					key="user_specified_topics.jsp.responses" />&nbsp;</th>
				<th class="thtop" nowrap="nowrap" align="center" width="100">&nbsp;<fmt:message
					key="all.jsp.author" />&nbsp;</th>
				<th class="thtop" nowrap="nowrap" align="center" width="50">&nbsp;<fmt:message
					key="user_specified_topics.jsp.views" />&nbsp;</th>
				<th class="thtop" nowrap="nowrap" align="center">&nbsp;<fmt:message
					key="user_specified_topics.jsp.lastResponse" />&nbsp;</th>
				<th class="thcornerr" nowrap="nowrap" align="center">&nbsp;<fmt:message
					key="user_specified_topics.jsp.forumName" />&nbsp;</th>
			</tr>

			<!-- TOPICS LISTING -->
			<c:forEach var="topic" items="${requestScope.userSpecifiedTopics}">
				<tr>
					<td class="row1" valign="middle" align="center" width="20"><img
						src="${contextPath}/templates/default/images/folder_new.gif"
						alt="" /></td>
					<td class="row1" width="100%"><span class="topictitle">
					<a
						href="displayResponses.do?forumId=${topic.forum.id}&topicId=${topic.id}">${topic.subject}</a>
					</span></td>

					<c:set var="statistician" value="${topic.statistician}" />
					<td class="row2" valign="middle" align="center"><span
						class="postdetails">${statistician.responseNum}</span></td>
					<td class="row3" valign="middle" align="center"><span
						class="name"><a
						href="displayProfile.do?userId=${topic.author.id}">${topic.author.name}</a></span>
					</td>

					<td class="row2" valign="middle" align="center"><span
						class="postdetails">${statistician.clickThroughRate}</span></td>
					<td class="row3right" valign="middle" nowrap="nowrap" align="center"><c:set var="latestResponse" value="${statistician.latestResponse}" /> 
						<span class="postdetails">
							<c:choose>
								<c:when test="${not empty latestResponse}">
									 
										<fmt:formatDate value="${latestResponse.createdTime}" dateStyle="medium" />
										<fmt:formatDate value="${latestResponse.createdTime}" pattern="hh:mm" />
										<br/>
										<a href="displayProfile.do?userId=${latestResponse.author.id}">${latestResponse.author.name}</a>
										<a href="displayResponses.do?forumId=${topic.forum.id}&topicId=${topic.id}#${latestResponse.id}">
											<img src="${contextPath}/templates/default/images/icon_latest_reply.gif" border="0" alt="[Latest Reply]" />
										</a>
									
								</c:when>
								<c:otherwise>
									<fmt:message key="user_specified_topics.jsp.noResponses" />
								</c:otherwise>
							</c:choose>
						</span>
					</td>


					<td class="row2" valign="middle" align="center"><a
						class="postdetails"
						href="displayTopics.do?forumzoneId=${topic.forum.forumzone.id}&forumId=${topic.forum.id}">${topic.forum.name}</a></td>

				</tr>
			</c:forEach>
			<!-- END OF TOPICS LISTING -->

		</table>
		</td>
	</tr>
</table>

<%@ include file="/templates/default/common/footer.jsp"%>