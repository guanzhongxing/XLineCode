<%@ include file="/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="header.jsp.myProfile" />
<%@ include file="/templates/default/user/common/header.jsp"%>

<set var="displayedUser" value="${requestScope.displayedUser}" />
<html:form action="updateMyProfile.do" method="post"
	enctype="multipart/form-data">
	<input type="hidden" name="userId" value="${displayedUser.id}" />
	<table cellspacing="0" cellpadding="10" width="100%" align="center"
		border="0">
		<tr>
			<td class="bodyline">
				<table class="forumline" cellspacing="1" cellpadding="3"
					width="100%" border="0">
					<tr>
						<td colspan="2"><span class="forumlink"><a
								class="forumlink" href="displayForums.do"><fmt:message
										key="all.jsp.forums" /></a></span></td>
					</tr>
					<tr>
						<th class="thhead" valign="middle" align="center" colspan="2"
							height="25"><fmt:message key="user_form.jsp.myProfile" /></th>
					</tr>
					<tr>
						<td class="row2" colspan="2" align="center"><span
							class="gensmall"><font color="red"><fmt:message
										key="user_form.jsp.requiredFields" /></font></span></td>
					</tr>

					<tr>
						<td class="row1" width="38%"><span class="gen"><fmt:message
									key="user_form.jsp.user" />: </span></td>
						<td class="row2"><span class="gen"> <input type="text"
								class="post" style="WIDTH: 200px" maxlength="255" size="25"
								name="name" value="${displayedUser.name}" /> <html:errors
									property="name" />
						</span></td>
					</tr>

					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.emailAddr" />: *</span></td>
						<td class="row2"><input type="text" class="post"
							style="WIDTH: 200px" maxlength="255" size="25" name="email"
							value="${displayedUser.email}" /> <input type="hidden"
							name="oldEmail" value="${displayedUser.email}" /> <html:errors
								property="email" /></td>
					</tr>

					<tr>
						<td class="row1"></td>
						<td class="row2"><a href="initChangePassword.do" />Change
							password</td>
					</tr>

					<tr>
						<th class="thsides" valign="middle" colspan="2" height="25"><fmt:message
								key="user_form.jsp.preferencesInfo" /></th>
					</tr>

					<tr>
						<td class="row2" colspan="2" align="center"><span
							class="gensmall"><font color="red"><fmt:message
										key="user_form.jsp.infoWillBePublicVisible" /></font></span></td>
					</tr>
					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.qq" />:</span></td>
						<td class="row2"><input type="text" class="post"
							style="WIDTH: 300px" maxlength="255" name="qq"
							value="${displayedUser.qq}" /> <html:errors property="qq" /></td>
					</tr>
					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.msn" />:</span></td>
						<td class="row2"><input type="text" class="post"
							style="WIDTH: 300px" maxlength="255" name="msn"
							value="${displayedUser.msn}" /> <html:errors property="msn" />
						</td>
					</tr>
					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.webSite" />:</span></td>
						<td class="row2"><input type="text" class="post"
							style="WIDTH: 300px" maxlength="255" size="25" name="website"
							value="${displayedUser.webSite}" /> <html:errors
								property="website" /></td>
					</tr>
					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.from" />:</span></td>
						<td class="row2"><input type="text" class="post"
							style="WIDTH: 300px" maxlength="100" size="25" name="location"
							value="${displayedUser.location}" /></td>
					</tr>
					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.interests" />:</span></td>
						<td class="row2"><input type="text" class="post"
							style="WIDTH: 300px" maxlength="150" size="35" name="interests"
							value="${displayedUser.interests}" /></td>
					</tr>
					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.signature" />:</span><br /> <span
							class="gensmall"><fmt:message
									key="user_form.jsp.signatureDef" /></span><br /></td>
						<td class="row2"><textarea class="post" style="WIDTH: 300px"
								name="signature" rows="6" cols="30">${displayedUser.signature}</textarea></td>
					</tr>
					<tr>
						<th class="thsides" valign="middle" colspan="2" height="25"><fmt:message
								key="user_form.jsp.preferences" /></th>
					</tr>
					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.showEmail" />:</span></td>
						<td class="row2"><input type="radio" value="1"
							name="showEmailAddr"
							<c:if test="${displayedUser.userPres.showEmailAddr==1}">checked="checked"</c:if> />
							<span class="gen"><fmt:message key="all.jsp.yes" /></span>&nbsp;&nbsp;
							<input type="radio" value="0" name="showEmailAddr"
							<c:if test="${displayedUser.userPres.showEmailAddr!=1}">checked="checked"</c:if> />
							<span class="gen"><fmt:message key="all.jsp.no" /></span></td>
					</tr>
					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.hideOnlineStatus" />:</span></td>
						<td class="row2"><input type="radio" value="1"
							name="hideOnlineStatus"
							<c:if test="${displayedUser.userPres.hideOnlineStatus==1}">checked="checked"</c:if> />
							<span class="gen"><fmt:message key="all.jsp.yes" /></span>&nbsp;&nbsp;
							<input type="radio" value="0" name="hideOnlineStatus"
							<c:if test="${displayedUser.userPres.hideOnlineStatus!=1}">checked="checked"</c:if> />
							<span class="gen"><fmt:message key="all.jsp.no" /></span></td>
					</tr>

					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.attachSignature" />:</span></td>
						<td class="row2"><input type="radio" value="1"
							name="attachSignature"
							<c:if test="${displayedUser.userPres.attachSignature==1}">checked="checked"</c:if> />
							<span class="gen"><fmt:message key="all.jsp.yes" /></span>&nbsp;&nbsp;
							<input type="radio" value="0" name="attachSignature"
							<c:if test="${displayedUser.userPres.attachSignature!=1}">checked="checked"</c:if> />
							<span class="gen"><fmt:message key="all.jsp.no" /></span></td>
					</tr>

					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.preferedLan" />:</span></td>
						<td class="row2"><select name="language">
								<option value="default">
									<fmt:message key="user_form.jsp.default" />
								</option>
								<option value="en_US"
									<c:if test="${displayedUser.userPres.locale=='en_US'}">selected="selected"</c:if>>
									<fmt:message key="user_form.jsp.en_US" />
								</option>
								<option value="zh_CN"
									<c:if test="${displayedUser.userPres.locale=='zh_CN'}">selected="selected"</c:if>>
									<fmt:message key="user_form.jsp.zh_CN" />
								</option>
								<option value="zh_KE"
									<c:if test="${displayedUser.userPres.locale=='zh_KE'}">selected="selected"</c:if>>
									<fmt:message key="user_form.jsp.zh_KE" />
								</option>
						</select></td>
					</tr>

					<tr>
						<th class="thsides" valign="middle" colspan="2" height="12"><fmt:message
								key="user_form.jsp.avatarControlPanel" /></th>
					</tr>
					<tr>
						<td class="row1" colspan="2">
							<table cellspacing="2" cellpadding="0" width="70%" align="center"
								border="0">
								<tr>
									<td width="65%"><span class="gensmall"><fmt:message
												key="user_form.jsp.avatarDesc" /></span></td>
									<td align="center"><span class="gensmall"><fmt:message
												key="user_form.jsp.currentAvatar" /></span><br /> <br /> <c:set
											var="attmInfo"
											value="${requestScope.displayedUser.avatar.attmInfo}" /> <c:set
											var="avatar" value="${requestScope.displayedUser.avatar}" />
										<c:choose>
											<c:when test="${attmInfo.attachmentType=='LOCAL'}">
												<c:set var="avatarUrl"
													value="${contextPath}/local/image.do?id=${avatar.id}" />
											</c:when>
											<c:otherwise>
												<c:set var="avatarUrl" value="${attmInfo.downloadUrl}" />
											</c:otherwise>
										</c:choose> <img src="${avatarUrl}" width="${avatarWidth}"
										height="${avatarHeight}" /> <br /> <input type="checkbox"
										name="delAvatar" value="1" />&nbsp; <span class="gensmall"><fmt:message
												key="user_form.jsp.removeAvatar" /></span></td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td class="row1"><span class="gen"><fmt:message
									key="user_form.jsp.loadAvatar" />:</span></td>
						<td class="row2"><html:file style="WIDTH: 200px;"
								property="image" /> <html:errors property="fileinvalid" /></td>
					</tr>

					<tr>
						<td class="catbottom" align="center" colspan="2" height="28">
							<input class="mainoption" type="submit"
							value="<fmt:message key="all.jsp.submit"/>" name="submit" />&nbsp;&nbsp;
							<input class="liteoption" type="reset"
							value="<fmt:message key="all.jsp.reset"/>" name="reset" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>

<%@ include file="/templates/default/common/footer.jsp"%>