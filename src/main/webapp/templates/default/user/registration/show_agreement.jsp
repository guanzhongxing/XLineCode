<%@ include file="/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="header.jsp.register" />
<%@ include file="/templates/default/user/common/header.jsp"%>

<table class="forumline" align="center" border="0" cellpadding="3"
	cellspacing="1" width="80%">
	<tr>
		<th class="thhead" colspan="2" height="25" valign="middle"><fmt:message
				key="agreement_show.jsp.title" /></th>
	</tr>

	<c:choose>
		<c:when test="${requestScope.registrationEnabled}">
			<tr>
				<td class="row1" colspan="2" width="100%"><span class="gen">
						<fmt:message key="registration_completion.jsp.agreement" />
				</span></td>
			</tr>

			<tr align="center">
				<td class="catbottom" colspan="2" height="28"><input
					class="mainoption"
					value="<fmt:message key="agreement_show.jsp.accept"/>"
					name="submit"
					onclick="document.location = '${contextPath}/initRegistration.do?fromAdmin=${requestScope.fromAdmin}';"
					type="button">&nbsp;&nbsp; <input class="mainoption"
					value="<fmt:message key="agreement_show.jsp.noAccept"/>"
					onclick="document.location = '${contextPath}/displayForums.do';"
					type="button">&nbsp;&nbsp;</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td class="row1" colspan="2" width="100%"><span class="gen">
						<fmt:message key="registration_completion.jsp.registration.false" />
				</span></td>
			</tr>
		</c:otherwise>
	</c:choose>

</table>

<%@ include file="/templates/default/common/footer.jsp"%>