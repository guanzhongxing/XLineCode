<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />

<c:choose>
	<c:when test="${not empty requestScope.ForumzoneForm.forumzoneName}">
		<c:set var="forumzoneId" value="${requestScope.ForumzoneForm.forumzoneId}" />
		<c:set var="forumzoneName"
			value="${requestScope.ForumzoneForm.forumzoneName}" />
		<c:set var="forumzoneDescription"
			value="${requestScope.ForumzoneForm.forumzoneDescription}" />
		<c:set var="moderated" value="${requestScope.ForumzoneForm.moderate}" />
		<c:set var="priority" value="${requestScope.ForumzoneForm.priority}" />
	</c:when>
	<c:when test="${not empty requestScope.forumzone}">
		<c:set var="forumzoneName" value="${requestScope.forumzone.name}" />
		<c:set var="forumzoneDescription"
			value="${requestScope.forumzone.description}" />
		<c:set var="moderated" value="${requestScope.forumzone.moderated}" />
		<c:set var="forumzoneId" value="${requestScope.forumzone.id}" />
		<c:set var="priority" value="${requestScope.forumzone.priority}" />
	</c:when>
</c:choose>

<html:form action="${servicePath}" method="post">
	<input type="hidden" name="forumzoneId" value="${forumzoneId}" />
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="2" height="25"><fmt:message
					key="forumzone_form.jsp.form.title" /></th>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="forumzone_form.jsp.name" /></span></td>
			<td class="row2"><input type="text" class="post"
				style="WIDTH: 200px" maxlength="150" size="25" name="forumzoneName"
				value="${forumzoneName}" /> <html:errors property="forumzoneName" /></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="forumzone_form.jsp.desc" /></span></td>
			<td class="row2"><textarea rows="12" cols="20" class="post"
					style="WIDTH: 400px" size="25" name="forumzoneDescription">${forumzoneDescription}</textarea>
				<html:errors property="forumzoneDescription" /></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="forumzone_form.jsp.priority" /></span></td>
			<td class="row2"><input type="text" class="post"
				style="WIDTH: 200px" maxlength="150" size="25" name="priority"
				value="${priority}" /> <html:errors property="priority" /></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="forumzone_form.jsp.moderate" /></span></td>
			<td class="row2"><span class="gensmall"> <input
					class="post" type="radio" name="moderate" value="0"
					<c:if test="${!moderated}">checked</c:if> />&nbsp;<fmt:message
						key="forumzone_form.jsp.no" />&nbsp;&nbsp; <input class="post"
					type="radio" name="moderate" value="1"
					<c:if test="${moderated}">checked</c:if> />&nbsp;<fmt:message
						key="forumzone_form.jsp.yes" />
			</span></td>
		</tr>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="forumzone_form.jsp.choose.group" /></span></td>
			<td>-->TODO</td>
		</tr>

		<tr align="center">
			<td class="catbottom" colspan="2" height="28"><input
				class="mainoption" type="submit"
				value="<fmt:message key="forumzone_form.jsp.update" />"
				name="submit" /></td>
		</tr>
	</table>
</html:form>
