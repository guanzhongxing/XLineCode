<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>

<c:forEach var="group" items="${group.subGroups}">
	<tr bgcolor="#f4f4f4">
		<c:choose>
			<c:when test="${permissionConfig}">
				<c:if test="${group.groupType=='GENERIC_ADMIN'}">
					<td colspan="${colspan}"><span class="gen"><c:forEach
								begin="0" end="${group.nestedLevel}">&nbsp;&nbsp;</c:forEach>
							${group.name} </span></td>
				</c:if>
			</c:when>
			<c:otherwise>
				<td colspan="${colspan}"><span class="gen"><c:forEach
							begin="0" end="${group.nestedLevel}">&nbsp;&nbsp;</c:forEach>
						${group.name} </span></td>
			</c:otherwise>
		</c:choose>

		<c:if test="${!permissionConfig}">
			<td align="center"><span class="gen"><a
					href="${contextPath}/admin/groups/form?groupId=${group.id}"><fmt:message
							key="group_list.jsp.edit" /></a></span></td>
			<td align="center"><c:if test="${group.deletable}">
					<input type="checkbox" name="groupIds" value="${group.id}" />
				</c:if></td>
		</c:if>

		<c:choose>
			<c:when test="${permissionConfig}">
				<c:if test="${group.groupType=='GENERIC_ADMIN'}">
					<td class="row2" align="center"><span class="gen"><a
							href="${contextPath}/admin/permissions/${group.id}"><fmt:message
									key="group_list.jsp.security" /></a></span></td>
				</c:if>
			</c:when>
			<c:otherwise>
				<td class="row2" align="center"><span class="gen"><a
						href="${contextPath}/admin/permissions/${group.id}"><fmt:message
								key="group_list.jsp.security" /></a></span></td>
			</c:otherwise>
		</c:choose>

	</tr>

	<c:import
		url="/admin/groups/${group.id}" />
</c:forEach>
