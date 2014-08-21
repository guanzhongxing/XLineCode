<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<sf:form action="${contextPath}/users/${requestScope.userId}/password"
	method="put">
	<table cellspacing="0" cellpadding="10" width="100%" align="center"
		border="0">
		<tr>
			<td class="bodyline">
				<table class="forumline" cellspacing="1" cellpadding="3"
					width="100%" border="0">
					<tr>
						<td colspan="2"><span class="forumlink"><a
								class="forumlink" href="${contextPath}/forums"><fmt:message
										key="all.jsp.forums" /></a></span></td>
					</tr>
					<tr>
						<td colspan="2"><span class="gen" style="color: #ff0000">
								<b><c:if test="${requestScope.invalidPwd}">
										<fmt:message key="error.invalid.pwd" />
									</c:if> </b>
						</span></td>
					</tr>
					<tr>
						<th class="thhead" valign="middle" align="center" colspan="2"
							height="25"><fmt:message
								key="user_change_pwd.jsp.change.pwd" /></th>
					</tr>
					<tr>
						<td class="row2" colspan="2" align="center"><span
							class="gensmall"><font color="red"><fmt:message
										key="user_form.jsp.requiredFields" /></font></span></td>
					</tr>

					<tr>
						<td class="row1" width="45%" align="right"><span class="gen"><fmt:message
									key="user_form.jsp.currentPwd" />: *</span><br /></td>
						<td class="row2"><input type="password" name="currentPwd"
							class="post" style="WIDTH: 200px" size="25" maxlength="100" /><span
							class="gen" style="color: #ff0000"> <b><html:errors
										property="currentPwd" /></b>
						</span></td>
					</tr>

					<tr>
						<td class="row1" align="right"><span class="gen"><fmt:message
									key="user_form.jsp.newPwd" />: *</span><br /></td>
						<td class="row2"><input type="password" name="newPwd"
							class="post" style="WIDTH: 200px" maxlength="100" size="25" /><span
							class="gen" style="color: #ff0000"> <b><html:errors
										property="newPwd" /></b>
						</span></td>
					</tr>

					<tr>
						<td class="row1" align="right"><span class="gen"><fmt:message
									key="user_form.jsp.confirmPwd" />: *</span><br /></td>
						<td class="row2"><input type="password" name="confirmPwd"
							class="post" style="WIDTH: 200px" maxlength="100" size="25" /><span
							class="gen" style="color: #ff0000"> <b><html:errors
										property="confirmPwd" /></b>
						</span></td>
					</tr>

					<tr>
						<td class="row2" colspan="2" align="center"><input
							class="mainoption" tabindex="5" type="submit"
							value="<fmt:message
									key="user_change_pwd.jsp.submit" />"></td>
					</tr>

				</table>
			</td>
		</tr>
	</table>
</sf:form>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>