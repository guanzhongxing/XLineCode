<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>
<script type="text/javascript">
	function confirmDelete(contentId,contentType,isAuthor) {
		if (confirm("<fmt:message
				key="responses_show.jsp.mdr.delete.tip" />")) {
			if(!isAuthor){
				var reason = prompt("<fmt:message
						key="responses_show.jsp.mdr.delete.reason" />:");

				if (reason == null || reason == "") {
					alert("<fmt:message
							key="responses_show.jsp.mdr.reason.tip" />");
					return false;
				}
			}
			
			var link;
			if(contentType=="topic")
				link = document.getElementById("topic" + contentId);
			else link = document.getElementById("rsp" + contentId);
			link.href += "&reason=" + encodeURIComponent(reason)
						+ "&contentType="+contentType;

			return true;
		}

		return false;
	}
	
	function confirmLockUnlock(topicId,isAuthor) {
		<c:choose>
			<c:when test="${currentTopic.locked}">
			var promptMsg="<fmt:message
					key="responses_show.jsp.mdr.unlock.tip" />";
			</c:when>
			<c:otherwise>
			var promptMsg="<fmt:message
					key="responses_show.jsp.mdr.lock.tip" />";
			</c:otherwise>
		</c:choose>
		if (confirm(promptMsg)) {
			if(!isAuthor){
				var reason = prompt("<fmt:message
						key="responses_show.jsp.mdr.lock.reason" />:");

				if (reason == null || reason == "") {
					alert("<fmt:message
							key="responses_show.jsp.mdr.reason.tip" />");
					return false;
				}
			}
			
			var link;
			link = document.getElementById("lockTopic" + topicId);
			link.href += "&reason=" + encodeURIComponent(reason);

			return true;
		}

		return false;
	}
</script>

<c:set var="currentTopic" value="${requestScope.currentTopic}" />
<table cellspacing="0" cellpadding="10" width="100%" align="center"
	border="0">
	<tr>
		<td class="bodyline">
			<table cellspacing="2" cellpadding="2" width="100%" border="0">
				<tr>
					<td valign="middle" align="left" colspan="2"><span
						class="maintitle">${currentTopic.subject}</span></td>
				</tr>
			</table> <%@ include file="responses_show_navigator.jsp"%>
			<table cellspacing="2" cellpadding="2" width="100%" border="0">
				<tr>
					<td colspan="3"><logic:messagesPresent message="true">
							<html:messages id="msg" message="true">
								<bean:write name="msg" />
								<br />
							</html:messages>
						</logic:messagesPresent></td>
				</tr>
			</table>

			<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
				border="0">
				<tr>
					<th class="thleft" nowrap="nowrap" width="150" height="26"><fmt:message
							key="all.jsp.author" /></th>
					<th class="thright" nowrap="nowrap" width="100%"><fmt:message
							key="responses_show.jsp.response.title" /></th>
				</tr>

				<!-- Topic -->
				<tr>
					<td colspan="2">
						<div class="postinfo">
							<div class="date">
								<img
									src="${resourcesHost}/images/icon_minipost_new.gif"
									alt="[Post New]" />
								<fmt:formatDate value="${currentTopic.createdTime}"
									dateStyle="medium" />
								<fmt:formatDate value="${currentTopic.createdTime}"
									pattern="hh:mm" />
							</div>
							<div class="subject">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><fmt:message
										key="responses_show.jsp.response.subject" />:</b><a
									name="${currentTopic.subject}">${currentTopic.subject}</a>
							</div>
							<div class="action">
								<c:if test="${requestScope.enableNewRsp}">
									<a
										href="${contextPath}/forums/topics/${currentTopic.id}/response?quote=true"
										rel="nofollow" class="icon_quote"><img
										src="${resourcesHost}/images/${locale}/icon_quote.gif"
										alt="" /></a>
								</c:if>
								<c:if
									test="${requestScope.enableModeratorEdition||currentTopic.author.id==requestScope.userId}">
									<a
										href="${contextPath}/initCreateUserTopic.do?forumzoneId=${forumzoneId}&forumId=${forum.id}&edit=true&topicId=${currentTopic.id}"
										rel="nofollow" class="icon_edit"><img
										src="${resourcesHost}/images/${locale}/icon_edit.gif"
										alt="" /></a>
								</c:if>
								<a class="nav" href="#top"><img
									src="${resourcesHost}/images/icon_up.gif"
									alt="[Up]" /></a>
							</div>
						</div>
					</td>
				</tr>

				<tr>
					<!-- Username -->
					<c:set var="rowspan" value="3" />

					<c:set var="author" value="${currentTopic.author}" />
					<td class="${rowColor}" valign="top" align="left"
						rowspan="${rowspan}"><%@ include
							file="response_show_user_inc.jsp"%></td>

					<!-- Message -->
					<td class="${rowColor}" valign="top"><span class="postbody">
							${currentTopic.content} <!-- Attachments --> <c:set
								var="attachments" value="${currentTopic.attachments}" /> <c:if
								test="${not empty currentTopic.attachments && currentTopic.author.canDownloadAttms}">
								<%@ include
									file="/WEB-INF/templates/default/user/common/post_show_attachments_inc.jsp"%>
							</c:if><!-- UJian Button BEGIN -->
						<div class="ujian-hook"></div> <script type="text/javascript">var ujian_config = {num:6,picSize:84,textHeight:45};</script>
						<script type="text/javascript"
							src="http://v1.ujian.cc/code/ujian.js?uid=1854086"></script> <a
						href="http://www.ujian.cc" style="border: 0;"><img
							src="http://img.ujian.cc/pixel.png" alt="友荐云推荐"
							style="border: 0; padding: 0; margin: 0;" /></a> <!-- UJian Button END -->
					</span></td>
				</tr>

				<tr>
					<td colspan="2" class="${rowColor}" width="100%" height="28">
						<hr /> <span class="gensmall">${currentTopic.author.signature}</span>
					</td>
				</tr>

				<tr>
					<td class="${rowColor}" valign="bottom" nowrap="nowrap" height="28"
						width="100%"><%@ include file="show_user_profile_inc.jsp"%>
					</td>
				</tr>

				<tr>
					<td class="spacerow" colspan="2" height="1"><img
						src="${resourcesHost}/images/spacer.gif" alt=""
						width="1" height="1" /></td>
				</tr>
				<!-- Topic end -->

				<!-- RESPONSES LISTING -->
				<c:if test="${not empty requestScope.responses}">
					<c:forEach var="response" items="${requestScope.responses}"
						varStatus="rowCounter">
						<c:choose>
							<c:when test="${rowCounter.count%2==0}">
								<c:set var="rowColor" value="row1" />
							</c:when>
							<c:otherwise>
								<c:set var="rowColor" value="row2" />
							</c:otherwise>
						</c:choose>

						<tr>
							<td colspan="2"><%@ include
									file="response_show_action_buttons_inc.jsp"%>
							</td>
						</tr>

						<tr>
							<!-- Username -->
							<c:set var="rowspan" value="3" />

							<c:set var="author" value="${response.author}" />
							<td class="${rowColor}" valign="top" align="left"
								rowspan="${rowspan}"><%@ include
									file="response_show_user_inc.jsp"%></td>

							<!-- Message -->
							<td class="${rowColor}" valign="top"><span class="postbody">${response.content}
							</span> <!-- Attachments --> <c:set var="attachments"
									value="${response.attachments}" /> <c:if
									test="${not empty response.attachments && response.author.canDownloadAttms}">
									<%@ include
										file="/WEB-INF/templates/default/user/common/post_show_attachments_inc.jsp"%>
								</c:if></td>
						</tr>

						<tr>
							<td colspan="2" class="${rowColor}" width="100%" height="28">
								<hr /> <span class="gensmall">${response.author.signature}</span>
							</td>
						</tr>

						<tr>
							<td class="${rowColor}" valign="bottom" nowrap="nowrap"
								height="28" width="100%"><%@ include
									file="show_user_profile_inc.jsp"%></td>
						</tr>

						<tr>
							<td class="spacerow" colspan="2" height="1"><img
								src="${resourcesHost}/images/spacer.gif" alt=""
								width="1" height="1" /></td>
						</tr>
					</c:forEach>
				</c:if>
				<!-- END OF POST LISTING -->

				<tr align="center">
					<td class="catbottom" colspan="2" height="28">
						<table cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td align="center"><span class="gensmall">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
			</table> <%@ include file="responses_show_navigator.jsp"%>

			<table cellspacing="0" cellpadding="5" width="100%" border="0">
				<tr>
					<td align="left" colspan="3"><c:if
							test="${requestScope.enableModeratorDeletion||currentTopic.author.id==requestScope.userId}">
							<a
								href="${contextPath}/deleteContent.do?contentId=${currentTopic.id}&contentType=topic&forumId=${forum.id}&forumzoneId=${forumzoneId}"
								id="topic${currentTopic.id}" rel="nofollow"
								onclick="return confirmDelete(${currentTopic.id},'topic',${currentTopic.author.id==requestScope.userId});"><img
								class="icon_topic_delete"
								src="${resourcesHost}/images/topic_delete.gif"
								alt="[Delete]"></a>
						</c:if> <c:if test="${enableTopicMovement}">
							<a
								href="${contextPath}/do/admin/topic/move/init?topicId=${currentTopic.id}"
								rel="nofollow"><img class="icon_topic_move"
								src="${resourcesHost}/images/topic_move.gif"
								title="Move" alt=""></a>
						</c:if> <c:if test="${enableTopicLock}">
							<c:choose>
								<c:when test="${currentTopic.locked}">
									<a id="lockTopic${currentTopic.id}"
										onclick="return confirmLockUnlock(${currentTopic.id},${currentTopic.author.id==requestScope.userId});"
										href="${contextPath}/do/user/topic/unlock?forumId=${forum.id}&topicId=${currentTopic.id}"
										rel="nofollow"><img class="icon_topic_lock"
										src="${resourcesHost}/images/topic_unlock.gif"
										title="Unlock" alt=""> </a>
								</c:when>
								<c:otherwise>
									<a id="lockTopic${currentTopic.id}" rel="nofollow"
										onclick="return confirmLockUnlock(${currentTopic.id},${currentTopic.author.id==requestScope.userId});"
										href="${contextPath}/do/user/topic/lock?forumId=${forum.id}&topicId=${currentTopic.id}"><img
										class="icon_topic_lock"
										src="${resourcesHost}/images/topic_lock.gif"
										title="Lock" alt=""></a>
								</c:otherwise>
							</c:choose>
						</c:if></td>
				</tr>
				<tr>
					<td align="left" class="gensmall"></td>
					<td align="right"><%@ include
							file="/WEB-INF/templates/default/user/common/forums_navigator.jsp"%>
					</td>
				</tr>
			</table>

		</td>
	</tr>
</table>

<a name="quick"></a>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>