<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<script type="text/javascript" src="${resourcesHost}/javascripts/jquery.js"></script>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />
<script language="javascript">
	function changeValue(field, value) {
		var f = document.getElementById(field);
		f.value = value;
		resize();
		''
	}

	function resize() {
		var h = document.getElementById("thumbH").value;
		var w = document.getElementById("thumbW").value;

		if ((h > 30 && h < 350) && (w > 30 && w < 350)) {
			document.getElementById("avatarPreview").style.width = w + "px";
			document.getElementById("avatarPreview").style.height = h + "px";
		}
	}

	function testEmail() {
		if ($("#address").val()) {
			$("#mailButton").val(
					"<fmt:message key="config_list.jsp.mail.sending" />").attr(
					"disabled", "disabled");

			var params = {
				sender : $("#sender").val(),
				smtpHost : $("#host").val(),
				smtpPort : $("#port").val(),
				requireAuth : $("#auth").val(),
				requireSSL : $("#ssl").val(),
				smtpUsername : $("#username").val(),
				smtpPwd : $("#password").val(),
				address : $("#address").val()
			};

			$
					.ajax({
						type : "POST",
						url : "${contextPath}/mail",
						data : params,
						dataType : "text",
						global : false,
						success : function(data) {
							alert('Email send.');
						},
						error : function(data) {
							alert('Fail to send a test mail.');
						},
						complete : function(data) {
							$("#mailButton")
									.val(
											"<fmt:message key="config_list.jsp.mail.send.test" />")
									.removeAttr("disabled");
						}
					});
		}
	}
</script>

