<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<script type="text/javascript">
	function validateForm(f) {
		if (f.userId.value == "") {
			alert('<fmt:message key="activate_account_manual.jsp.fill.user.id"/>');
			f.userId.focus();

			return false;
		}

		if (isNaN(f.userId.value)) {
			alert('<fmt:message key="activate_account_manual.jsp.invalid.user.id"/>');
			f.userId.focus();

			return false;
		}

		if (f.authCode.value == "") {
			alert('<fmt:message key="activate_account_manual.jsp.fill.auth.code"/>');
			f.authCode.focus();

			return false;
		}

		return true;
	}
</script>

<form action="manuallyActivateAcct.do" method="post"
	onsubmit="return validateForm(this);">

	<table cellspacing="2" cellpadding="2" width="100%" align="center"
		border="0">
		<tr>
			<td align="left"><span class="nav"><a class="nav"
					href="{contextPath}/forums"><fmt:message
							key="activate_account_manual.jsp.forum.index" /></a></span></td>
		</tr>
	</table>

	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="2" height="25"><fmt:message
					key="activate_account_manual.jsp.activate.account.title" /></th>
		</tr>

		<tr>
			<td class="row2" colspan="2" align="center"><span
				class="gensmall"><font color="red"><fmt:message
							key="activate_account_manual.jsp.required.fields" /></font></span></td>
		</tr>

		<tr>
			<td class="row1" width="38%" align="right"><span class="gen"><fmt:message
						key="activate_account_manual.jsp.user.id" />: *</span></td>
			<td class="row2"><input class="post" type="text"
				style="WIDTH: 200px" maxlength="25" size="25" name="userId" /></td>
		</tr>

		<tr>
			<td class="row1" align="right"><span class="gen"><fmt:message
						key="activate_account_manual.jsp.auth.code" />: *</span></td>
			<td class="row2"><input class="post" type="text"
				style="WIDTH: 200px" maxlength="255" size="25" name="authCode" /></td>
		</tr>

		<tr align="center">
			<td class="catbottom" colspan="2" height="28"><input
				class="mainoption" type="submit"
				value="<fmt:message key="activate_account_manual.jsp.submit"/>"
				name="submit" />&nbsp;&nbsp; <input class="liteoption" type="reset"
				value="<fmt:message key="activate_account_manual.jsp.reset"/>"
				name="reset" /></td>
		</tr>
	</table>
</form>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>