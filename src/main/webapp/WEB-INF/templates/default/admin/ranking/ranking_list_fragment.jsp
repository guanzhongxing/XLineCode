<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>

<c:if test="${not empty ranking}">
	<tr>
		<td class="row1" width="38%"><span class=gen><c:forEach
					begin="0" end="${nestedLv}">&nbsp;&nbsp;</c:forEach>${ranking.name}</span></td>
		<td width="26%" class="row1" align="center"><span class="gen">${ranking.points}</span></td>
		<td width="26%" class="row1" align="center"><span class="gen">${ranking.timeRanking}</span></td>
		<td width="26%" class="row1" align="center"><span class="gen"><a
				href="${contextPath}/do/admin/ranking/form?rankingId=${ranking.id}"><fmt:message
						key="ranking_list.jsp.edit" /></a></span></td>
		<td width="10%" class="row1" align="center"><input
			type="checkbox" name="ids" value="${ranking.id}" /></td>
	</tr>
	<c:set var="ranking" value="${ranking.seniorRanking}" />
	<c:set var="spaces" value="${spaces}+&nbsp;" />
	<c:if test="${ranking!=null}">
		<c:import
			url="/do/admin/ranking/get?action=subList&rankingId=${ranking.id}&nestedLv=${nestedLv}" />
	</c:if>
</c:if>

