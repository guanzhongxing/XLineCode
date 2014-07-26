<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<fmt:message var="title" key="all.jsp.privateMsg" />
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<c:set var="boxType" value="${requestScope.boxType}" />
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
												<c:if test="${boxType!='inbox'}">
													<a href="displayPrivateMsgInbox.do">
												</c:if>
												<img src="${resourcesHost}/images/msg_inbox.gif" border="0" alt="[Inbox]" />
												<c:if test="${boxType!='inbox'}">
													</a>
												</c:if>
											</td>
											<td>
												<c:if test="${boxType!='inbox'}">
													<a href="displayPrivateMsgInbox.do">
												</c:if>
												<span class="cattitle"><fmt:message key="pm_list.jsp.inbox"/> &nbsp;</span>
												<c:if test="${boxType!='inbox'}">
													</a>
												</c:if>
											</td>
											<td>
												<c:if test="${boxType!='sentbox'}">
													<a href="displayPrivateMsgSentbox.do">
												</c:if>
												<img src="${resourcesHost}/images/msg_sentbox.gif" border="0" alt="[Sentbox]" />
												<c:if test="${boxType!='sentbox'}">
													</a>
												</c:if>
											</td>
											<td>
												<c:if test="${boxType!='sentbox'}">
													<a href="displayPrivateMsgSentbox.do">
												</c:if>
												<span class="cattitle"><fmt:message key="pm_list.jsp.sentBox"/>&nbsp;</span>
												<c:if test="${boxType!='sentbox'}">
													</a>
												</c:if>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
				<br clear="all" />
				<form action="" method="post" name="privmsg_list" id="privmsg_list">
					<table cellspacing="2" cellpadding="2" width="100%" align="center" border="0">
						<tbody>
							<tr>
								<td valign="middle" align="left">
									<a href="initCreatePrivateMsg.do" class="icon_new_topic"><img src="${resourcesHost}/images/${locale}/post.gif" alt="New Topic" /></a>
								</td>
								<td align="left" width="100%">
									<span class="nav">
									 <a class="nav" href="{contextPath}/forums"><fmt:message key="all.jsp.forums"/></a>
									 </span>
								</td>
								<td class="nav" nowrap="nowrap" align="right">
						  			<%@ include file="/WEB-INF/templates/common/pagination/pagination.jsp" %>
								</td>
							</tr>
						</tbody>
					</table>
					<table class="forumline" cellspacing="1" cellpadding="3" width="100%" border="0">
						<tbody>
							<tr>
								<th class="thcornerl" nowrap="nowrap" width="5%" height="25">
									&nbsp;&nbsp;
								</th>
								<th class="thtop" nowrap="nowrap" width="55%">
									&nbsp;<fmt:message key="all.jsp.msgSubject"/>&nbsp;
								</th>
								<th class="thtop" nowrap="nowrap" width="20%">
									<c:choose>
										<c:when test="${boxType=='inbox'}">
											&nbsp;<fmt:message key="pm.jsp.from"/>&nbsp;
										</c:when>
										<c:otherwise>
											&nbsp;<fmt:message key="pm_list.jsp.to"/>&nbsp;
										</c:otherwise>
									</c:choose>
								</th>
								<th class="thtop" nowrap="nowrap" width="15%">
									&nbsp;<fmt:message key="pm.jsp.date"/>&nbsp;
								</th>
								<th class="thcornerr" nowrap="nowrap" width="5%">
									&nbsp;&nbsp;
								</th>
							</tr>
							<c:forEach var="pm" items="${requestScope.privateMsgs}">
								<tr>
									<td class="row1" valign="middle" align="center" width="5%">
										<c:choose>
											<c:when test="${boxType=='inbox'}">
												<c:choose>
													<c:when test="${pm.readToReceiver}">
														<img src="${resourcesHost}/images/folder.gif" alt="<fmt:message key="folder_descriptions.htm.read"/>" />
													</c:when>
													<c:otherwise>
														<img src="${resourcesHost}/images/folder_unRead.gif" alt="<fmt:message key="folder_descriptions.htm.unRead"/>" />
													</c:otherwise>
												</c:choose>
												
											</c:when>
											<c:otherwise>
												<img src="${resourcesHost}/images/folder.gif" alt="Unread" />
											</c:otherwise>
										</c:choose>
									</td>
									<td class="row1" valign="middle" width="55%">
										<span class="topictitle">&nbsp;<a class="topictitle" href="displayPrivateMsg.do?pmId=${pm.id}">${pm.subject}</a>
											<c:if test="${pm.newToReceiver}">
												<img src="${resourcesHost}/images/new.gif" alt="New" />
											</c:if>
										</span>
									</td>
									<td class="row1" valign="middle" align="center" width="20%">
										<c:choose>
											<c:when test="${boxType=='inbox'}">
												<span class="name">&nbsp;<a class="name" href="displayProfile.do?userId=${pm.author.id}">${pm.author.name}</a></span>
											</c:when>
											<c:otherwise>								
												<span class="name">&nbsp;<a class="name" href="displayProfile.do?userId=${pm.receiver.id}">${pm.receiver.name}</a></span>
											</c:otherwise>
										</c:choose>
									</td>
									<td class="row1" valign="middle" align="center" width="15%">
										<span class="postdetails">
											<fmt:formatDate value="${pm.createdTime}" dateStyle="medium"/>
											<fmt:formatDate value="${pm.createdTime}" pattern="hh:mm"/>
										</span>
									</td>
									<td class="row1" valign="middle" align="center" width="5%"><span class="postdetails"><input type="checkbox" value="${pm.id}" name="id" /></span></td>
								</tr>
							</c:forEach>
							<tr>
								<td class="catbottom" align="right" colspan="5" height="28">
									&nbsp; <input class="liteoption" type="submit" value="<fmt:message key="pm_list.jsp.deleteButton"/>" name="delete" />
								</td></tr>
						</tbody>
					</table>
					<table cellspacing="2" cellpadding="2" width="100%" align="center" border="0">
						<tbody>
							<tr>
								<td valign="middle" align="left">
									<a href="initCreatePrivateMsg.do" class="icon_new_topic nav"><img src="${resourcesHost}/images/${locale}/post.gif" alt="" /></a>
								</td>
								<td valign="middle" align="left" width="100%"><span class="nav"></span></td>
								<td valign="top" nowrap="nowrap" align="right">
								</td>
								<td class="nav" nowrap="nowrap" align="right">
						  			<%@ include file="/WEB-INF/templates/common/pagination/pagination.jsp" %>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<div align="center"></div>
			</td>
		</tr>
	</tbody>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>