<%@ include file="/templates/default/common/common_header.jsp"%>
<c:choose>
	<c:when test="${mode=='recent'}">
		<fmt:message var="title" key="header.jsp.recentTopic" />
	</c:when>
	<c:otherwise>
		<fmt:message var="title" key="header.jsp.hottestTopics" />
	</c:otherwise>
</c:choose>
<%@ include file="/templates/default/user/common/header.jsp"%>


<table cellspacing="0" cellpadding="10" width="100%" align="center"
	border="0">
	<tr>
		<td class="bodyline" valign="top">

			<table cellspacing="0" cellpadding="2" width="100%" align="center"
				border="0">
				<tr>
					<td>&nbsp;</td>
					<td valign="bottom" align="right"><c:choose>
							<c:when test="${mode=='recent'}">
								<span class="nav" style="color: #DD6900"><fmt:message
										key="recent_topics.jsp.recentTopics" /></span> &raquo;
								<a class="nav" href="displayForums.do"><fmt:message
										key="recent_topics.jsp.forums" /></a>
							</c:when>
							<c:otherwise>
								<span class="nav" style="color: #DD6900"><fmt:message
										key="header.jsp.hottestTopics" /></span> &raquo;
								<a class="nav" href="displayForums.do"><fmt:message
										key="hottest_topics.jsp.forums" /></a>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table> <c:forEach var="forum" items="${requestScope.forums}">
				<table class="forumline" cellspacing="1" cellpadding="4"
					width="100%" border="0">
					<tr>
						<th class="thcornerl" nowrap="nowrap" align="left" width="12%">&nbsp;<fmt:message
								key="recent_topics.jsp.forumName" />
						</th>
						<th class="thtop" nowrap="nowrap" align="center" colspan="2"
							height="25">&nbsp;<fmt:message
								key="recent_topics.jsp.topics" />&nbsp;
						</th>
						<th class="thtop" nowrap="nowrap" align="center" width="50">&nbsp;<fmt:message
								key="recent_topics.jsp.responses" />&nbsp;
						</th>
						<th class="thtop" nowrap="nowrap" align="center" width="100">&nbsp;<fmt:message
								key="all.jsp.author" />&nbsp;
						</th>
						<th class="thtop" nowrap="nowrap" align="center" width="50">&nbsp;<fmt:message
								key="recent_topics.jsp.views" />&nbsp;
						</th>
						<th class="thcornerr" nowrap="nowrap" align="center">&nbsp;<fmt:message
								key="recent_topics.jsp.latestResponse" />&nbsp;
						</th>
					</tr>

					<!-- TOPICS LISTING -->
					<c:choose>
						<c:when test="${mode=='recent'}">
							<c:set var="topics" value="${forum.recentTopics}" />
						</c:when>
						<c:otherwise>
							<c:set var="topics" value="${forum.hotTopics}" />
						</c:otherwise>
					</c:choose>
					<c:forEach var="topic" items="${topics}">
						<tr>
							<td class="row2" valign="middle" align="center"><a
								class="postdetails"
								href="displayTopics.do?forumzoneId=${forum.forumzone.id}&forumId=${forum.id}&forumName=${forum.name}">${forum.name}</a>&nbsp;
							</td>
							<td class="row1" valign="middle" align="center" width="20"><%@ include
									file="topic_status_displayer.jsp"%></td>
							<td class="row1" width="80%"><span class="topictitle">
									<a
									href="displayResponses.do?forumId=${forum.id}&topicId=${topic.id}">${topic.subject}</a>
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
							<c:set var="latestResponse"
								value="${statistician.latestResponse}" />
							<td class="row3right" valign="middle" nowrap="nowrap"
								align="center"><span class="postdetails"> <c:choose>
										<c:when test="${not empty latestResponse}">
											<fmt:formatDate value="${latestResponse.createdTime}"
												dateStyle="medium" />
											<fmt:formatDate value="${latestResponse.createdTime}"
												pattern="hh:mm" />
											<br />
											<a
												href="displayProfile.do?userId=${latestResponse.author.id}">${latestResponse.author.name}</a>
											<a
												href="displayResponses.do?forumId=${forum.id}&topicId=${latestResponse.topic.id}#${latestResponse.id}"><img
												src="${contextPath}/templates/default/images/icon_latest_reply.gif"
												border="0" alt="Latest Reply" /></a>
										</c:when>
										<c:otherwise>
											<fmt:message key="recent_topics.jsp.noResponses" />
										</c:otherwise>
									</c:choose>
							</span></td>
						</tr>
					</c:forEach>
					<!-- END OF TOPICS LISTING -->
				</table>

				<table cellspacing="0" cellpadding="2" width="100%" align="center"
					border="0">
					<tr>
						<td align="left"><span class="gensmall"> <a
								class="gensmall" href="">&nbsp;</a>
						</span><span class="gensmall">&nbsp;</span></td>
					</tr>
				</table>
			</c:forEach>
		</td>
	</tr>
</table>

<%@ include file="/templates/default/common/footer.jsp"%>