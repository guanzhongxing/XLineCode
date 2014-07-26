<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />
<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
	border="0">
	<tr>
		<th class="thhead" valign="middle" colspan="6" height="25"><fmt:message
				key="user_list.jsp.admin.title" /></th>
	</tr>

	<c:set var="baseUrl" value="/do/admin/group/get?action=subGroup" />
	<tr>
		<td class="gen" align="center" colspan="6"><html:form
				action="/admin/user/list" method="post">
				<span class="gensmall"> <fmt:message
						key="user_list.jsp.search.by.name" /> <input type="text"
					name="username" value="${requestScope.username}" />&nbsp; <fmt:message
						key="user_list.jsp.search.by.group" /> <html:select
						property="groupId">
						<option value="0">
							<fmt:message key="user_list.jsp.all.groups" />
						</option>
						<c:if test="${not empty requestScope.groups}">
							<c:forEach var="group" items="${requestScope.groups}">
								<html:option value="${group.id}">${group.name}</html:option>
								<c:import url="${baseUrl}&&groupId=${group.id}" />
							</c:forEach>
						</c:if>
					</html:select> <input type="submit"
					value="<fmt:message key="user_list.jsp.search" />"
					class="mainoption" />
				</span>
			</html:form></td>
	</tr>

	<html:form action="/admin/user/lock" method="post">
		<tr>
			<td class="row2" width="10%" align="center"><span class="gen"><b><fmt:message
							key="user_list.jsp.user.id" /></b></span></td>
			<td class="row2"><span class="gen"><b><fmt:message
							key="user_list.jsp.username" /></b></span></td>
			<td class="row2" align="center"><span class="gen"><b>&nbsp;</b></span></td>
			<td class="row2" align="center"><span class="gen"><b>&nbsp;</b></span></td>
			<td class="row2" width="10%" align="center"><span class="gen"><b><fmt:message
							key="user_list.jsp.lock" /></b></span></td>
			<td class="row2" width="10%" align="center"><span class="gen"><b><fmt:message
							key="user_list.jsp.unlock" /></b></span></td>
		</tr>

		<c:forEach var="member" items="${requestScope.members}">
			<tr>
				<td class="row1" align="center"><span class="gen">${member.id}</span></td>
				<td class="row1"><span class="gen">${member.name}</span></td>
				<td class="row1" align="center"><span class="gen"><a
						href="${contextPath}/displayMyProfile.do?userId=${member.id}"><fmt:message
								key="user_list.jsp.edit" /></a></span></td>
				<td class="row1" align="center"><span class="gen"><a
						href="${contextPath}/do/admin/user/group/update?userId=${member.id}"><fmt:message
								key="user_list.jsp.groups" /></a></span></td>

				<c:choose>
					<c:when test="${member.locked}">
						<td class="row1" align="center">&nbsp;</td>
						<td class="row1" align="center"><input type="checkbox"
							name="userIds" value="${member.id}" /></td>
					</c:when>
					<c:otherwise>
						<td class="row1" align="center"><input type="checkbox"
							name="userIds" value="${member.id}" /></td>
						<td class="row1" align="center">&nbsp;</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>

		<tr align="center">
			<td class="catbottom" colspan="5" height="28"><input
				class="mainoption" type="button"
				value="<fmt:message key="user_list.jsp.insert" />"
				onClick="document.location = '${contextPath}/showAgreement.do?fromAdmin=true';" />&nbsp;&nbsp;
			</td>
			<td class="catbottom"><input class="mainoption" type="submit"
				value="<fmt:message key="user_list.jsp.lock" /> / <fmt:message key="user_list.jsp.unlock" />"
				name="submit" /></td>
		</tr>
</table>
</html:form>
<table width="100%">
	<tr>
		<td align="right"><%@ include
				file="/WEB-INF/templates/common/pagination/pagination.jsp"%></td>
	</tr>
</table>
