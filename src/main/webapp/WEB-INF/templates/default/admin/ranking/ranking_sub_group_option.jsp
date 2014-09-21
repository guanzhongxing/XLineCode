<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>

<c:forEach var="subGroup" items="${group.subGroups}">
	<c:set var="selected" value="false" />
	<c:forEach var="rankingGroupId" items="${requestScope.rankingGroupIds}">
		<c:if test="${rankingGroupId==subGroup.id}">
			<c:set var="selected" value="true" />
		</c:if>
	</c:forEach>

	<option value="${subGroup.id}" <c:if test="${selected}">selected</c:if>>
		<c:forEach begin="0" end="${subGroup.nestedLevel}">&nbsp;&nbsp;</c:forEach>
		${subGroup.name}
	</option>
	<c:import url="${contextPath}/admin/groups/${subGroup.id}?type=rankingOption" />
</c:forEach>
