<%@ include file="/templates/default/common/common_header.jsp"%>
<%@ include file="/templates/default/user/common/header.jsp"%>
<script type="text/javascript">
	function askModerationReason() {
		var message = prompt("<fmt:message key="topic_move.jsp.move.promt" />");

		if (message == null || message == "") {
			alert("<fmt:message key="topic_move.jsp.move.reason" />");
			return false;
		} else {
			document.getElementById("reason").value=message;
			return true;
		}
	}
</script>

<html:form action="/admin/topic/move" styleId="topicMovement"
	enctype="multipart/form-data" onsubmit="return askModerationReason();">
	<input type="hidden" name="topicId" value="${param.topicId}" />
	<input id="reason" type="hidden" name="reason" value="" />

	<table width="100%" cellspacing="0" cellpadding="10" border="0"
		align="center">
		<tr>
			<td class="bodyline"><br />
				<table width="100%" cellspacing="2" cellpadding="2" border="0"
					align="center">
					<tr>
						<td align="left" class="nav"><a class="nav"
							href="${contextPath}/displayForums.do"><fmt:message key="all.jsp.forums" /></a></td>
					</tr>
				</table>

				<table class="forumline" width="100%" cellspacing="1"
					cellpadding="4" border="0">
					<tbody>
						<tr>
							<th class="thhead" height="25"><b><fmt:message
										key="topic_move.jsp.topic.move" /></b></th>
						</tr>

						<tr>
							<td class="row1">
								<table width="100%" cellspacing="0" cellpadding="1" border="0">
									<tbody>
										<tr>
											<td>&nbsp;</td>
										</tr>

										<tr>
											<td align="center"><span class="gen"><fmt:message
														key="topic_move.jsp.move.to" /> <select id="select"
													name="newForumId">
														<c:forEach var="forumzone"
															items="${requestScope.forumzones}">
															<optgroup label="${forumzone.name}">
																<c:set var="forums" value="${forumzone.forums}" />
																<c:forEach var="forum" items="${forums}">
																	<option value="${forum.id}">${forum.name}</option>
																</c:forEach>
															</optgroup>
														</c:forEach>
												</select> </span></td>
										</tr>

										<tr>
											<td align="center"><br> <input type="submit"
												name="submit" class="liteoption"
												value="<fmt:message key="topic_move.jsp.move" />">
												&nbsp; <input type="button"
												value="<fmt:message key="topic_move.jsp.back" />"
												onclick="history.go(-1)" class="liteoption"></td>
										</tr>

										<tr>
											<td>&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table></td>
		</tr>
	</table>
</html:form>

<%@ include file="/templates/default/common/footer.jsp"%>