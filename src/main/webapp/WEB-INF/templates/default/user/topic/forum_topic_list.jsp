<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<c:set var="currentForum" value="${requestScope.currentForum}" />
<table cellspacing="0" cellpadding="10" width="100%" align="center"
	border="0">
	<tr>
		<td class="bodyline" valign="top">
			<table cellspacing="2" cellpadding="2" width="100%" align="center">
				<tr>
					<td width="50" valign="middle" align="left"><c:choose>
							<c:when test="${requestScope.enableNewTopic}">
								<a
									href="initCreateUserTopic.do?forumzoneId=${currentForum.forumzone.id}&forumId=${currentForum.id}"
									rel="nofollow" class="icon_new_topic"> <img
									src="${resourcesHost}/images/${locale}/post.gif"
									alt="" />
								</a>
							</c:when>
							<c:otherwise>
								<img
									src="${resourcesHost}/images/${locale}/post_disable.gif"
									title="<fmt:message
								key="all.jsp.lack.of.permission" />" />
							</c:otherwise>
						</c:choose></td>
					<td valign="middle" align="left" colspan="2"><a
						class="maintitle" href="${contextPath}/forums"> <fmt:message
								key="display_topics.jsp.forums" />
					</a>&raquo; <span class="maintitle" style="color: #DD6900">
							${currentForum.name} </span></td>
					<td class="nav" nowrap="nowrap" align="right"><%@ include
							file="/WEB-INF/templates/common/pagination/pagination.jsp"%>
					</td>
				</tr>
			</table>

			<table class="forumline" cellspacing="1" cellpadding="4" width="100%"
				border="0">
				<tr>
					<th class="thcornerl" nowrap="nowrap" align="center" colspan="2"
						height="25">&nbsp;<fmt:message key="display_topics.jsp.topic" />&nbsp;
					</th>
					<th class="thtop" nowrap="nowrap" align="center" width="50">&nbsp;<fmt:message
							key="display_topics.jsp.responses" />&nbsp;
					</th>
					<th class="thtop" nowrap="nowrap" align="center" width="100">&nbsp;<fmt:message
							key="all.jsp.author" />&nbsp;
					</th>
					<th class="thtop" nowrap="nowrap" align="center" width="50">&nbsp;<fmt:message
							key="display_topics.jsp.views" />&nbsp;
					</th>
					<th class="thcornerr" nowrap="nowrap" align="center">&nbsp;<fmt:message
							key="display_topics.jsp.lastResponse" />&nbsp;
					</th>
				</tr>

				<!-- TOPICS LISTING -->
				<c:set var="topics" value='${requestScope.announcements}' />
				<%@ include file="topic_list.jsp"%>
				<c:set var="showNoTopicTip" value='true' />
				<c:set var="topics" value='${requestScope.topics}' />
				<%@ include file="topic_list.jsp"%>

				<tr align="center">
					<td class="catbottom" valign="middle" align="right" colspan="6"
						height="28">
						<table cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td align="center"><span class="gensmall">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>


			<table cellspacing="2" cellpadding="2" width="100%" align="center"
				border="0">
				<tr>
					<td width="50" valign="middle" align="left"><c:choose>
							<c:when test="${requestScope.enableNewTopic}">
								<a
									href="initCreateUserTopic.do?forumzoneId=${currentForum.forumzone.id}&forumId=${currentForum.id}"
									rel="nofollow" class="icon_new_topic"> <img
									src="${resourcesHost}/images/${locale}/post.gif"
									alt="" />
								</a>
							</c:when>
							<c:otherwise>
								<img
									src="${resourcesHost}/images/${locale}/post_disable.gif"
									title="<fmt:message
								key="all.jsp.lack.of.permission" />" />
							</c:otherwise>
						</c:choose></td>
					<td valign="middle" align="left"><span class="nav"> <a
							class="nav" href="${contextPath}/forums"> <fmt:message
									key="display_topics.jsp.forums" />
						</a> &raquo; <span class="nav" style="color: #DD6900">${currentForum.name}</span>
					</span></td>
					<td class="nav" nowrap="nowrap" align="right"><%@ include
							file="/WEB-INF/templates/common/pagination/pagination.jsp"%>
					</td>
				</tr>

				<tr>
					<td align="left" colspan="3"><span class="nav"></span></td>
				</tr>
			</table>

			<table cellspacing="0" cellpadding="5" width="100%" border="0">
				<tr>
					<td align="left" class="gensmall"></td>
					<td align="right"><%@ include
							file="/WEB-INF/templates/default/user/common/forums_navigator.jsp"%>
					</td>
				</tr>
			</table>

			<table cellspacing="0" cellpadding="0" width="100%" align="center"
				border="0">
				<tr>
					<td valign="top" align="left"><%@ include
							file="/WEB-INF/templates/default/user/common/folder_descriptions.htm"%>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>