<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />

<c:choose>
	<c:when test="${param.permissionConfig}">
		<c:set var="colspan" value="3" scope="request" />
		<c:set var="permissionConfig" value="true" scope="request" />
	</c:when>
	<c:otherwise>
		<c:set var="colspan" value="1" scope="request" />
		<c:set var="permissionConfig" value="false" scope="request" />
	</c:otherwise>
</c:choose>
<html:form action="/admin/group/post?action=delete" method="post">
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="4" height="25"><fmt:message
					key="group_list.jsp.title" /></th>
		</tr>

		<tr>
			<td class="row2" colspan="${colspan}" width="38%"><span
				class="gen"><b><fmt:message
							key="group_list.jsp.group.name" /></b></span></td>
			<c:if test="${!permissionConfig}">
				<td class="row2" align="center"><span class="gen"><b><fmt:message
								key="group_list.jsp.action" /></b></span></td>
				<td class="row2" align="center" width="10%"><span class="gen"><b><fmt:message
								key="group_list.jsp.delete" /></b></span></td>
			</c:if>
			<td class="row2" align="center"><span class="gen"><b><fmt:message
							key="group_list.jsp.security" /></b></span></td>
		</tr>

		<c:forEach var="group" items="${requestScope.groups}">
			<tr>
				<td class="row1" colspan="${colspan}"><span class="gen">${group.name}</span></td>
				<c:if test="${!permissionConfig}">
					<td class="row1" align="center"><span class="gen"><a
							href="${contextPath}/admin/groups/form?groupId=${group.id}"><fmt:message
									key="group_list.jsp.edit" /></a></span></td>
					<td class="row1" align="center"><c:if
							test="${group.deletable}">
							<input type="checkbox" name="groupIds" value="${group.id}" />
						</c:if></td>
				</c:if>
				<td class="row1" align="center"><span class="gen"><a
						href="${contextPath}/admin/permissions/${group.id}<c:if test="${permissionConfig}">?permissionType=backend</c:if>"><fmt:message
								key="group_list.jsp.security" /></a></span></td>
			</tr>

			<c:import
				url="/do/admin/group/get?action=subGroup&type=list&groupId=${group.id}" />
		</c:forEach>

		<c:if test="${!permissionConfig}">
			<tr align="center">
				<td class="catbottom" colspan="4" height="28"><input
					class="mainoption" type="button"
					value="<fmt:message key="group_list.jsp.new" />" name="button"
					onclick="document.location = '${contextPath}/admin/groups/form';" />
					&nbsp;&nbsp; <input class="mainoption" type="submit"
					value="<fmt:message key="group_list.jsp.delete" />" /></td>
			</tr>
		</c:if>
	</table>
</html:form>

