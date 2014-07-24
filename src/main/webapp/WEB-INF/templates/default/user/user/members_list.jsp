<%@ include file="/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="header.jsp.members" />
<%@ include file="/templates/default/user/common/header.jsp"%>

<table cellspacing="0" cellpadding="0" width="100%" border="0">
	<tr>
		<td colspan="3">
			<table width="100%">
				<tr>
					<td align="left"><span class="nav"><a class="nav"
							href="displayForums.do">&gt;&gt;&gt;<fmt:message
									key="members_list.jsp.forums" /></a></span></td>
					<td class="nav" nowrap="nowrap" align="right"><%@ include
							file="/templates/common/pagination/pagination.jsp"%>
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
					href="displayProfile.do?userId=${member.id}">${member.name}</a></span></td>
			<td class="row2"><span class="gen"><a
					href="initCreatePrivateMsg.do?userId=${member.id}" class="icon_pm"><img
						src="${contextPath}/templates/default/images/${locale}/icon_pm.gif"
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

<%@ include file="/templates/default/common/footer.jsp"%>