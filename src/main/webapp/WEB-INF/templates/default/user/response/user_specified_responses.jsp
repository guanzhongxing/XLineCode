<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="user_specified_responses.jsp.title" />
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<c:set var="displayedUser" value="${requestScope.displayedUser}" />
<table cellspacing="0" cellpadding="10" width="100%" align="center"
	border="0">
	<tr>
		<td class="bodyline">
			<table cellspacing="2" cellpadding="2" width="100%" border="0">
				<tr>
					<td valign="middle" align="left" colspan="2"><span
						class="maintitle"><fmt:message
								key="user_specified_responses.jsp.userResponses">
								<fmt:param value="${displayedUser.name}" />
							</fmt:message></span></td>
				</tr>
			</table>

			<table cellspacing="2" cellpadding="2" width="100%" border="0">
				<tr>
					<td valign="middle" align="left" colspan="0"><a class="nav"
						href="${contextPath}/forums"><fmt:message key="all.jsp.forums" /></a>
						&raquo; <a class="nav"
						href="${contextPath}/users/${displayedUser.id}"><fmt:message
								key="user_specified_responses.jsp.profileFor">
								<fmt:param value="${displayedUser.name}" />
							</fmt:message> </a> &raquo; <span class="nav"><fmt:message
								key="user_specified_responses.jsp.userResponses">
								<fmt:param value="${displayedUser.name}" />
							</fmt:message> </span></td>

					<td valign="middle" align="right"><%@ include
							file="/WEB-INF/templates/common/pagination/pagination.jsp"%>
					</td>
				</tr>
			</table>

			<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
				border="0">
				<tr>
					<th class="thleft" nowrap="nowrap" width="150" height="26"><fmt:message
							key="all.jsp.author" /></th>
					<th class="thright" nowrap="nowrap" width="100%"><fmt:message
							key="user_specified_responses.jsp.rsTitle" /></th>
				</tr>

				<!-- POST LISTING -->
				<c:set var="rowColor" value="" />
				<c:forEach var="response"
					items="${requestScope.userSpecifiedResponses}" varStatus="status">
					<c:choose>
						<c:when test="${status.count%2==0}">
							<c:set var="rowColor" value="row1" />
						</c:when>
						<c:otherwise>
							<c:set var="rowColor" value="row2" />
						</c:otherwise>
					</c:choose>

					<c:set var="topicOfRs" value="${response.topic}" />
					<c:set var="forumOfRs" value="${topicOfRs.forum}" />
					<c:set var="forumzoneId" value="${forumOfRs.forumzone.id}" />

					<tr>
						<td class="postInfo" colspan="2">
							<table width="100%">
								<tr>
									<td><span class="gen"> <a
											href="${contextPath}/forums/${forumOfRs.id}">${forumOfRs.name}</a>
											&raquo; <a
											href="${contextPath}/forums/topics/${topicOfRs.id}">${topicOfRs.subject}</a>
											&raquo; <a
											href="${contextPath}/forums/topics/${topicOfRs.id}/${response.id}#${response.id}"><fmt:message
													key="user_specified_responses.jsp.goToRs" /></a>
									</span></td>
									<td align="right"></td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<!-- Message -->
						<td class="${rowColor}" valign="top" colspan="2"><span
							class="postbody">${response.content}</span></td>
					</tr>

					<tr>
						<td class="spacerow" colspan="2" height="1"><img
							src="${resourcesHost}/images/spacer.gif" alt="" width="1"
							height="1" /></td>
					</tr>
				</c:forEach>
				<!-- END OF POST LISTING -->

				<tr align="center">
					<td class="catbottom" colspan="2" height="28">
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

					<td valign="middle" align="left" colspan="0"><a class="nav"
						href="${contextPath}/forums"><fmt:message key="all.jsp.forums" /></a>
						&raquo; <a class="nav"
						href="${contextPath}/user/${displayedUser.id}"><fmt:message
								key="user_specified_responses.jsp.profileFor">
								<fmt:param value="${displayedUser.name}" />
							</fmt:message></a> &raquo; <span class="nav"><fmt:message
								key="user_specified_responses.jsp.userResponses">
								<fmt:param value="${displayedUser.name}" />
							</fmt:message></span></td>

					<td valign="middle" align="right"><%@ include
							file="/WEB-INF/templates/common/pagination/pagination.jsp"%>
					</td>
				</tr>

				<tr>
					<td colspan="3"><img src="${resourcesHost}/images/spacer.gif"
						alt="" width="1" height="1" /></td>
				</tr>
			</table>

			<table cellspacing="0" cellpadding="0" width="100%" border="0">
				<tr>
					<td align="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>