<%@ include file="/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="all.jsp.privateMsg" />
<%@ include file="/templates/default/user/common/header.jsp"%>

<table cellspacing="0" cellpadding="10" width="100%" align="center" border="0">
	<tbody>
		<tr>
			<td class="bodyline">
				<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                  <tbody>
                    <tr>
                      <td valign="top" align="center" width="100%">
                        <table cellspacing="2" cellpadding="2" border="0">
                          <tbody>
                            <tr valign="middle">
                            	<td>
									<a href="displayPrivateMsgInbox.do">
										<img src="${contextPath}/templates/default/images/msg_inbox.gif" border="0" alt="[Inbox]" />
									</a>
								</td>
								<td>
									<a href="displayPrivateMsgInbox.do">
										<span class="cattitle"><fmt:message key="pm_list.jsp.inbox"/> &nbsp;</span>
									</a>
								</td>
								<td>
									<a href="displayPrivateMsgSentbox.do">
										<img src="${contextPath}/templates/default/images/msg_sentbox.gif" border="0" alt="[Sentbox]" />
									</a>
								</td>
								<td>
									<a href="displayPrivateMsgSentbox.do">
										<span class="cattitle"><fmt:message key="pm_list.jsp.sentBox"/>&nbsp;</span>
									</a>
								</td>
                            </tr>
                          </tbody>
                        </table>
                      </td>
                    </tr>
                  </tbody>
                </table>
				<br clear="all" />
				<form action="#" method="post">
				<input type="hidden" name="module" value="${moduleName}" />
				<input type="hidden" name="action" value="delete" />
					<table cellspacing="2" cellpadding="2" width="100%" border="0">
						<tbody>
							<tr>
								<td valign="middle"><a href="initCreatePrivateMsg.do?pmId=${thePm.id}" class="icon_reply"><img src="${contextPath}/templates/default/images/${locale}/reply.gif" alt="" /></a>
								</td>
								<td width="100%">
									<span class="nav">&nbsp;<a class="nav" href="displayForums.do"><fmt:message key="all.jsp.forums"/></a></span>
								</td>
							</tr>
						</tbody>
					</table>
					<c:set var="thePm" value="${requestScope.thePm}" />
					<table class="forumline" cellspacing="1" cellpadding="4" width="100%" border="0">
						<tbody>
							<tr>
								<th class="thhead" nowrap="nowrap" colspan="3"><fmt:message key="all.jsp.privateMsg"/></th>
							</tr>
							<tr>
								<td class="row2" width="10%"><span class="genmed"><fmt:message key="pm.jsp.from"/>:</span></td>
								<td class="row2" colspan="2"><span class="genmed"><a class="nav" href="#">${thePm.author.name}</a></span></td>
							</tr>
							<tr>
								<td class="row2" width="10%"><span class="genmed"><fmt:message key="pm.jsp.to"/>:</span></td>
								<td class="row2" colspan="2"><span class="genmed"><a class="nav" href="#">${thePm.receiver.name}</a></span></td>
							</tr>
							<tr>
								<td class="row2" width="10%"><span class="genmed"><fmt:message key="pm.jsp.date"/>:</span></td>
								<td class="row2" colspan="2"><span class="genmed">${thePm.createdTime}</span></td>
							</tr>
							<tr>
								<td class="row2" width="10%"><span class="genmed"><fmt:message key="all.jsp.msgSubject"/>:</span></td>
								<td class="row2"><span class="genmed">${thePm.subject}</span></td>
								<td class="row2" nowrap="nowrap" align="right">
									<a href="initCreatePrivateMsg.do?quote=true&pmId=${thePm.id}" class="icon_quote"><img src="${contextPath}/templates/default/images/${locale}/icon_quote.gif" alt="" /></a>
								</td>
							</tr>
							<tr>
								<td class="row1" valign="top" colspan="3">
									<span class="postbody">${thePm.content}
										<hr/>
										<c:if test="${thePm.attachSig}">
											<span class="gensmall">${thePm.author.signature}</span>
										</c:if>
									</span>
								</td>
							</tr>
							
							<tr>
								<td class="catbottom" align="right" colspan="3" height="28">
									<input type="hidden" value="${thePm.id}" name="id" />
									&nbsp; <input class="liteoption" type="submit" value="removeMessage}" name="delete" />
								</td>
							</tr>
						</tbody>
					</table>
					<table cellspacing="2" cellpadding="2" width="100%" align="center" border="0">
						<tbody>
							<tr>
								<td><a href="initCreatePrivateMsg.do?pmId=${thePm.id}" class="icon_reply"><img src="${contextPath}/templates/default/images/${locale}/reply.gif" alt="" /></a></td>
							</tr>
						</tbody>
					</table>
				</form>
				<div align="center"></div>
		  </td>
		</tr>
	</tbody>
</table>

<%@ include file="/templates/default/common/footer.jsp"%>
