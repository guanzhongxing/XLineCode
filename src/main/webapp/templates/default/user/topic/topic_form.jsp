<%@ include file="/templates/default/common/common_header.jsp"%>
<%@ include file="/templates/default/user/common/header.jsp"%>
<%@ include file="/templates/default/user/common/ckEditor_init.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/tabs.css" />
<script type="text/javascript" src="${contextPath}/scripts/post.js"></script>

<c:choose>
	<c:when test="${not empty requestScope.edittedTopic}">
		<c:set var="title" value="${requestScope.edittedTopic.subject}" />
		<c:set var="topicId" value="${requestScope.edittedTopic.id}" />
		<c:set var="content" value="${requestScope.edittedTopic.content}" />
		<c:set var="infoType" value="${requestScope.edittedTopic.infoType}" />
		<c:set var="editted" value="${requestScope.editted}" />
		<c:set var="fromModeration" value="${requestScope.fromModeration}" />
	</c:when>
	<c:otherwise>
		<c:set var="title" value="${requestScope.UserTopicForm.title}" />
		<c:set var="topicId" value="${requestScope.UserTopicForm.topicId}" />
		<c:set var="content" value="${requestScope.UserTopicForm.content}" />
		<c:set var="infoType" value="${requestScope.UserTopicForm.infoType}" />
		<c:set var="fromModeration"
			value="${requestScope.UserTopicForm.fromModeration}" />
		<c:set var="moderationReason"
			value="${requestScope.UserTopicForm.moderationReason}" />
		<c:set var="editted" value="${requestScope.UserTopicForm.editted}" />
	</c:otherwise>
</c:choose>

<html:form action="createUserTopic.do" styleId="post"
	enctype="multipart/form-data">
	<table cellspacing="0" cellpadding="10" width="100%" align="center"
		border="0">
		<tr>
			<td class="bodyline">
				<table cellspacing="2" cellpadding="2" width="100%" align="center"
					border="0">
					<tr>
						<td align="right"><span class="nav"> <a class="nav"
								href="displayTopics.do?forumzoneId=${param.forumzoneId}&forumId=${param.forumId}">${requestScope.forumName}</a>
								&raquo; <a class="nav" href="displayForums.do"><fmt:message
										key="topic_form.jsp.forums" /></a>
						</span></td>
					</tr>
				</table>

				<table class="forumline" cellspacing="1" cellpadding="3"
					width="100%" border="0">
					<tr>
						<th class="thhead" colspan="2" height="25"><b> <fmt:message
									key="topic_form.jsp.newTopic" />
						</b></th>
					</tr>

					<tr>
						<td class="row1" width="15%"><span class="gen"><b><fmt:message
										key="topic_form.jsp.subject" /></b></span></td>
						<td class="row2" width="85%"><span class="gen"> <input
								type="hidden" name="forumzoneId" value="${param.forumzoneId}" /><input
								type="hidden" name="forumId" value="${param.forumId}" /> <input
								type="hidden" name="forumName" value="${requestScope.forumName}" />
								<html:errors property="title" /> <input type="hidden"
								name="editted" value="${editted}" /> <input type="hidden"
								name="topicId" value="${topicId}" /> <input type="text"
								class="subject" tabindex="2" maxlength="100" name="title"
								value="${title}" />
						</span></td>
					</tr>

					<tr>
						<td class="row1" valign="top"><span class="gen"><b><fmt:message
										key="topic_form.jsp.body" /></b></span></td>

						<td class="row2" valign="top">
							<div class="gen">
								<html:errors property="content" />
								<textarea name="content" cols="50" rows="15">
								${content}
								</textarea>
							</div>
						</td>
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
							<td class="row2 genmed"><fmt:message
									key="respond_topic.jsp.mdr.reason" />: <input type="text"
								name="moderationReason" size="50" value="${moderationReason}"><input
								type="hidden" name="fromModeration" value="true"> <html:errors
									property="moderationReason" /></td>
						</tr>
					</c:if>

					<tr>
						<td align="center" height="28" colspan="2" class="catbottom">
							<input class="mainoption" id="btnSubmit" accesskey="s"
							tabindex="6" type="submit"
							value="<fmt:message key="topic_form.jsp.submit"/>" name="post" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>

<%@ include file="/templates/default/common/footer.jsp"%>