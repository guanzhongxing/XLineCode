<%@ include file="/templates/default/common/common_header.jsp"%>
<%@ include file="/templates/default/user/common/header.jsp"%>
<%@ include file="/templates/default/user/common/ckEditor_init.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/tabs.css" />
<script type="text/javascript" src="${contextPath}/scripts/post.js"></script>

<c:choose>
	<c:when test="${not empty requestScope.edittedRsp}">
		<c:set var="title" value="${requestScope.edittedRsp.subject}" />
		<c:set var="topicId" value="${requestScope.edittedRsp.id}" />
		<c:set var="content" value="${requestScope.edittedRsp.content}" />
		<c:set var="editted" value="${requestScope.editted}" />
		<c:set var="fromModeration" value="${requestScope.fromModeration}" />
	</c:when>
	<c:otherwise>
		<c:set var="title" value="${requestScope.ResponseForm.title}" />
		<c:set var="topicId" value="${requestScope.ResponseForm.topicId}" />
		<c:set var="content" value="${requestScope.ResponseForm.content}" />
		<c:set var="fromModeration"
			value="${requestScope.ResponseForm.fromModeration}" />
		<c:set var="moderationReason"
			value="${requestScope.ResponseForm.moderationReason}" />
		<c:set var="editted" value="${requestScope.ResponseForm.editted}" />
	</c:otherwise>
</c:choose>

<html:form action="respondTopic.do" styleId="post"
	enctype="multipart/form-data">
	<table cellspacing="0" cellpadding="10" width="100%" align="center"
		border="0">
		<tr>
			<td class="bodyline">
				<table cellspacing="2" cellpadding="2" width="100%" align="center"
					border="0">
					<tr>
						<td align="right"><span class="nav"> <a class="nav"
								href="displayResponses.do?forumId=${param.forumId}&topicId=${param.topicId}">${requestScope.topicTitle}</a>&raquo;
								<a class="nav"
								href="displayTopics.do?forumzoneId=${param.forumzoneId}&forumId=${param.forumId}">${requestScope.forumName}</a>&raquo;
								<a class="nav" href="displayForums.do"><fmt:message
										key="respond_topic.jsp.forums" /></a>
						</span></td>
					</tr>
				</table>

				<table class="forumline" cellspacing="1" cellpadding="3"
					width="100%" border="0">
					<tr>
						<th class="thhead" colspan="2" height="25"><b> <fmt:message
									key="respond_topic.jsp.respondTopic">
									<fmt:param value="${requestScope.topicTitle}" />
								</fmt:message>
						</b></th>
					</tr>

					<tr>
						<td class="row1" width="15%"><span class="gen"><b><fmt:message
										key="respond_topic.jsp.subject" /></b></span></td>
						<td class="row2" width="85%"><span class="gen"><html:errors
									property="title" /><input class="subject" type="text"
								tabindex="2" maxlength="100" name="title"
								value="<c:choose><c:when test="${not empty title}">${title}</c:when><c:when test="${(empty title)&&!editted}"><fmt:message key="respond_topic.jsp.Re"><fmt:param value="${requestScope.topicTitle}"/></fmt:message></c:when></c:choose>" />
								<input type="hidden" name="forumId" value="${param.forumId}" />
								<input type="hidden" name="editted" value="${editted}" /> <input
								type="hidden" name="topicId" value="${param.topicId}" /> <input
								type="hidden" name="responseId" value="${param.responseId}" />
						</span></td>
					</tr>

					<tr>
						<td class="row1" valign="top"><span class="gen"><b><fmt:message
										key="respond_topic.jsp.body" /></b></span></td>
						<td class="row2" valign="top"><html:errors property="content" />
							<div class="gen">
								<textarea name="content" cols="50" rows="15">
									<c:choose>
										<c:when test="${not empty requestScope.quotedMsg}">
											<c:set var="quotedMsg" value="${requestScope.quotedMsg}" />
											<blockquote>
														<cite>${quotedMsg.author.name}</cite>
												${quotedMsg.content}
											</blockquote>
										</c:when>
										<c:otherwise>
											${content}
										</c:otherwise>
									</c:choose>
								</textarea>
							</div></td>
					</tr>

					<!-- Options -->
					<tr>
						<td class="row1">&nbsp;</td>
						<td class="row2">
							<div id="tabs10">
								<c:if test="${requestScope.fileInvalid}">
									<fmt:message key="error.message.exceedMaxAttmSize">
										<fmt:param value="${requestScope.maxSize}" />
									</fmt:message>
								</c:if>
								<ul>
									<li target="postOptions" class="current"><a
										href="javascript:void(0);"
										onClick="activateTab('postOptions', this);"><span>Options</span></a></li>
									<c:if test="${requestScope.attachmentsEnabled}">
										<li target="postAttachments"><a
											href="javascript:void(0);"
											onClick="activateTab('postAttachments', this);"><span>Attachments</span></a></li>
									</c:if>
								</ul>
							</div> <!-- Post Options -->
							<div id="postOptions" class="postTabContents">
								<div>
									<%@ include
										file="/templates/default/user/common/post_options_tab.jsp"%>
								</div>
							</div> <!-- Attachments tab --> <c:if
								test="${requestScope.attachmentsEnabled}">
								<div id="postAttachments" class="postTabContents"
									style="display: none;">
									<div>
										<%@ include
											file="/templates/default/user/common/post_attachments_tab.jsp"%>
									</div>
								</div>
							</c:if>
						</td>
					</tr>

					<c:if test="${fromModeration}">
						<tr>
							<td class="row1" valign="top"><span class="gen"><b><fmt:message
											key="respond_topic.jsp.mdr.log" /></b></span></td>
							<td class="row2 genmed">
								<table border="0" cellpadding="3" cellspacing="0" width="100%"
									class="forumline">
									<tr>
										<td class="row1"><html:errors property="moderationReason" /></td>
									</tr>
									<tr>
										<td class="row1"><fmt:message
												key="respond_topic.jsp.mdr.reason" />: <input type="text"
											name="moderationReason" size="50" value="${moderationReason}"><input
											type="hidden" name="fromModeration" value="true"></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:if>

					<tr>
						<td align="center" height="28" colspan="2" class="catbottom">
							<input class="mainoption" id="btnSubmit" accesskey="s"
							tabindex="6" type="submit"
							value="<fmt:message key="respond_topic.jsp.submit"/>" name="post" />
						</td>
					</tr>
				</table>
			</td>
		</tr>

		<tr>
			<td colspan="2">
				<table border="0" cellpadding="3" cellspacing="0" width="100%"
					class="forumline">
					<tr>
						<th class="cathead" height="28" align="center"><b><span
								class="cattitle"><fmt:message
										key="respond_topic.jsp.topicReview" /></span></b></th>
					</tr>

					<tr>
						<td class="row1"><iframe width="100%" height="300"
								frameborder="0"
								src="${contextPath}/displayResponses.do?forumId=${param.forumId}&topicId=${param.topicId}&iFrame=true"></iframe>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>

<%@ include file="/templates/default/common/footer.jsp"%>