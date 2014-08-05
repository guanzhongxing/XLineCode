<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />
<html:form action="/admin/user/group/update" method="post">
	<input type="hidden" name="userId" value="${user.id}" />
	<table class="forumline" cellSpacing="1" cellPadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="2" height="25"><fmt:message
					key="user_groups.jsp.groups.for" />&nbsp;${user.name}</th>
		</tr>

		<c:set var="baseUrl"
			value="/admin/groups" />
		<c:set var="userGroups" value="${user.groups}" />
		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="user_groups.jsp.group.name" /></span></td>
			<td class="row2"><select name="groupIds" multiple size="4">
					<c:forEach var="group" items="${requestScope.groups}">
						<c:set var="selected" value="false" />
						<c:forEach var="userGroup" items="${userGroups}">
							<c:if test="${userGroup.id==group.id}">
								<c:set var="selected" value="true" />
							</c:if>
						</c:forEach>
						<option value="${group.id}"
							<c:if test="${selected}">selected</c:if>>${group.name}</option>
						<c:import url="${baseUrl}/${group.id}?type=userOption&userId=${user.id}" />
					</c:forEach>
			</select></td>
		</tr>

		<tr align="center">
			<td class="catbottom" colspan="2" height="28"><input
				class="mainoption" type="submit"
				value="<fmt:message key="user_groups.jsp.update" />" name="submit" /></td>
		</tr>
	</table>
</html:form>

