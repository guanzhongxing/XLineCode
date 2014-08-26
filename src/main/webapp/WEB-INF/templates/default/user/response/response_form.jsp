<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>
<%@ include
	file="/WEB-INF/templates/default/user/common/ckEditor_init.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/tabs.css" />
<script type="text/javascript"
	src="${resourcesHost}/javascripts/post.js"></script>

<c:choose>
	<c:when test="${not empty requestScope.edittedRsp}">
		<c:set var="subject" value="${requestScope.edittedRsp.subject}" />
		<c:set var="content" value="${requestScope.edittedRsp.content}" />
		<c:set var="editted" value="${requestScope.editted}" />
		<c:set var="fromModeration" value="${requestScope.fromModeration}" />
		<c:set var="method" value="put" />
		<c:set var="url" value="${contextPath}/forums/topics/${requestScope.topic.id}/${requestScope.edittedRsp.id}" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="post" />
		<c:set var="url" value="${contextPath}/forums/topics/${requestScope.topic.id}/response" />
	</c:otherwise>
</c:choose>

<sf:form action="${url}"
	method="${method}" styleId="post" modelAttribute="response"
	enctype="multipart/form-data">
	<table cellspacing="0" cellpadding="10" width="100%" align="center"
		border="0">
		<tr>
			<td class="bodyline">
				<table cellspacing="2" cellpadding="2" width="100%" align="center"
					border="0">
					<tr>
						<td align="right"><span class="nav"> <a class="nav"
								href="${contextPath}/forums/topics/${requestScope.topic.id}">${requestScope.topic.subject}</a>&raquo;
								<a class="nav"
								href="${contextPath}/forums/${requestScope.forum.id}">${requestScope.forum.name}</a>&raquo;
								<a class="nav" href="${contextPath}/forums"><fmt:message
										key="respond_topic.jsp.forums" /></a>
						</span></td>
					</tr>
				</table>

				<table class="forumline" cellspacing="1" cellpadding="3"
					width="100%" border="0">
					<tr>
						<th class="thhead" colspan="2" height="25"><b> <fmt:message
									key="respond_topic.jsp.respondTopic">
									<fmt:param value="${requestScope.topic.subject}" />
								</fmt:message>
						</b></th>
					</tr>

					<tr>
						<td class="row1" width="15%"><span class="gen"><b><fmt:message
										key="respond_topic.jsp.subject" /></b></span></td>
						<td class="row2" width="85%"><span class="gen"><html:errors
									property="title" /><input class="subject" type="text"
								tabindex="2" maxlength="100" name="subject"
								value="<c:choose><c:when test="${not empty subject}">${subject}</c:when><c:when test="${(empty subject)&&!editted}"><fmt:message key="respond_topic.jsp.Re"><fmt:param value="${requestScope.topic.subject}"/></fmt:message></c:when></c:choose>" />
								<input type="hidden" name="forumId"
								value="${requestScope.forum.id}" /> <input type="hidden"
								name="editted" value="${editted}" /> </span></td>
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
										file="/WEB-INF/templates/default/user/common/post_options_tab.jsp"%>
								</div>
							</div> <!-- Attachments tab --> <c:if
								test="${requestScope.attachmentsEnabled}">
								<div id="postAttachments" class="postTabContents"
									style="display: none;">
									<div>
										<%@ include
											file="/WEB-INF/templates/default/user/common/post_attachments_tab.jsp"%>
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
								src="${contextPath}/forums/topics/${requestScope.topic.id}?iFrame=true"></iframe>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</sf:form>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>