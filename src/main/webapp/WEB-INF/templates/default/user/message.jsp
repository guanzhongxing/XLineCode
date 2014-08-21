<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
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
								key="all.jsp.forums" /></a></td>
				</tr>
			</table>

			<table class="forumline" width="100%" cellspacing="1" cellpadding="4"
				border="0">
				<tr>
					<th class="thhead" height="25"><b><fmt:message
								key="message.jsp.info" /></b></th>
				</tr>

				<tr>
					<td class="row1">
						<table width="100%" cellspacing="0" cellpadding="1" border="0">
							<tr>
								<td>&nbsp;</td>
							</tr>

							<tr>
								<td align="center"><div class="gen">
										<c:choose>
											<c:when test="${not empty requestScope.registration}">
												<fmt:message key="message.jsp.activate.acct.msg" />
											</c:when>
											<c:when test="${not empty requestScope.invalidActivation}">
												<fmt:message key="message.jsp.invalid.activation">
													<fmt:param value="${requestScope.manualActivationUrl}" />
												</fmt:message>
											</c:when>
											<c:when test="${not empty requestScope.passwordChanged}">
												<fmt:message key="message.jsp.pwd.change" />
											</c:when>
											<c:when test="${requestScope.insufficientPermission}">
												<fmt:message key="message.jsp.insufficient.prms" />
											</c:when>
											<c:when test="${requestScope.accessDenied}">
												<fmt:message key="message.jsp.access.deny" />
												<c:if test="${requestScope.fullAuthRequired}">
													<br>
													<span class="gen" style="color: #ff0000"> <b><fmt:message
																key="message.jsp.full.auth.required" /></b>
													</span>
												</c:if>
											</c:when>
											<c:when test="${requestScope.saveCmtToLockedInfo}">
												<fmt:message key="message.jsp.save.cmt.to.locked.info" />
											</c:when>
											<c:when test="${requestScope.requireCaptcha}">
												<form action="${contextPath}/attachments/${requestScope.attachmentId}" method="post">
													<input type='hidden' id='YXM_here' />
													<script type='text/javascript' charset='gbk'
														id='YXM_script'
														src='http://api.yinxiangma.com/api3/yzm.yinxiangma.php?pk=6e157d5b43e55a51b50d9c7ee715e31d&v=YinXiangMaJAVASKD_4.0'></script>
													<c:if test="${requestScope.wrongCaptcha}">
														<span class="gen" style="color: #ff0000"> <b><fmt:message
																	key="error.form.captcha.wrong" /></b>
														</span>
													</c:if>
													<input class="mainoption" type="submit"
														value="<fmt:message key="all.jsp.ok" />" />
												</form>
											</c:when>
											<c:otherwise>
												<fmt:message key="message.jsp.waiting.moderation">
													<fmt:param value="${container}" />
													<fmt:param value="${dispatchPath}" />
												</fmt:message>
											</c:otherwise>
										</c:choose>
									</div></td>
							</tr>

							<tr>
								<td>&nbsp;</td>
							</tr>

							<tr>
								<td align="center"><a class="nav"
									href="${contextPath}/forums"><fmt:message
											key="all.jsp.forums" /></a></td>
							</tr>
						</table>
					</td>
				</tr>
			</table></td>
	</tr>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>