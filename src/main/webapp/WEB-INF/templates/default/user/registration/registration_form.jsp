<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="header.jsp.register" />
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<script type="text/javascript">
	var act; //the variable might be a problem if too many users generate XHR request(too many idle variables)
	var xmlHttp;
</script>
<script type="text/javascript" src="scripts/CheckUserNameExistence.js"></script>

<html:form
	action="submitRegistration.do?fromAdmin=${requestScope.fromAdmin|| requestScope.RegistrationForm.fromAdmin}"
	method="post" enctype="multipart/form-data">
	<table cellspacing="2" cellpadding="2" width="100%" align="center"
		border="0">
		<tr>
			<td align="right"><span class="nav"><a class="nav"
					href="${contextPath}/forums"><fmt:message
							key="registration_form.jsp.forums" /></a></span></td>
		</tr>
	</table>

	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="2" height="25"><fmt:message
					key="registration_form.jsp.registerInformation" /></th>
		</tr>

		<tr>
			<td class="row2" colspan="2" align="center"><span
				class="gensmall" style="color: red"><fmt:message
						key="registration_form.jsp.requiredFields" /></span></td>
		</tr>
		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="registration_form.jsp.user" />: *</span></td>
			<td class="row2"><html:text styleClass="post"
					style="WIDTH: 200px" maxlength="25" size="25" property="userName"
					styleId="username" onfocus="javascript:act=false;"
					onkeyup="javascript:act=true;"
					onblur="showProcessIcon(act);startRequest('GET','checkUserNameExistence.do?userName='+this.value+'&timeStamp='+new Date().getTime(),true,null,act);" />
				<span class="gen" style="color: #ff0000"> <b><html:errors
							property="userName" /></b>
			</span></td>
		</tr>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="registration_form.jsp.emailAddress" />: *</span></td>
			<td class="row2"><html:text styleClass="post"
					style="WIDTH: 200px" maxlength="255" size="25" property="email" />
				<span class="gen" style="color: #ff0000"> <b><html:errors
							property="email" /></b>
			</span></td>
		</tr>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="registration_form.jsp.password" />: *</span></td>
			<td class="row2"><html:password property="pwd" styleClass="post"
					style="WIDTH: 200px" size="25" maxlength="100" /> <span
				class="gen" style="color: #ff0000"> <b><html:errors
							property="pwd" /></b>
			</span></td>
		</tr>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="registration_form.jsp.confirmPassword" />: * </span></td>
			<td class="row2"><html:password styleClass="post"
					style="WIDTH: 200px" maxlength="100" size="25"
					property="confirmPwd" /> <span class="gen" style="color: #ff0000">
					<b><html:errors property="confirmPwd" /></b>
			</span></td>
		</tr>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="registration_form.jsp.gender" />: * </span></td>
			<td class="row2"><html:radio styleClass="post" property="gender"
					value="m" /> <fmt:message key="registration_form.jsp.male" /> <html:radio
					styleClass="post" property="gender" value="f" /> <fmt:message
					key="registration_form.jsp.female" /> <span class="gen"
				style="color: #ff0000"> <b><html:errors property="gender" /></b>
			</span></td>
		</tr>

		<c:if test="${fromAdmin || requestScope.RegistrationForm.fromAdmin}">
			<c:choose>
				<c:when test="${requestScope.RegistrationForm.groupId!=0}">
					<c:set var="parentGroupId"
						value="${requestScope.RegistrationForm.groupId}" scope="request" />
				</c:when>
				<c:otherwise>
					<c:set var="parentGroupId" value="-1" scope="request" />
				</c:otherwise>
			</c:choose>

			<input type="hidden" name="fromAdmin" value="true">
			<c:set var="baseUrl" value="/do/admin/group/get?action=subGroup"
				scope="request" />
			<tr>
				<td class="row1"><span class="gen"><fmt:message
							key="registration_form.jsp.group" />: * </span></td>
				<td class="row2"><select name="groupId">
						<c:if test="${not empty requestScope.groups}">
							<c:forEach var="group" items="${requestScope.groups}">
								<option value="${group.id}"
									<c:if test="${group.id==parentGroupId}">selected</c:if>>${group.name}</option>
								<c:import url="${baseUrl}&&groupId=${group.id}" />
							</c:forEach>
						</c:if>
				</select> <html:errors property="groupId" /></td>
			</tr>
		</c:if>

		<tr>
			<td class="row1"><span class="gen"><fmt:message
						key="registration_form.jsp.avatar" />: * </span></td>
			<td class="row2"><html:file property="image" /> <html:errors
					property="image" /></td>
		</tr>

		<c:if test="${requestScope.registrationCaptchaEnabled}">
			<tr>
				<td align="right"><span class="gen"><fmt:message
							key="all.jsp.captcha" />:</span></td>
				<td><input type='hidden' id='YXM_here' /> <script
						type='text/javascript' charset='gbk' id='YXM_script'
						src='http://api.yinxiangma.com/api3/yzm.yinxiangma.php?pk=6e157d5b43e55a51b50d9c7ee715e31d&v=YinXiangMaJAVASKD_4.0'></script>
					<c:if test="${requestScope.wrongCaptcha}">
						<span class="gen" style="color: #ff0000"> <b><fmt:message
									key="error.form.captcha.wrong" /></b>
						</span>
					</c:if></td>
			</tr>
		</c:if>
		<tr align="center">
			<td class="catbottom" colspan="2" height="28"><input
				class="mainoption" type="submit"
				value="<fmt:message key="registration_form.jsp.submit"/>"
				name="submit" />&nbsp;&nbsp; <input class="liteoption" type="reset"
				value="<fmt:message key="all.jsp.reset"/>" name="reset" /></td>
		</tr>
	</table>
</html:form>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>