<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />



<table border="0" cellpadding="3" cellspacing="1" width="100%"
	class="forumline">
	<tr>
		<th class="thhead" align="center" height="28"><fmt:message
				key="moderation_list_posts.jsp.pending.msgs" />
		</td>
	</tr>

	<c:choose>
		<c:when test="${empty logs}">
			<tr>
				<td align="center"><span class="gensmall"><fmt:message
							key="moderation_list_posts.jsp.none.to.moderate" /></span></td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:if test="${not empty logs}">
				<html:form action="admin/moderation/update" method="post">
					<tr>
						<td class="nav" nowrap="nowrap" align="right"><%@ include
								file="/WEB-INF/templates/common/pagination/pagination.jsp"%>
						</td>
					</tr>
					
					<c:forEach var="log" items="${logs}">
						<c:set var="logType" value="${log.logType}" />
						<c:set var="modifiedInfo" value="${log.modifiedInfo}" />
						<c:choose>
							<c:when test="${logType=='INFO'}">
								<c:set var="topicSubject" value="${modifiedInfo.subject}" />
							</c:when>
							<c:otherwise>
								<c:set var="topicSubject" value="${modifiedInfo.info.subject}" />
							</c:otherwise>
						</c:choose>
						<c:set var="msgId" value="${modifiedInfo.id}" />
						<c:set var="logId" value="${log.id}" />

						<tr>
							<td class="row1">
								<table width="100%" align="center" cellpadding="0"
									cellspacing="1">
									<tr>
										<td colspan="2" class="catleft">&nbsp;<span class="gen"><b>${topicSubject}</b></span></td>
									</tr>

									<tr>
										<td>
											<table width="95%" align="center">

												<tr>
													<th class="thcornerl" width="150" height="26"><fmt:message
															key="moderation_list_posts.jsp.author" /></th>
													<th class="thcornerr"><fmt:message
															key="moderation_list_posts.jsp.msg.title" /></th>
												</tr>

												<tr>
													<td align="left" valign="top" class="row1"><span
														class="name"><a name=""></a><b>${modifiedInfo.author.name}</b></span></td>

													<td class="row1" height="28" valign="top">
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td width="100%"><span class="gensmall"><fmt:message
																			key="moderation_list_posts.jsp.subject" />:
																		${modifiedInfo.subject} </span></td>
															</tr>
															<tr>
																<td colspan="2"><hr /></td>
															</tr>
															<tr>
																<td colspan="2"><span class="postbody">${modifiedInfo.content}</span>
																</td>
															</tr>
														</table>
													</td>
												</tr>

												<tr>
													<td colspan="2" align="right"><span class="gensmall">
															<input type="radio" name="updateOpt_${msgId}"
															value="${requestScope.approved}"> <fmt:message
																key="moderation_list_posts.jsp.aprove" />&nbsp; <input
															type="radio" name="updateOpt_${msgId}"
															value="${requestScope.deferred}" checked> <fmt:message
																key="moderation_list_posts.jsp.defer" />&nbsp; <input
															type="radio" name="updateOpt_${msgId}"
															value="${requestScope.rejected}"> <fmt:message
																key="moderation_list_posts.jsp.reject" />&nbsp; <input
															type="hidden" name="msgIds" value="${msgId}"> <input
															type="hidden" name="logTypes" value="${logType}">
															<input type="hidden" name="logIds" value="${logId}">
													</span></td>
												</tr>

												<tr>
													<td colspan="2" height="1" class="spacerow"><img
														src="${resourcesHost}/images/spacer.gif"
														alt="" width="1" height="1" /></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>

					<tr>
						<td class="nav" nowrap="nowrap" align="right"><%@ include
								file="/WEB-INF/templates/common/pagination/pagination.jsp"%>
						</td>
					</tr>
					<tr>
						<td align="center" class="catleft"><input type="hidden"
							name="msgType" value="topic"><input class="mainoption"
							type="submit"
							value="<fmt:message key="moderation_list_posts.jsp.submit" />"></td>
					</tr>
				</html:form>
			</c:if>
		</c:otherwise>
	</c:choose>
</table>