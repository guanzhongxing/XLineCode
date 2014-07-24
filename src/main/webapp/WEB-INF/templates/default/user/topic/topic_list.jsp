
<!-- TOPICS LISTING -->
<c:choose>
	<c:when test="${not empty pageScope.topics}">
		<c:forEach var="topic" items="${pageScope.topics}">
			<c:set var="forum" value="${topic.forum}" />
			<c:choose>
				<c:when
					test="${topic.infoType=='CATEGORY_ANNOUNCEMENT'||topic.infoType=='DEPARTMENT_ANNOUNCEMENT'||topic.infoType=='SYSTEM_ANNOUNCEMENT'}">
					<c:set var="class1" value='class="row1Announce"' />
					<c:set var="class2" value='class="row2Announce"' />
					<c:set var="class3" value='class="row3Announce"' />
				</c:when>
				<c:when test="${topic.infoType=='STICKY'}">
					<c:set var="class1" value='class="row1sticky"' />
					<c:set var="class2" value='class="row2sticky"' />
					<c:set var="class3" value='class="row3sticky"' />
				</c:when>
				<c:otherwise>
					<c:set var="class1" value='class="row1"' />
					<c:set var="class2" value='class="row2"' />
					<c:set var="class3" value='class="row3"' />
				</c:otherwise>
			</c:choose>

			<tr class="bg_small_yellow">
				<td ${class1} valign="middle" align="center" width="20"><c:choose>
						<c:when
							test="${topic.infoType=='CATEGORY_ANNOUNCEMENT'||topic.infoType=='DEPARTMENT_ANNOUNCEMENT'||topic.infoType=='SYSTEM_ANNOUNCEMENT'}">
							<img
								src="${contextPath}/templates/default/images/folder_announce.gif"
								alt="<fmt:message key="folder_descriptions.htm.announce" />" />
						</c:when>
						<c:otherwise>
							<%@ include file="topic_status_displayer.jsp"%>
						</c:otherwise>
					</c:choose></td>
				<td ${class1} width="100%"><span class="topictitle"> <a
						href="displayResponses.do?forumId=${forum.id}&topicId=${topic.id}">${topic.subject}</a>
				</span> <c:if test="${topic.newToUser}">
						<img src="${contextPath}/templates/default/images/new.gif"
							alt="New" />
					</c:if></td>

				<c:set var="statistician" value="${topic.statistician}" />
				<td ${class2} valign="middle" align="center"><span
					class="postdetails">${statistician.responseNum}</span></td>
				<td ${class3} valign="middle" align="center"><span class="name"><a
						href="displayProfile.do?userId=${topic.author.id}">${topic.author.name}</a></span>
				</td>

				<td ${class2} valign="middle" align="center"><span
					class="postdetails">${statistician.clickThroughRate}</span></td>
				<td ${class3} valign="middle" nowrap="nowrap" align="center"><span
					class="postdetails"> <c:choose>
							<c:when test="${not empty statistician.latestResponse}">
								<c:set var="latestResponse"
									value="${statistician.latestResponse}" />
								<fmt:formatDate value="${latestResponse.createdTime}"
									dateStyle="medium" />
								<fmt:formatDate value="${latestResponse.createdTime}"
									pattern="hh:mm" />
								<br />
								<a href="displayProfile.do?userId=${latestResponse.author.id}">${latestResponse.author.name}</a>
								<a
									href="displayResponses.do?forumId=${forum.id}&topicId=${latestResponse.topic.id}#${latestResponse.id}"><img
									src="${contextPath}/templates/default/images/icon_latest_reply.gif"
									border="0" alt="[Latest Reply]" /></a>
							</c:when>
							<c:otherwise>
								<fmt:message key="display_topics.jsp.noResponseYet" />
							</c:otherwise>
						</c:choose>
				</span></td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:if test="${showNoTopicTip}">
			<tr class="bg_small_yellow">
				<td ${class1} colspan="6"><span class="topictitle"> <fmt:message
							key="display_topics.jsp.noTopicYet" />
				</span></td>
			</tr>
		</c:if>
	</c:otherwise>
</c:choose>
<!-- END OF TOPICS LISTING -->