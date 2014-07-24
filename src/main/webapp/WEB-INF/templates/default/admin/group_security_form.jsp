<%@ include file="/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/style.css" />
<script type="text/javascript">
	function checkThisField(fieldName) {
		if (fieldName.options[0].selected) {
			for (i = 1; i < fieldName.length; i++) {
				fieldName.options[i].selected = false;
			}
		} else {
			fieldName.options[0].selected = false;
		}

		if (fieldName.selectedIndex == -1) {
			fieldName.options[0].selected = true;
		}
	}
</script>

<html:form action="/admin/permission/update" method="post">
	<input type="hidden" name="groupId" value="${group.id}" />
	<input type="hidden" name="permissionType"
		value="${param.permissionType}" />

	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="3" height="25"><fmt:message
					key="group_security_form.jsp.title" /> - "<i>${group.name}</i>"</th>
		</tr>

		<tr>
			<td colspan="3"><span class="gen"> <font color="#ff0000"><b><center>
								<fmt:message key="group_security_form.jsp.how.to" />
								:
							</center></b></font> <br />
			</span> <span class="gensmall">
					<li><fmt:message key="group_security_form.jsp.restrictive" />.
				</li>
					<li><fmt:message key="group_security_form.jsp.allow.all.desc" />.
				</li>
					<li><fmt:message key="group_security_form.jsp.not.trivial" />.</li>
			</span></td>
		</tr>


		<c:forEach var="section" items="${requestScope.sections}">
			<c:if test="${section.displayed}">
				<tr>
					<td class="row1" colspan="3"><span class="gen"><b>${section.desc}</b></span></td>
				</tr>

				<c:forEach var="permissionConfig" items="${section.entries}">
					<tr>
						<td class="row2">&nbsp;</td>
						<td class="row2" valign="middle" width="33%"><span
							class="gensmall">${permissionConfig.desc}</span></td>
						<td class="row2" valign="middle" align="left"><span
							class="gensmall"> <c:choose>
									<c:when test="${permissionConfig.type=='SELECTION'}">
										<select name="${permissionConfig.name}PermissionIds"
											<c:if test="${permissionConfig.multiple}"> multiple size="4"</c:if>
											onChange="checkThisField(this)">
											<option value="-1"
												<c:if test="${permissionConfig.allowAll}"> selected</c:if>>
												<fmt:message key="group_security_form.jsp.allow.all" />
											</option>
											<c:forEach var="option" items="${permissionConfig.options}">
												<option value="${option.id}"
													<c:if test="${option.selected && !permissionConfig.allowAll}"> selected</c:if>>${option.name}</option>
											</c:forEach>
										</select>
									</c:when>
									<c:otherwise>
										<c:set var="option" value="${permissionConfig.option}" />
										<input type="checkbox"
											name="${permissionConfig.name}PermissionIds"
											value="${option.id}"
											<c:if test="${option.selected}">checked</c:if> />
									</c:otherwise>
								</c:choose></td>
					</tr>
				</c:forEach>
			</c:if>
		</c:forEach>

		<tr align="center">
			<td class="catbottom" colspan="3" height="28"><input
				class="mainoption" type="submit"
				value="<fmt:message key="group_security_form.jsp.save" />"
				name="submit" /></td>
		</tr>
	</table>
</html:form>
