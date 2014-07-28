<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="header.jsp.mdr.log" />
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<table cellspacing="0" cellpadding="10" width="100%" align="center"
	border="0">
	<tr>
		<td class="bodyline" valign="top">

			<table cellspacing="0" cellpadding="2" width="100%" align="center"
				border="0">
				<tr>
					<td valign="bottom" align="left"><a class="maintitle"
						href="${contextPath}/forums"><fmt:message
								key="display_topics.jsp.forums" /></a>&raquo; <span
						class="maintitle" style="color: #DD6900"><fmt:message
								key="moderation_acvitity_log.jsp.mdr.log" /></span></td>
					<td class="gen" align="right"><%@ include
							file="/WEB-INF/templates/common/pagination/pagination.jsp"%></td>
				</tr>
			</table>

			<table class="forumline gen" cellspacing="1" cellpadding="4"
				width="100%" border="0">
				<tr>
					<th class="thtop" align="center" height="20px"><fmt:message
							key="moderation_acvitity_log.jsp.date" /></th>

					<th class="thtop" align="center" height="20"><fmt:message
							key="moderation_acvitity_log.jsp.mdr" /></th>
					<th class="thtop" align="center" height="20" nowrap="nowrap"><fmt:message
							key="moderation_acvitity_log.jsp.usr" /></th>
					<th class="thtop" align="center" nowrap="nowrap"><fmt:message
							key="moderation_acvitity_log.jsp.typ" /></th>
					<th class="thtop" align="center" nowrap="nowrap" width="40"><fmt:message
							key="moderation_acvitity_log.jsp.rsn" /></th>
					<th class="thtop" align="center"><fmt:message
							key="moderation_acvitity_log.jsp.org.msg" /></th>
					<th class="thtop" align="center"><fmt:message
							key="moderation_acvitity_log.jsp.act" /></th>
				</tr>

				<c:forEach var="log" items="${requestScope.logs}">
					<tr>
						<td class="row2" valign="top" align="center"><fmt:formatDate
								value="${log.moderatedDate}" dateStyle="medium" /> <fmt:formatDate
								value="${log.moderatedDate}" pattern="hh:mm" /></td>

						<td class="row1" valign="top" align="center" nowrap="nowrap">
							<a
							href="${contextPath}/users/${log.moderator.id}">${log.moderator.name}</a>
						</td>
						<td class="row1" valign="top" align="center" nowrap="nowrap">
							<a
							href="${contextPath}/users/${log.infoAuthor.id}">${log.infoAuthor.name}</a>
						</td>
						<td class="row2" valign="top" align="center" nowrap="nowrap">
							<c:if test="${log.status=='DELETED'}">
								<c:if test="${log.logType=='INFO'}">
									<fmt:message key="moderation_acvitity_log.jsp.del.topic" />
								</c:if>
								<c:if test="${log.logType=='CMT'}">
									<fmt:message key="moderation_acvitity_log.jsp.del.rsp" />
								</c:if>
							</c:if> <c:if test="${log.status=='MODIFIED'}">
								<c:if test="${log.logType=='INFO'}">
									<fmt:message key="moderation_acvitity_log.jsp.edit.topic" />
								</c:if>
								<c:if test="${log.logType=='CMT'}">
									<fmt:message key="moderation_acvitity_log.jsp.edit.rsp" />
								</c:if>
							</c:if>
							<c:if test="${log.status=='MOVED'}">
								<fmt:message key="moderation_acvitity_log.jsp.moved" />
							</c:if>
							<c:if test="${log.status=='LOCKED'}">
								<fmt:message key="moderation_acvitity_log.jsp.locked" />
							</c:if>
							<c:if test="${log.status=='UNLOCKED'}">
								<fmt:message key="moderation_acvitity_log.jsp.unlocked" />
							</c:if>
						</td>
						<td class="row1" valign="top">${log.reason}</td>

						<td class="row2" valign="top">${log.archiveContent}</td>

						<td class="row2 gensmall" valign="top" align="center" width="100">
							<c:set var="content" value="${log.modifiedInfo}" /> <c:if
								test="${log.status!='DELETED'}">
								<c:choose>
									<c:when test="${content.state.deprecated}">
										<fmt:message key="moderation_acvitity_log.jsp.del.modified" />
									</c:when>
									<c:otherwise>
										<c:if test="${log.logType=='INFO'}">
											[<a
												href="${contextPath}/forums/topics/${content.id}"><fmt:message
													key="moderation_acvitity_log.jsp.view.topic" /></a>]
										</c:if>
										<c:if test="${log.logType=='CMT'}">
											<c:set var="info" value="${content.info}" />
											<c:choose>
												<c:when test="${info.state.deprecated}">
													<fmt:message key="moderation_acvitity_log.jsp.del.modified" />
												</c:when>
												<c:otherwise>
													[<a
														href="${contextPath}/forums/topics/${info.id}"><fmt:message
															key="moderation_acvitity_log.jsp.view.topic" /></a>]<br> [<a
														href="${contextPath}/forums/topics/${info.id}#${content.id}"><fmt:message
															key="moderation_acvitity_log.jsp.view.rsp" /></a>]
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>

			<table cellspacing="0" cellpadding="2" width="100%" align="center"
				border="0">
				<tr>
					<td valign="bottom" align="left"><a class="maintitle"
						href="${contextPath}/forums"><fmt:message
								key="display_topics.jsp.forums" /></a>&raquo; <span
						class="maintitle" style="color: #DD6900"><fmt:message
								key="moderation_acvitity_log.jsp.mdr.log" /></span></td>
					<td class="gen" align="right"><%@ include
							file="/WEB-INF/templates/common/pagination/pagination.jsp"%></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>