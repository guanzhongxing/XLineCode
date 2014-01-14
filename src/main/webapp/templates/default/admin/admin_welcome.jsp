<%@ include file="/templates/default/common/common_header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8;" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/style.css" />

<style type="text/css">
h1 {
	font-size: 22px;
	font-family: arial, verdana;
}

p,td {
	font-size: 11px;
}
</style>
</head>
<body bgcolor="#E5E5E5" text="#000000">

	<a name="top"></a>

	<h1>
		<fmt:message key="admin_welcome.jsp.forum.statistic" />
	</h1>

	<table width="100%" cellpadding="4" cellspacing="1" border="0"
		class="forumline">
		<tr>
			<th width="25%" nowrap="nowrap" height="25" class="thcornerl"><fmt:message
					key="admin_welcome.jsp.statistic" /></th>
			<th width="25%" height="25" class="thtop"><fmt:message
					key="admin_welcome.jsp.value" /></th>

			<th width="25%" nowrap="nowrap" height="25" class="thtop"><fmt:message
					key="admin_welcome.jsp.statistic" /></th>
			<th width="25%" height="25" class="thcornerr"><fmt:message
					key="admin_welcome.jsp.value" /></th>
		</tr>
		<tr>
			<td class="row1" nowrap="nowrap"><fmt:message
					key="admin_welcome.jsp.response.num" /></td>
			<td class="row1"><b>${rspNum}</b></td>
			<td class="row1" nowrap="nowrap"><fmt:message
					key="admin_welcome.jsp.response.perDay" /></td>

			<td class="row2"><b>${rspsPerDay}</b></td>
		</tr>
		<tr>
			<td class="row2" nowrap="nowrap"><fmt:message
					key="admin_welcome.jsp.topic.num" /></td>
			<td class="row2"><b>${topicNum}</b></td>
			<td class="row2" nowrap="nowrap"><fmt:message
					key="admin_welcome.jsp.topic.perDay" /></td>
			<td class="row2"><b>${topicsPerDay}</b></td>

		</tr>
		<tr>
			<td class="row1" nowrap="nowrap"><fmt:message
					key="admin_welcome.jsp.user.num" /></td>
			<td class="row1"><b>${userNum}</b></td>
			<td class="row1" nowrap="nowrap"><fmt:message
					key="admin_welcome.jsp.user.perDay" /></td>
			<td class="row1"><b>${usersPerDay}</b></td>
		</tr>

	</table>
	<h1>
		<fmt:message key="admin_welcome.jsp.whoIsOnline" />
	</h1>

	<div style="overflow: auto; width: 100%; height: 280px;">
		<table width="100%" cellpadding="4" cellspacing="1" border="0"
			class="forumline">
			<tr>
				<th width="20%" class="thcornerl" height="25">&nbsp;<fmt:message
						key="admin_welcome.jsp.username" />&nbsp;
				</th>
				<th width="20%" height="25" class="thtop">&nbsp;<fmt:message
						key="admin_welcome.jsp.logIn" />&nbsp;
				</th>
				<th width="20%" class="thtop">&nbsp;<fmt:message
						key="admin_welcome.jsp.lastUpdated" />&nbsp;
				</th>
				<th width="20%" height="25" class="thcornerr">&nbsp;<fmt:message
						key="admin_welcome.jsp.ipAddress" />&nbsp;
				</th>
			</tr>

			<c:forEach var="session" items="${requestScope.userSessions}">
				<tr>
					<td width="20%" class="row}">&nbsp;<span class="gen"><a
							href="${contextPath}/displayMyProfile.do?userId=${session.userId}" class="gen">${session.username}</a></span>&nbsp;
					</td>
					<td width="20%" align="center" class="row}">&nbsp;<span
						class="gen"><fmt:formatDate value="${session.loginDate}"
								dateStyle="medium" /> <fmt:formatDate
								value="${session.loginDate}" pattern="hh:mm" /></span>&nbsp;
					</td>
					<td width="20%" align="center" nowrap="nowrap" class="row}">&nbsp;<span
						class="gen"><fmt:formatDate value="${session.activatedDate}"
								dateStyle="medium" /> <fmt:formatDate
								value="${session.activatedDate}" pattern="hh:mm" /></span>&nbsp;
					</td>
					<td width="20%" class="row}">&nbsp;<span class="gen">${session.ip}</span>&nbsp;
					</td>
				</tr>
			</c:forEach>

			<tr>
				<td colspan="5" height="1" class="row3"><img
					src="${contextPath}/templates/default/images/spacer.gif" width="1"
					height="1" alt="." /></td>
			</tr>
		</table>
	</div>

	<br />

	<center>
		<span class="copyright">Copyright &copy; <a href="#">Vertonur
				Team</a>. All Rights Reserved.
		</span>
	</center>

</body>
</html>
