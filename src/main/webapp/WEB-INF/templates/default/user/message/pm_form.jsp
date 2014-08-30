<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="all.jsp.privateMsg" />
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>
<%@ include
	file="/WEB-INF/templates/default/user/common/ckEditor_init.jsp"%>

<c:if test="${not empty requestScope.reviewedPm}" var="isReply"
	scope="page">
	<c:set var="reviewedPm" value="${requestScope.reviewedPm}" />
</c:if>

<sf:form action="${contextPath}/pms/form" styleId="post" modelAttribute="pm" method="post">
	<table cellspacing="0" cellpadding="10" width="100%" align="center"
		border="0">
		<tr>
			<td class="bodyline">
				<table cellspacing="2" cellpadding="2" width="100%" align="center"
					border="0">
					<tr>
						<td align="right"><span class="nav"> &raquo; <a
								class="nav" href="${contextPath}/forums"><fmt:message
										key="all.jsp.forums" /></a>
						</span></td>
					</tr>
				</table>

				<table class="forumline" cellspacing="1" cellpadding="3"
					width="100%" border="0">
					<tr>
						<th class="thhead" colspan="2" height="25"><b> <fmt:message
									key="all.jsp.privateMsg" />
						</b></th>
					</tr>

					<tr>
						<td class="row1" width="15%"><span class="gen"><b><fmt:message
										key="pm.jsp.to" /></b></span></td>
						<td class="row2" width="85%"><span class="gen"> <c:choose>
									<c:when test="${isReply}">
										<input type="hidden" name="receiverId"
											value="${reviewedPm.author.id}" />
											${reviewedPm.author.name}
								</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${not empty requestScope.receiver}">
												<input type="hidden" name="receiverId"
													value="${requestScope.receiver.id}" />
											${requestScope.receiver.name}
										</c:when>
											<c:otherwise>
												<select name="receiverId">
													<c:forEach var="user" items="${requestScope.users}">
														<option value="${user.id}">${user.name}</option>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
						</span></td>
					</tr>

					<tr>
						<td class="row1" width="15%"><span class="gen"><b><fmt:message
										key="all.jsp.msgSubject" /></b></span></td>
						<td class="row2" width="85%"><span class="gen"><html:errors
									property="subject" /><input class="subject" type="text"
								tabindex="2" maxlength="100" name="subject"
								value="<c:choose><c:when test="${isReply}">Re:${reviewedPm.subject}</c:when><c:otherwise>${requestScope.subject}</c:otherwise></c:choose>" /></span></td>
					</tr>

					<tr>
						<td class="row1" valign="top"><span class="gen"><b><fmt:message
										key="all.jsp.msgBody" /></b></span></td>

						<td class="row2" valign="top"><c:if
								test="${param.quote}" var="isQuoted" scope="page" />
							<div class="gen">
								<html:errors property="content" />
								<input type="hidden" name="isQuoted" value=true />
								<textarea name="content" cols="50" rows="15">
							<c:if test="${isQuoted}">
								<blockquote>
											<cite>${reviewedPm.author.name}</cite>
							
									
									</c:if>
							<c:choose>
								<c:when test="${isReply}">
									${reviewedPm.content}
								</c:when>
								<c:otherwise>
									${requestScope.content}
								</c:otherwise>
							</c:choose>
							<c:if test="${isQuoted}">
								</blockquote>
							</c:if>
						</textarea>
							</div></td>
					</tr>

					<tr>
						<td class="row1">&nbsp;</td>
						<td class="row2">
							<div>
								<%@ include
									file="/WEB-INF/templates/default/user/common/post_options_tab.jsp"%>
							</div>
						</td>
					</tr>

					<tr>
						<td align="center" height="28" colspan="2" class="catbottom">
							<input class="mainoption" id="btnSubmit" accesskey="s"
							tabindex="6" type="submit"
							value="<fmt:message key="all.jsp.submit"/>" name="post" />
						</td>
					</tr>
				</table>
			</td>
		</tr>

		<c:if test="${isReply}">
			<tr>
				<td colspan="2">
					<table border="0" cellpadding="3" cellspacing="0" width="100%"
						class="forumline">
						<tr>
							<th class="cathead" height="28" align="center"><b><span
									class="cattitle"><fmt:message key="all.jsp.reply" /></span></b></th>
						</tr>

						<tr>
							<td class="row1"><iframe width="100%" height="300"
									frameborder="0"
									src="${contextPath}/pms/${param.pmId}?iFrame=true"></iframe></td>
						</tr>
					</table>
				</td>
			</tr>
		</c:if>
	</table>
</sf:form>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>