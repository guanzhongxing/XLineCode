<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="header.jsp.register" />
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<a name="top" id="top"></a>
<table width="100%" cellspacing="0" cellpadding="10" border="0"
	align="center">
	<tr>
		<td class="bodyline"><br />

			<table width="100%" cellspacing="2" cellpadding="2" border="0"
				align="center">
				<tr>
					<td align="left" class="nav"><a class="nav"
						href="${contextPath}/forums"><fmt:message
								key="registration_completion.jsp.forums" /></a></td>
				</tr>
			</table>

			<table class="forumline" width="100%" cellspacing="1" cellpadding="4"
				border="0">
				<tr>
					<th class="thhead" height="25"><b><fmt:message
								key="registration_completion.jsp.registrationCompleteTitle" /></b></th>
				</tr>
				<tr>
					<td class="row1">
						<table width="100%" cellspacing="0" cellpadding="1" border="0">
							<tr>
								<td>&nbsp;</td>
							</tr>

							<tr>
								<td align="left"><span class="gen"> <fmt:message
											key="registration_completion.jsp.registrationCompleteMsg">
											<fmt:param
												value="${contextPath}/displayMyProfile.do?userId=${param.userId}" />
											<fmt:param value="${contextPath}/forums" />
										</fmt:message>
								</span></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
			</table></td>
	</tr>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>