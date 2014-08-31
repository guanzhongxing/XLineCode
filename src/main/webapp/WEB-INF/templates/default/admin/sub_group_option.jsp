<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>

<c:forEach var="subGroup" items="${group.subGroups}">
	<c:if test="${subGroup.id!=groupId}">
		<option value="${subGroup.id}"
			<c:if test="${subGroup.id==parentGroupId}">selected</c:if>>
			<c:forEach begin="0" end="${subGroup.nestedLevel}">&nbsp;&nbsp;</c:forEach>
			${subGroup.name} - -${groupId}
		</option>
		<c:import url="/admin/groups/${subGroup.id}" />
	</c:if>
</c:forEach>
