<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="header.jsp.members" />
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<table cellspacing="0" cellpadding="0" width="100%" border="0">
	<tr>
		<td colspan="3">
			<table width="100%">
				<tr>
					<td align="left"><span class="nav"><a class="nav"
							href="${contextPath}/forums">&gt;&gt;&gt;<fmt:message
									key="members_list.jsp.forums" /></a></span></td>
					<td class="nav" nowrap="nowrap" align="right"><%@ include
							file="/WEB-INF/templates/common/pagination/pagination.jsp"%>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
	border="0">
	<tr>
		<th class="thcornerl" nowrap="nowrap">#</th>
		<th class="thtop" nowrap="nowrap">&nbsp;<fmt:message
				key="members_list.jsp.username" />&nbsp;
		</th>
		<th class="thtop" nowrap="nowrap">&nbsp;<fmt:message
				key="all.jsp.privateMsg" />&nbsp;
		</th>
		<th class="thtop" nowrap="nowrap">&nbsp;<fmt:message
				key="members_list.jsp.email" />&nbsp;
		</th>
		<th class="thtop" nowrap="nowrap">&nbsp;<fmt:message
				key="members_list.jsp.registrationDate" />&nbsp;
		</th>
		<th class="thtop" nowrap="nowrap">&nbsp;<fmt:message
				key="members_list.jsp.topicNum" />&nbsp;
		</th>
		<th class="thtop" nowrap="nowrap">&nbsp;<fmt:message
				key="members_list.jsp.rspNum" />&nbsp;
		</th>
	</tr>

	<c:set var="members" value="${requestScope.members}" />
	<c:forEach var="member" items="${members}">
		<tr align="center">
			<td class="row2"><span class="gen">${member.id}</span></td>
			<td class="row2"><span class="gen"><a
					href="${contextPath}/users/${member.id}">${member.name}</a></span></td>
			<td class="row2"><span class="gen"><a
					href="${contextPath}/pms/form?userId=${member.id}" class="icon_pm"><img
						src="${resourcesHost}/images/${locale}/icon_pm.gif"
						alt="" /></a></span></td>
			<td class="row2"><span class="gen"><c:choose>
						<c:when test="${requestScope.showEmail}">
		${member.email}
	</c:when>
						<c:otherwise>
		***
		</c:otherwise>
					</c:choose></span></td>
			<td class="row2"><span class="gen"> <fmt:formatDate
						value="${member.regTime}" dateStyle="medium" /> <fmt:formatDate
						value="${member.regTime}" pattern="hh:mm" />
			</span></td>
			<td class="row2"><span class="gen">${member.statistician.topicNum}</span></td>
			<td class="row2"><span class="gen">${member.statistician.responseNum}</span></td>
		</tr>
	</c:forEach>

	<tr align="center">
		<td class="catbottom" colspan="10" height="28">&nbsp;</td>
	</tr>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>