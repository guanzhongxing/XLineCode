<%@ include file="/templates/default/common/common_header.jsp"%>

<c:set var="baseUrl"
	value="/do/admin/group/get?action=subGroup&type=userOption&userId=${user.id}" />
<c:set var="userGroups" value="${user.groups}" />
<c:forEach var="subGroup" items="${group.subGroups}">
	<c:set var="selected" value="false" />
	<c:forEach var="userGroup" items="${userGroups}">
		<c:if test="${userGroup.id==subGroup.id}">
			<c:set var="selected" value="true" />
		</c:if>
	</c:forEach>

	<option value="${subGroup.id}" <c:if test="${selected}">selected</c:if>>
		<c:forEach begin="0" end="${subGroup.nestedLevel}">&nbsp;&nbsp;</c:forEach>
		${subGroup.name}
	</option>
	<c:import url="${baseUrl}&groupId=${subGroup.id}" />
</c:forEach>
