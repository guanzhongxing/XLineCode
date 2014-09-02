<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />

<sf:form action="${contextPath}/forumzones" method="delete">
	<table class="forumline" cellspacing="0" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="35 height="25"><fmt:message
					key="forumzone_list.jsp.listing" /></th>
		</tr>

		<tr>
			<td align="center" colspan="5"><span class="gensmall"> <c:if
						test="${created}">
						<fmt:message
							key="message.admin.forumzone.action.CreateForumzoneAction.create.succeed" />
					</c:if> <c:if test="${updated}">
						<fmt:message
							key="message.admin.forumzone.action.EditForumzoneAction.edit.succeed" />
					</c:if> <c:if test="${deleted}">
						<fmt:message
							key="message.admin.forumzone.action.DeleteForumzoneAction.delete.succeed" />
					</c:if>
			</span></td>
		</tr>

		<c:forEach var="forumzone" items="${requestScope.allForumzones}">
			<tr>
				<td class="catleft" width="38%"><span class="cattitle">${forumzone.name}</span></td>
				<td class="catleft" align="center"><span class="gen"><a
						id="categoryEdit"
						href="${contextPath}/forumzones/form?forumzoneId=${forumzone.id}"><fmt:message
								key="forumzone_list.jsp.edit" /></a></span></td>
				<td class="catleft" align="center" width="10%"><input
					type="checkbox" name="forumzoneIds" value="${forumzone.id}" /></td>

				<td class="catleft" align="center" width="10%"><span
					class="gen">${forumzone.priority}</span></td>

				<td class="catleft" align="center" width="10%">&nbsp;</td>
			</tr>

			<tr>
				<td colspan="5" class="row1">
					<table width="100%">
						<c:forEach var="forum" items="${forumzone.forums}">
							<c:if test="${!forum.deprecated}">
								<tr>
									<td>&nbsp;</td>
									<td class="row1" width="100%"><span class="gen">${forum.name}</td>
									<td>&nbsp;</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:forEach>

		<tr align="center">
			<td class="catbottom" colspan="5" height="28"><input
				class="mainoption" type="button"
				value="<fmt:message key="forumzone_list.jsp.insert" />"
				id="btn_insert" name="button"
				onclick="document.location = '${contextPath}/forumzones/form';" />
				&nbsp;&nbsp; <input class="mainoption" type="submit"
				value="<fmt:message key="forumzone_list.jsp.delete" />"
				name="submit" /></td>
		</tr>
	</table>
</sf:form>
