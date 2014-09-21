<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />

<sf:form action="${contextPath}/forums" method="delete">
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="6" height="25"><fmt:message
					key="forum_list.jsp.listing" /></th>
		</tr>

		<tr>
			<td align="center" colspan="5"><span class="gensmall"> <c:if
						test="${created}">
						<fmt:message
							key="message.admin.forum.action.CreateForumAction.create.succeed" />
					</c:if> <c:if test="${updated}">
						<fmt:message
							key="message.admin.forum.action.EditForumAction.edit.succeed" />
					</c:if> <c:if test="${msgToBeModerated}">
						<fmt:message key="msg.pending.to.modernate" />
					</c:if> <c:if test="${deleted}">
						<fmt:message
							key="message.admin.forum.action.DeleteForumAction.delete.succeed" />
					</c:if>
			</span></td>
		</tr>

		<c:forEach var="forumzone" items="${requestScope.allForumzones}">
			<c:if test="${!forumzone.deprecated}">
				<tr>
					<td colspan="6" class="catleft"><span class="catTitle">${forumzone.name}</span></td>
				</tr>

				<c:forEach var="forum" items="${forumzone.forums}">
					<c:if test="${!forum.deprecated}">
						<tr>
							<td width="10" class="row1">&nbsp;</td>
							<td class="row1"><span class="forumLink">${forum.name}</span></td>
							<td class="row1" align="center"><span class="gen"> <a
									id="forumEdit"
									href="${contextPath}/forums/form?forumId=${forum.id}"><fmt:message
											key="forum_list.jsp.edit" /></a>
							</span></td>
							<td class="row2" align="center" width="10%"><input
								type="checkbox" name="forumIds"
								value="${forum.id}" /></td>

							<td class="row2" align="center" width="10%"><span
								class="gen">${forum.priority}</span></td>
							<td class="row2" align="center" width="10%">&nbsp;</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
		</c:forEach>

		<tr align="center">
			<td class="catbottom" colspan="6" height="28"><input
				class="mainoption" type="button"
				value="<fmt:message key="forum_list.jsp.insert" />" id="btn_insert"
				name="button"
				onclick="document.location = '${contextPath}/forums/form';" />
				&nbsp;&nbsp; <input class="mainoption" type="submit"
				value="<fmt:message key="forum_list.jsp.delete" />" name="submit" />
			</td>
		</tr>
	</table>
</sf:form>
