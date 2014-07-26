<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="header.jsp.login" />
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<form action="${contextPath}/users/login" method="post">
	<table cellspacing="2" cellpadding="2" width="100%" align="center"
		border="0">
		<tr>
			<td class="nav" align="right"><a class="nav"
				href="${contextPath}/forums">&gt;&gt;&gt;<fmt:message
						key="forum_list.jsp.forums" /></a></td>
		</tr>
	</table>

	<table class="forumline" cellspacing="1" cellpadding="4" width="100%"
		align="center" border="0">
		<tr>
			<th class="thhead" nowrap="nowrap" height="25"><fmt:message
					key="userlogin.jsp.loginInfo" /></th>
		</tr>

		<c:if test="${requestScope.userNotFound}">
			<span class="gen" style="color: #ff0000"> <b><fmt:message
						key="error.form.usr.not.exist" /></b>
			</span>
		</c:if>
		<c:if test="${requestScope.pwdNotMatch}">
			<span class="gen" style="color: #ff0000"> <b><fmt:message
						key="error.form.wrong.pwd" /></b>
			</span>
		</c:if>
		<tr>
			<td class="row1">
				<table cellspacing="1" cellpadding="3" width="100%" border="0">
					<tr>
						<td align="center" colspan="2">&nbsp;</td>
					</tr>

					<tr>
						<td align="right" width="45%"><span class="gen"><fmt:message
									key="userlogin.jsp.email" />:</span></td>
						<td><input class="post" maxlength="40" size="25"
							name="userId" type="text" /> <c:if
								test="${requestScope.invalidUsrId}">
								<span class="gen" style="color: #ff0000"> <b><fmt:message
											key="error.form.usr.id.null" /></b>
								</span>
							</c:if></td>

						<input name="fromUrl" type="hidden" value="${header.Referer}" />
					</tr>

					<tr>
						<td align="right"><span class="gen"><fmt:message
									key="all.jsp.password" />:</span></td>
						<td><input class="post" type="password" maxlength="25"
							size="25" name="password" /> <c:if
								test="${requestScope.invalidPwd}">
								<span class="gen" style="color: #ff0000"> <b><fmt:message
											key="error.form.pwd.null" /></b>
								</span>
							</c:if></td>
					</tr>

					<c:if test="${requestScope.loginCaptchaEnabled}">
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
						<td colspan="2"><span class="gen"><label
								for="autologin"><fmt:message
										key="userlogin.jsp.auto.login" />: <input type="checkbox"
									id="autoLogin" name="autoLogin"></label></span></td>
					</tr>

					<tr align="center">
						<td colspan="2"><input class="mainoption" type="submit"
							name="login" value="<fmt:message key="all.jsp.login" />" /></td>
					</tr>

				</table>
			</td>
		</tr>
	</table>
</form>
<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>