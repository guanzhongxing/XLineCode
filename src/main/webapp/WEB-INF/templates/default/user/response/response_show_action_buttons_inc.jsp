<div class="postinfo">
	<div class="date">
		<a href="#${response.id}"> <img
			src="${contextPath}/templates/default/images/icon_minipost_new.gif"
			alt="[Post New]" />
		</a>
		<fmt:formatDate value="${response.createdTime}" dateStyle="medium" />
		<fmt:formatDate value="${response.createdTime}" pattern="hh:mm" />
	</div>
	<div class="subject">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><fmt:message
				key="responses_show.jsp.response.subject" />:</b><a
			name="${response.id}">${response.subject}</a>
	</div>
	<div class="action">
		<c:if test="${requestScope.enableNewRsp}">
			<a
				href="initRespondTopic.do?quote=true&responseId=${response.id}&topicId=${currentTopic.id}&forumId=${currentTopic.forum.id}&forumzoneId=${forumzoneId}"
				rel="nofollow" class="icon_quote"><img
				src="${contextPath}/templates/default/images/${locale}/icon_quote.gif"
				alt="" /></a>
		</c:if>
		<c:if
			test="${requestScope.enableModeratorEdition||((response.author.id==requestScope.userId)&&!userSession.guest)}">
			<a
				href="initRespondTopic.do?edit=true&responseId=${response.id}&topicId=${currentTopic.id}&forumId=${forum.id}&forumzoneId=${forumzoneId}"
				rel="nofollow" class="icon_edit"><img
				src="${contextPath}/templates/default/images/${locale}/icon_edit.gif"
				alt="" /></a>
		</c:if>
		<c:if
			test="${requestScope.enableModeratorDeletion||((response.author.id==requestScope.userId)&&!userSession.guest)}">
			<a
				href="deleteContent.do?contentId=${response.id}&contentType=rsp&topicId=${currentTopic.id}&forumId=${forum.id}"
				id="rsp${response.id}" rel="nofollow"
				onclick="return confirmDelete(${response.id},'rsp',${response.author.id==requestScope.userId});"><img
				src="${contextPath}/templates/default/images/icon_delete.gif"
				alt="[Delete]"></a>
		</c:if>
		<a class="nav" href="#top"><img
			src="${contextPath}/templates/default/images/icon_up.gif" alt="[Up]" /></a>
	</div>
</div>