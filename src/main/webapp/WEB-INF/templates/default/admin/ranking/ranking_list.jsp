<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />

<sf:form action="${contextPath}/rankings" method="delete">
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
			<c:import url="/rankings/${ranking.id}?nestedLv=${nestedLv}" />
		</c:if>


		<c:set var="nestedLv" value="0" />
		<c:set var="ranking" value="${requestScope.timeRanking}" />
		<c:if test="${not empty ranking}">
			<c:import url="/rankings/${ranking.id}?&nestedLv=${nestedLv}" />
		</c:if>

		<tr align="center">
			<td class="catbottom" colspan="5" height="28"><input
				class="mainoption" type="button"
				value="<fmt:message key="ranking_list.jsp.new" />" name="button"
				onclick="document.location = '${contextPath}/rankings/form';" />
				&nbsp;&nbsp; <input class="mainoption" type="submit"
				value="<fmt:message key="ranking_list.jsp.delete.selected" />"
				name="submit" /></td>
		</tr>
	</table>
</sf:form>