<sf:form action="${contextPath}/system" method="post">

	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="2" height="25"><fmt:message
					key="config_list.jsp.title" /></th>
		</tr>

		<!-- General Settings -->
		<tr>
			<td class="catsides" colspan="2"><span class="gen"><b><fmt:message
							key="config_list.jsp.general" /></b></span></td>
		</tr>
		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.site.url" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="50" name="homepageLink"
					value="${globalConfig.homepageLink}" /> <html:errors
						property="homepageLink" /></span></td>
		</tr>

		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.forum.name" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="50" name="forumName"
					value="${globalConfig.forumName}" /> <html:errors
						property="forumName" /></span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.forum.page.title" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" size="50" name="forumPageTitle"
					value="${globalConfig.forumPageTitle}" /> <html:errors
						property="forumPageTitle" /></span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.forum.desc" /></span></td>
			<td class="row1" width="38%"><span class="gen"> <textarea
						rows="12" cols="20" class="post" style="WIDTH: 400px" size="25"
						name="forumDesc">${globalConfig.forumDescription}</textarea> <html:errors
						property="forumDesc" />
			</span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.topics.per.page" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" size="10" name="topicsPerPage" value="${topicsPerPage}" />
					<html:errors property="topicsPerPage" /></span></td>
		</tr>

		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.responses.per.page" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="10" name="rspsPerPage" value="${rspsPerPage}" />
					<html:errors property="rspsPerPage" /></span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.users.per.page" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" size="10" name="usersPerPage" value="${usersPerPage}" />
					<html:errors property="usersPerPage" /></span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.new.topic.delay" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" size="10" name="newTopicInterval"
					value="${newTopicInterval}" /> <html:errors
						property="newTopicInterval" /></span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.new.rsp.delay" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" size="10" name="newRspInterval"
					value="${newRspInterval}" /> <html:errors
						property="newRspInterval" /></span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.disable.registraion" /></span></td>
			<td class="row1" width="38%"><span class="gen"> <select
					name="registrationEnabled">
						<c:choose>
							<c:when test="${registrationEnabled}">
								<option value="true" selected>
									<fmt:message key="config_list.jsp.true" />
								</option>
								<option value="false">
									<fmt:message key="config_list.jsp.false" />
								</option>
							</c:when>
							<c:otherwise>
								<option value="true">
									<fmt:message key="config_list.jsp.true" />
								</option>
								<option value="false" selected>
									<fmt:message key="config_list.jsp.false" />
								</option>
							</c:otherwise>
						</c:choose>
				</select>
			</span></td>
		</tr>

		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.registration.captcha" /></span></td>
			<td class="row2" width="38%"><span class="gen"><select
					name="registrationCaptchaEnabled">
						<c:choose>
							<c:when test="${globalConfig.registrationCaptchaEnabled}">
								<option value="false">
									<fmt:message key="config_list.jsp.false" />
								</option>
								<option value="true" selected>
									<fmt:message key="config_list.jsp.true" />
								</option>
							</c:when>
							<c:otherwise>
								<option value="false" selected>
									<fmt:message key="config_list.jsp.false" />
								</option>
								<option value="true">
									<fmt:message key="config_list.jsp.true" />
								</option>
							</c:otherwise>
						</c:choose>
				</select> </span></td>
		</tr>

		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.login.captcha" /></span></td>
			<td class="row2" width="38%"><span class="gen"> <select
					name="loginCaptchaEnabled">
						<c:choose>
							<c:when test="${globalConfig.loginCaptchaEnabled}">
								<option value="false">
									<fmt:message key="config_list.jsp.false" />
								</option>
								<option value="true" selected>
									<fmt:message key="config_list.jsp.true" />
								</option>
							</c:when>
							<c:otherwise>
								<option value="false" selected>
									<fmt:message key="config_list.jsp.false" />
								</option>
								<option value="true">
									<fmt:message key="config_list.jsp.true" />
								</option>
							</c:otherwise>
						</c:choose>
				</select>
			</span></td>
		</tr>

		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.download.captcha" /></span></td>
			<td class="row2" width="38%"><span class="gen"> <select
					name="downloadCaptchaEnabled">
						<c:choose>
							<c:when test="${globalConfig.downloadCaptchaEnabled}">
								<option value="false">
									<fmt:message key="config_list.jsp.false" />
								</option>
								<option value="true" selected>
									<fmt:message key="config_list.jsp.true" />
								</option>
							</c:when>
							<c:otherwise>
								<option value="false" selected>
									<fmt:message key="config_list.jsp.false" />
								</option>
								<option value="true">
									<fmt:message key="config_list.jsp.true" />
								</option>
							</c:otherwise>
						</c:choose>
				</select>
			</span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.session.timeout" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" size="10" name="sessionTimeout"
					value="${sessionTiming}" /> <html:errors property="sessionTimeout" /></span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.login.session.timeout" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" size="10" name="loginSessionTimeout"
					value="${loginSessionTimeout}" /> <html:errors
						property="loginSessionTimeout" /></span></td>
		</tr>

		<!-- Cache Settings -->
		<tr>
			<td class="catsides" colspan="2"><span class="gen"><b><fmt:message
							key="config_list.jsp.cache.settings" /></b></span></td>
		</tr>

		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.hot.topics.start" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="10" name="hotTopicDef" value="${hotTopicDef}" />
					<html:errors property="hotTopicDef" /></span></td>
		</tr>

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.recent.topics.limit" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" size="10" name="recentTopicPageNum"
					value="${recentTopicPageNum}" /> <html:errors
						property="recentTopicPageNum" /></span></td>
		</tr>

		<!-- Avatar -->
		<tr>
			<td class="catsides" colspan="2"><span class="gen"><b><fmt:message
							key="config_list.jsp.avatar" /></b></span></td>
		</tr>

		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.max.avatar.size" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="10" name="avatarSize" value="${avatarSize}" /> <html:errors
						property="avatarSize" /></span></td>
		</tr>
		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.max.avatar.wd" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" size="10" name="avatarWidth" value="${avatarWidth}"
					onchange="changeValue('thumbW', this.value)" /> <html:errors
						property="avatarWidth" /></span></td>
		</tr>
		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.max.avatar.height" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="10" name="avatarHeight" value="${avatarHeight}"
					onchange="changeValue('thumbH', this.value)" /> <html:errors
						property="avatarHeight" /></span></td>
		</tr>
		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.avatar.preview" /></span></td>
			<td class="row2" width="38%"><input type="hidden" id="thumbH"
				value="${avatarHeight}"> <input type="hidden" id="thumbW"
				value="${avatarWidth}">
				<div id="avatarPreview"
					style="align: center; border: 1px solid orange;">&nbsp;</div></td>
		</tr>

		<!-- Mail -->
		<tr>
			<td class="catsides" colspan="2"><span class="gen"><b><fmt:message
							key="config_list.jsp.mail" /></b></span></td>
		</tr>

		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.sender" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="50" name="sender" id="sender"
					value="${emailConfig.sender }" /> <html:errors property="sender" /></span></td>
		</tr>
		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.mail.host" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="50" name="smtpHost" id="host"
					value="${emailConfig.smtpHost }" /> <html:errors
						property="smtpHost" /></span></td>
		</tr>
		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.mail.port" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="10" name="smtpPort" id="port"
					value="${emailConfig.smtpPort }" /> <html:errors
						property="smtpPort" /></span></td>
		</tr>
		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.smtp.auth" /></span></td>
			<td class="row1" width="38%"><select name="requireAuth"
				id="auth">
					<c:choose>
						<c:when test="${emailConfig.requireAuth}">
							<option value="false">
								<fmt:message key="config_list.jsp.false" />
							</option>
							<option value="true" selected>
								<fmt:message key="config_list.jsp.true" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="false" selected>
								<fmt:message key="config_list.jsp.false" />
							</option>
							<option value="true">
								<fmt:message key="config_list.jsp.true" />
							</option>
						</c:otherwise>
					</c:choose>
			</select></td>
		</tr>
		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.mail.ssl" /></span></td>
			<td class="row1" width="38%"><select name="requireSSL" id="ssl">
					<c:choose>
						<c:when test="${emailConfig.requireSSL}">
							<option value="false">
								<fmt:message key="config_list.jsp.false" />
							</option>
							<option value="true" selected>
								<fmt:message key="config_list.jsp.true" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="false" selected>
								<fmt:message key="config_list.jsp.false" />
							</option>
							<option value="true">
								<fmt:message key="config_list.jsp.true" />
							</option>
						</c:otherwise>
					</c:choose>
			</select></td>
		</tr>
		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.smtp.username" /></span></td>
			<td class="row2" width="38%"><span class="gen"><input
					type="text" size="50" name="smtpUsername" id="username"
					value="${emailConfig.smtpUsername }" /> <html:errors
						property="smtpUsername" /></span></td>
		</tr>
		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.smtp.pwd" /></span></td>
			<td class="row1" width="38%"><span class="gen"><input
					type="text" name="smtpPwd" id="password"
					value="${emailConfig.smtpPwd }" /> <html:errors property="smtpPwd" /></span></td>
		</tr>
		<tr>
			<td class="row1" width="38%"><span class="gen"><font
					color="green"><fmt:message key="config_list.jsp.smtp.test" /></font></span></td>
			<td class="row1" width="38%"><span class="gen">E-mail: </span> <input
				type="text" id="address">&nbsp; <input type="button"
				value="<fmt:message key="config_list.jsp.mail.send.test" />"
				class="mainoption" id="mailButton" onClick="testEmail();"></td>
		</tr>
		<!-- Topic answer -->

		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.msg.format" /></span></td>
			<td class="row1" width="38%"><select name="emailFormat">
					<option value="text">Text</option>
					<option value="html">Html</option>
			</select></td>
		</tr>

		<tr>
			<td class="row2" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.email.notify" /></span></td>
			<td class="row2" width="38%"><select name="notifyUserOnNewRsp">
					<c:choose>
						<c:when test="${notifyUserOnNewRsp}">
							<option value="false">
								<fmt:message key="config_list.jsp.false" />
							</option>
							<option value="true" selected>
								<fmt:message key="config_list.jsp.true" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="false" selected>
								<fmt:message key="config_list.jsp.false" />
							</option>
							<option value="true">
								<fmt:message key="config_list.jsp.true" />
							</option>
						</c:otherwise>
					</c:choose>
			</select></td>
		</tr>
		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.user.email.auth" /></span></td>
			<td class="row1" width="38%"><select name="requireAuthEmail">
					<c:choose>
						<c:when test="${requireAuthEmail}">
							<option value="false">
								<fmt:message key="config_list.jsp.false" />
							</option>
							<option value="true" selected>
								<fmt:message key="config_list.jsp.true" />
							</option>
						</c:when>
						<c:otherwise>
							<option value="false" selected>
								<fmt:message key="config_list.jsp.false" />
							</option>
							<option value="true">
								<fmt:message key="config_list.jsp.true" />
							</option>
						</c:otherwise>
					</c:choose>
			</select></td>
		</tr>

		<tr>
			<td class="catsides" colspan="2"><span class="gen"><b><fmt:message
							key="config_list.jsp.user.mdr" /></b></span></td>
		</tr>
		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.user.mdr.dst.num" /></span></td>
			<td class="row1" width="38%"><input type="text" size="10"
				name="mdrDigestionNum" value="${mdrDigestionNum}" /> <html:errors
					property="mdrDigestionNum" /></td>
		</tr>
		<tr>
			<td class="row1" width="38%"><span class="gen"><fmt:message
						key="config_list.jsp.user.mdr.dst.ivl" /></span></td>
			<td class="row1" width="38%"><input type="text" size="10"
				name="assignPendingLogInterval" value="${assignPendingLogInterval}" />
				<html:errors property="assignPendingLogInterval" /></td>
		</tr>

		<tr>
			<td class="catsides" colspan="2" align="center"><input
				type="submit" value="<fmt:message key="all.jsp.update" />"
				class="mainoption" /></td>
		</tr>

	</table>
</sf:form>
<script language="javascript">
	resize();
</script>
