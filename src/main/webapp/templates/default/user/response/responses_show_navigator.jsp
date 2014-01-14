
<table cellspacing="2" cellpadding="2" width="100%" border="0">
	<tr>
		<td width="8%" align="left" valign="bottom" nowrap="nowrap"><c:choose>
				<c:when test="${currentTopic.locked}">
					<img
						src="${contextPath}/templates/default/images/${locale}/reply_locked.gif"
						alt="" />
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${requestScope.enableNewRsp}">
							<a
								href="${contextPath}/initRespondTopic.do?forumzoneId=${forumzoneId}&topicId=${currentTopic.id}&forumId=${forum.id}"
								rel="nofollow" class="icon_reply nav"> <img
								src="${contextPath}/templates/default/images/${locale}/reply.gif"
								alt="" />
							</a>
						</c:when>
						<c:otherwise>
							<img
								src="${contextPath}/templates/default/images/${locale}/reply_disable.gif"
								title="<fmt:message
								key="all.jsp.lack.of.permission" />" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose></td>
		<td valign="middle" align="left" colspan="0"><span class="nav">
				<a class="nav" href="displayForums.do"><fmt:message
						key="user_specified_topics.jsp.forums" /></a> &raquo; <a class="nav"
				href="${contextPath}/displayTopics.do?forumzoneId=${forumzoneId}&forumId=${forum.id}">${forum.name}
			</a>
		</span></td>
		<td class="nav" nowrap="nowrap" align="right"><%@ include
				file="/templates/common/pagination/pagination.jsp"%>
		</td>
	</tr>
</table>

