<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />

<c:choose>
	<c:when test="${not empty requestScope.forum}">
		<c:set var="forumName" value="${requestScope.forum.name}" />
		<c:set var="forumDescription"
			value="${requestScope.forum.description}" />
		<c:set var="moderated" value="${requestScope.forum.moderated}" />
		<c:set var="forumzoneId" value="${requestScope.forum.forumzone.id}" />
		<c:set var="priority" value="${requestScope.forum.priority}" />

		<c:set var="method" value="put" />
		<c:set var="url" value="${contextPath}/forums/${requestScope.forum.id}" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="post" />
		<c:set var="url" value="${contextPath}/forums/form" />
	</c:otherwise>
</c:choose>

<sf:form action="${url}" method="${method}">
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="2" height="25"><fmt:message
					key="forum_form.jsp.title" /></th>
		</tr>
		
		<tr>
			<td align="center" colspan="5"><span class="gensmall"> <c:if
						test="${failed}">
						<fmt:message
							key="error.admin.forum.action.CreateForumAction.create.failed" />
					</c:if> <c:if test="${msgToBeModerated}">
						<fmt:message key="msg.pending.to.modernate" />
					</c:if>
			</span></td>
		</tr>

		<tr>
			<td class="row1" width="20%"><span class="gen"><fmt:message
						key="forum_form.jsp.forum.name" /></span></td>
			<td class="row2"><input type="text" class="post"
				style="WIDTH: 200px" maxlength="200" size="25" name="name"
				value="${forumName}" /> <html:errors property="forumName" /></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="forum_form.jsp.priority" /></span></td>
			<td class="row2"><input type="text" class="post"
				style="WIDTH: 200px" maxlength="150" size="25" name="priority"
				value="${priority}" /> <html:errors property="priority" /></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="forum_form.jsp.moderate" /></span></td>
			<td class="row2"><span class="gensmall"> <input
					class="post" type="radio" name="moderated" value="0"
					<c:if test="${!moderated}">checked</c:if> />&nbsp;<fmt:message
						key="forum_form.jsp.no" />&nbsp;&nbsp; <input class="post"
					type="radio" name="moderated" value="1"
					<c:if test="${moderated}">checked</c:if> />&nbsp;<fmt:message
						key="forum_form.jsp.yes" />
			</span></td>
		</tr>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="forum_form.jsp.forumzone" /></span></td>
			<td class="row2"><select name="forumzoneId">
					<c:forEach var="forumzone" items="${requestScope.allForumzones}">
						<option value="${forumzone.id}"
							<c:if test="${forumzoneId==forumzone.id}">selected</c:if>>${forumzone.name}</option>
					</c:forEach>
			</select></td>
		</tr>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="forum_form.jsp.desc" /></span></td>
			<td class="row2"><textarea name="description" cols="40"
					rows="10" class="post" style="width: 100%">${forumDescription}</textarea>
				<html:errors property="forumDescription" /></td>
		</tr>

		<tr align="center">
			<td class="catbottom" colspan="2" height="28"><input
				class="mainoption" type="submit"
				value="<fmt:message key="forum_form.jsp.update" />" name="submit" /></td>
		</tr>

	</table>
</sf:form>
