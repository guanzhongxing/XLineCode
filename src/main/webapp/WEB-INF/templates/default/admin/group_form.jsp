<%@ include file="/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/style.css" />

<html:form action="/admin/group/post" method="post">
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="2" height="25"><fmt:message
					key="group_form.jsp.title" /></th>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="group_form.jsp.name" /></span></td>
			<td class="row2"><c:choose>
					<c:when test="${not empty requestScope.edittedGroup}">
						<input type="text" name="groupName" value="${edittedGroup.name}" />
					</c:when>
					<c:otherwise>
						<input type="text" name="groupName"
							value="${requestScope.GroupConfigForm.groupName}" />
					</c:otherwise>
				</c:choose> <html:errors property="groupName" /><input type="hidden"
				name="groupId" value="${edittedGroup.id}" /></td>
		</tr>


		<c:choose>
			<c:when test="${not empty requestScope.edittedGroup}">
				<c:set var="baseUrl"
					value="/do/admin/group/get?action=subGroup&&edittedGroupId=${edittedGroup.id}"
					scope="request" />
			</c:when>
			<c:otherwise>
				<c:set var="baseUrl" value="/do/admin/group/get?action=subGroup"
					scope="request" />
			</c:otherwise>
		</c:choose>
		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="group_form.jsp.parent.name" /></span></td>
			<td class="row2"><c:choose>
					<c:when test="${requestScope.GroupConfigForm.parentGroupId!=0}">
						<c:set var="parentGroupId"
							value="${requestScope.GroupConfigForm.parentGroupId}"
							scope="request" />
					</c:when>
					<c:when test="${not empty requestScope.edittedGroup}">
						<c:set var="groupId" value="${requestScope.edittedGroup.id}"
							scope="request" />
						<c:set var="parentGroupId"
							value="${requestScope.edittedGroup.parent.id}" scope="request" />
					</c:when>
					<c:otherwise>
						<c:set var="parentGroupId" value="-1" scope="request" />
					</c:otherwise>
				</c:choose> <html:errors property="parentGroupId" /><select
				name="parentGroupId">
					<option value="0">
						<fmt:message key="group_form.jsp.top.lv.group" />
					</option>

					<c:if test="${not empty requestScope.groups}">
						<c:forEach var="group" items="${requestScope.groups}">
							<c:if test="${group.id!=groupId}">
								<option value="${group.id}"
									<c:if test="${group.id==parentGroupId}">selected</c:if>>${group.name}</option>
								<c:import url="${baseUrl}&&groupId=${group.id}" />
							</c:if>
						</c:forEach>
					</c:if>
			</select></td>
		</tr>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="group_form.jsp.grp.type" /></span></td>
			<td class="row2"><html:errors property="groupType" /><select
				name="groupType">
					<option value="GENERIC_GST"
						<c:if test="${edittedGroup.groupType=='GENERIC_GST'}">selected</c:if>>
						<fmt:message key="group_form.jsp.gst.grp" />
					</option>
					<option value="GENERIC_USR"
						<c:if test="${edittedGroup.groupType=='GENERIC_USR'}">selected</c:if>>
						<fmt:message key="group_form.jsp.usr.grp" />
					</option>
					<option value="GENERIC_ADMIN"
						<c:if test="${edittedGroup.groupType=='GENERIC_ADMIN'}">selected</c:if>>
						<fmt:message key="group_form.jsp.admin.grp" />
					</option>
					<option value="GENERIC_MDR"
						<c:if test="${edittedGroup.groupType=='GENERIC_MDR'}">selected</c:if>>
						<fmt:message key="group_form.jsp.mdr.grp" />
					</option>
			</select></td>
		</tr>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="group_form.jsp.desc" /></span></td>
			<td class="row2"><html:errors property="groupDesc" /> <textarea
					rows="4" cols="30" name="groupDesc" wrap="hard">
					<c:choose>
						<c:when test="${not empty requestScope.GroupConfigForm.groupDesc}">${requestScope.GroupConfigForm.groupDesc}</c:when>
						<c:when test="${not empty requestScope.edittedGroup}">${requestScope.edittedGroup.description}</c:when>
						<c:otherwise>
							<fmt:message key="group_form.jsp.desc.default" />
						</c:otherwise>
					</c:choose>
				</textarea></td>
		</tr>
		<tr align="center">
			<td class="catbottom" colspan="2" height="28"><input
				class="mainoption" type="submit"
				value="<fmt:message key="group_form.jsp.update" />" name="submit" /></td>
		</tr>
	</table>
</html:form>

