
<table cellspacing="2" cellpadding="2" width="100%" border="0">
	<tr>
		<td width="8%" align="left" valign="bottom" nowrap="nowrap"><c:choose>
				<c:when test="${currentTopic.locked}">
					<img
						src="${resourcesHost}/images/${locale}/reply_locked.gif"
						alt="" />
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${requestScope.enableNewRsp}">
							<a
								href="${contextPath}/forums/topics/${currentTopic.id}/response"
								rel="nofollow" class="icon_reply nav"> <img
								src="${resourcesHost}/images/${locale}/reply.gif"
								alt="" />
							</a>
						</c:when>
						<c:otherwise>
							<img
								src="${resourcesHost}/images/${locale}/reply_disable.gif"
								title="<fmt:message
								key="all.jsp.lack.of.permission" />" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose></td>
		<td valign="middle" align="left" colspan="0"><span class="nav">
				<a class="nav" href="${contextPath}/forums"><fmt:message
						key="user_specified_topics.jsp.forums" /></a> &raquo; <a class="nav"
				href="${contextPath}/forums/${forum.id}">${forum.name}
			</a>
		</span></td>
		<td class="nav" nowrap="nowrap" align="right"><%@ include
				file="/WEB-INF/templates/common/pagination/pagination.jsp"%>
		</td>
	</tr>
</table>

