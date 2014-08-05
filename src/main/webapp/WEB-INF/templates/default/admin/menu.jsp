<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />
</head>
<body text="#000000" vlink="#5493b4" link="#006699" bgcolor="#e5e5e5">
	<table cellspacing="0" cellpadding="4" width="100%" align="center"
		border="0">
		<tr>
			<td align="center">
				<table class="forumline" cellspacing="1" cellpadding="4"
					width="100%" border="0">
					<tr>
						<th class="thhead" height="25"><b><fmt:message
									key="menu.jsp.admin" /></b></th>
					</tr>

					<tr>
						<td class="row1"><a id="forumIndex" class="genmed"
							target="_top" href="${contextPath}/forums"><fmt:message
									key="menu.jsp.forum.index" /></a></td>
					</tr>
					<tr>
						<td class="row1"><a id="forumIndex" class="genmed"
							target="main" href="${contextPath}/admin/statistic"><fmt:message
									key="menu.jsp.admin.index" /></a></td>
					<tr>
						<td class="catsides" height="28"><span class="cattitle"><fmt:message
									key="menu.jsp.forum.admin" /></span></td>
					</tr>

					<c:if test="${enableOperateGroup}">
						<tr>
							<td class="row1"><a id="groups" class="genmed"
								href="${contextPath}/admin/groups" target="main"><fmt:message
										key="menu.jsp.groups" /></a></td>
						</tr>
					</c:if>

					<c:if test="${enableOperateUser}">
						<tr>
							<td class="row1"><a id="users" class="genmed"
								href="${contextPath}/users?admin" target="main"><fmt:message
										key="menu.jsp.users" /></a></td>
						</tr>
						<tr>
							<td class="row1">
								<table width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="20px">&nbsp;</td>
										<td><a id="pendingRegistrations" class="gensmall"
											href="#" target="main"><fmt:message
													key="menu.jsp.pending.activations" />-->TODO</a></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:if>

					<c:if test="${enableOperateForumzone}">
						<tr>
							<td class="row1"><a id="categories" class="genmed"
								href="${contextPath}/forumzones" target="main"><fmt:message
										key="menu.jsp.categories" /></a></td>
						</tr>
					</c:if>

					<c:if test="${enableOperateForum}">
						<tr>
							<td class="row1"><a id="forums" class="genmed"
								href="${contextPath}/admin/forums" target="main"><fmt:message
										key="menu.jsp.forums" /></a></td>
						</tr>
					</c:if>

					<tr>
						<td class="row1"><a id="forums" class="genmed"
							href="${contextPath}/moderations" target="main"><fmt:message
									key="menu.jsp.approving" /></a></td>
					</tr>

					<c:if test="${enableOperateRanking}">
						<tr>
							<td class="row1">
								<p>
									<a id="rankings" class="genmed" href="${contextPath}/rankings"
										target="main"><fmt:message key="menu.jsp.rankings" /></a>
								</p>
							</td>
						</tr>
					</c:if>

					<c:if test="${enableConfigPoints}">
						<tr>
							<td class="row1">
								<table width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="20px">&nbsp;</td>
										<td><a id="pointsConfig" class="gensmall"
											href="${contextPath}/rankings/points" target="main"><fmt:message
													key="menu.jsp.rankings.points.config" /></a></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:if>

					<c:if test="${enableConfigRuntimeParameter}">
						<tr>
							<td class="row1"><p>
									<a id="configurations" class="genmed"
										href="${contextPath}/system" target="main"><fmt:message
											key="menu.jsp.configs" /></a>
								</p></td>
						</tr>
					</c:if>

					<c:if test="${enableConfigBackendPermission}">
						<tr>
							<td class="row1"><p>
									<a id="configurations" class="genmed"
										href="${contextPath}/admin/groups?permissionConfig=true"
										target="main"><fmt:message
											key="menu.jsp.backend.perm.config" /></a>
								</p></td>
						</tr>
					</c:if>

					<%-- <tr>
						<td class="row1">
							<table width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td colspan="2" class="genmed"><fmt:message
											key="menu.jsp.attachments" /></td>
								</tr>
								<tr>
									<td width="20px">&nbsp;</td>
									<td><a id="attachments" class="gensmall" href="#"
										target="main"><fmt:message
												key="menu.jsp.attachments.configs" /></a></td>
								</tr>

								<tr>
									<td width="20px">&nbsp;</td>
									<td><a id="attachments" class="gensmall" href="#"
										target="main"><fmt:message
												key="menu.jsp.attachments.attach.quota" /></a></td>
								</tr>

								<tr>
									<td width="20px">&nbsp;</td>
									<td><a id="attachments" class="gensmall" href="#"
										target="main"><fmt:message
												key="menu.jsp.attachments.attach.ext.groups" /></a></td>
								</tr>

								<tr>
									<td width="20px">&nbsp;</td>
									<td><a id="attachments" class="gensmall" href="#"
										target="main"><fmt:message
												key="menu.jsp.attachments.attach.exts" /></a></td>
								</tr>
							</table>

						</td>
					</tr> --%>
				</table>
			</td>
		</tr>
	</table>

	<div align="center">
		<span class="copyright">Powered by <a class="copyright"
			href="#" target="_blank">Vertonur</a>
	</div>
	<br />

</body>
</html>
