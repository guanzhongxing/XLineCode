<%@ include file="/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/style.css" />
	
<c:choose>
	<c:when test="${not empty requestScope.RankingConfigForm}">
		<c:set var="rankingId" value="${requestScope.RankingConfigForm.id}" />
		<c:set var="name" value="${requestScope.RankingConfigForm.name}" />
		<c:set var="timeRanking"
			value="${requestScope.RankingConfigForm.timeRanking}" />
		<c:set var="limitHours"
			value="${requestScope.RankingConfigForm.limitHours}" />
		<c:set var="points" value="${requestScope.RankingConfigForm.points}" />
		<c:set var="rankingGroupIds"
			value="${requestScope.RankingConfigForm.groupIds}" scope="request" />
	</c:when>
	<c:when test="${not empty requestScope.ranking}">
		<c:set var="rankingId" value="${requestScope.ranking.id}" />
		<c:set var="name" value="${requestScope.ranking.name}" />
		<c:set var="timeRanking" value="${requestScope.ranking.timeRanking}" />
		<c:set var="limitHours" value="${requestScope.ranking.limitHours}" />
		<c:set var="points" value="${requestScope.ranking.points}" />
	</c:when>
</c:choose>

<html:form action="/admin/ranking/update" method="post">
	<input type="hidden" name="id" value="${rankingId}" />
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tbody>
			<tr>
				<th class="thhead" valign="middle" colspan="2" height="25"><fmt:message
						key="ranking_form.jsp.title" /></th>
			</tr>

			<tr>
				<td class="gen"><fmt:message key="ranking_form.jsp.name" /></td>
				<td class="row2"><input name="name" type="text" id="rank_title"
					value="${name}" /> <html:errors property="name" /></td>
			</tr>

			<tr>
				<td class="row1" width="38%"><span class="gen"><fmt:message
							key="ranking_form.jsp.time.ranking" /></span></td>
				<td class="row2"><input name="timeRanking" value="1"
					type="checkbox" id="rank_special"
					<c:if test="${timeRanking}">checked</c:if> /></td>
			</tr>

			<tr>
				<td class="row1" width="38%"><span class="gen"><fmt:message
							key="ranking_form.jsp.limit.hours" /></span></td>
				<td class="row2"><input name="limitHours" type="text"
					id="rank_min" value="${limitHours}" size="6" maxlength="5" /></td>
			</tr>

			<tr>
				<td class="row1" width="38%"><span class="gen"><fmt:message
							key="ranking_form.jsp.points" /></span></td>
				<td class="row2"><input name="points" type="text" id="rank_min"
					value="${points}" size="6" maxlength="5" /> <html:errors
						property="points" /></td>
			</tr>

			<c:if test="${not empty requestScope.groups}">
				<c:set var="baseUrl"
					value="/do/admin/group/get?action=subGroup&type=rankingOption"
					scope="request" />
				<tr>
					<td class="row1" width="38%"><span class="gen"><fmt:message
								key="ranking_form.jsp.groups" /></span></td>
					<td class="row2"><select name="groupIds" multiple size="4">
							<c:forEach var="group" items="${requestScope.groups}">
								<c:set var="selected" value="false" />
								<c:forEach var="rankingGroupId" items="${rankingGroupIds}">
									<c:if test="${rankingGroupId==group.id}">
										<c:set var="selected" value="true" />
									</c:if>
								</c:forEach>
								<option value="${group.id}"
									<c:if test="${selected}">selected</c:if>>${group.name}</option>
								<c:import url="${baseUrl}&groupId=${group.id}" />
							</c:forEach>
					</select></td>
				</tr>
			</c:if>

			<tr align="center">
				<td class="catbottom" colspan="2" height="28"><input
					class="mainoption" type="submit"
					value="<fmt:message key="ranking_form.jsp.update" />" name="submit" /></td>
			</tr>
		</tbody>
	</table>
</html:form>