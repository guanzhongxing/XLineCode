<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />

<table width="100%" align="center" cellpadding="4" cellspacing="1"
	border="0" class="forumline">
	<tr>
		<th class="thhead" align="center" height="28"><fmt:message
				key="moderation_list.jsp.pending.moderation" />
		</td>
	</tr>

	<c:choose>
		<c:when test="${empty requestScope.toModerateMap}">
			<tr>
				<td align="center"><span class="gensmall"><fmt:message
							key="moderation_list.jsp.pending.none.to.moderate" /></span></td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:if test="${requestScope.pendingLogNum>0}">
				<tr>
					<td class="catleft"><fmt:message
							key="moderation_list.jsp.pending.log.num" /><span
						class="cattitle">:${requestScope.pendingLogNum}</span></td>
				</tr>
			</c:if>
			<c:forEach var="toModerateEntry" items="${toModerateMap}">
				<c:set var="forumzone" value="${toModerateEntry.key}" />
				<c:set var="toModerateForumMap" value="${toModerateEntry.value}" />
				<c:forEach var="toModerateForumEntry" items="${toModerateForumMap}">
					<c:set var="forum" value="${toModerateForumEntry.key}" />
					<c:set var="toModerateNum" value="${toModerateForumEntry.value}" />
					<tr>
						<td class="catleft"><span class="cattitle">${forumzone.name}</span></td>
					</tr>

					<tr>
						<td class="row1">
							<table width="100%" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td class="catleft">&nbsp;<span class="gen"><b><fmt:message
													key="moderation_list.jsp.pending.forum.name" /></b></span></td>
									<td class="catleft" align="center"><span class="gen"><b><fmt:message
													key="moderation_list.jsp.pending.msgs.to.moderate" /></b></span></td>
									<td class="catleft" align="center"><span class="gen"><b><fmt:message
													key="moderation_list.jsp.pending.action" /></b></span></td>
								</tr>

								<tr>
									<td>&nbsp;<span class="gen">${forum.name}</span></td>
									<td align="center"><span class="gen">${toModerateNum}</span></td>
									<td align="center"><a class="gen"
										href="${contextPath}/do/admin/moderation?action=view&&forumId=${forum.id}"><fmt:message
												key="moderation_list.jsp.pending.view" /></a></td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>