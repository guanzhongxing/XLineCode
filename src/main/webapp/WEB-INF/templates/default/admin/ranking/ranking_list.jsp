<%@ include file="/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/style.css" />

<html:form action="admin/ranking/delete" method="post">
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="5" height="25"><fmt:message
					key="ranking_list.jsp.title" /></th>
		</tr>

		<tr>
			<td class="row2"><span class="gen"><b><fmt:message
							key="ranking_list.jsp.name" /></b></span></td>
			<td class="row2" align="center"><span class="gen"><b><fmt:message
							key="ranking_list.jsp.points" /></b></span></td>
			<td class="row2" align="center"><span class="gen"><b><fmt:message
							key="ranking_list.jsp.time.ranking" /></b></span></td>
			<td class="row2" align="center"><span class="gen"><b><fmt:message
							key="ranking_list.jsp.action" /></b></span></td>
			<td class="row2" align="center"><span class="gen"><b><fmt:message
							key="ranking_list.jsp.delete" /></b></span></td>
		</tr>

		<html:errors />

		<c:set var="nestedLv" value="0" />
		<c:set var="ranking" value="${requestScope.ranking}" />
		<c:if test="${not empty ranking}">
			<c:import
				url="/do/admin/ranking/get?action=subList&rankingId=${ranking.id}&nestedLv=${nestedLv}" />
		</c:if>


		<c:set var="nestedLv" value="0" />
		<c:set var="ranking" value="${requestScope.timeRanking}" />
		<c:if test="${not empty ranking}">
			<c:import
				url="/do/admin/ranking/get?action=subList&rankingId=${ranking.id}&nestedLv=${nestedLv}" />
		</c:if>

		<tr align="center">
			<td class="catbottom" colspan="5" height="28"><input
				class="mainoption" type="button"
				value="<fmt:message key="ranking_list.jsp.new" />" name="button"
				onclick="document.location = '${contextPath}/do/admin/ranking/form';" />
				&nbsp;&nbsp; <input class="mainoption" type="submit"
				value="<fmt:message key="ranking_list.jsp.delete.selected" />"
				name="submit" /></td>
		</tr>
	</table>
</html:form>

