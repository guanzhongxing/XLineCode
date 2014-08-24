<div class="postinfo">
	<div class="date">
		<a href="#${response.id}"> <img
			src="${resourcesHost}/images/icon_minipost_new.gif" alt="[Post New]" />
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
				href="${contextPath}/forums/topics/${currentTopic.id}/response?quote=true&responseId=${response.id}"
				rel="nofollow" class="icon_quote"><img
				src="${resourcesHost}/images/${locale}/icon_quote.gif" alt="" /></a>
		</c:if>
		<c:if
			test="${requestScope.enableModeratorEdition||((response.author.id==requestScope.userId)&&!userSession.guest)}">
			<a
				href="${contextPath}/forums/topics/${currentTopic.id}/response?edit=true&responseId=${response.id}"
				rel="nofollow" class="icon_edit"><img
				src="${resourcesHost}/images/${locale}/icon_edit.gif" alt="" /></a>
		</c:if>
		<c:if
			test="${requestScope.enableModeratorDeletion||((response.author.id==requestScope.userId)&&!userSession.guest)}">
			<input type="image" id="rsp${response.id}"
				src="${resourcesHost}/images/topic_delete.gif"
				onclick="return confirmDelete('${contextPath}/forums/topics/${currentTopic.id}/${response.id}',${response.author.id==requestScope.userId});"
				style="border: 0px;" />
		</c:if>
		<a class="nav" href="#top"><img
			src="${resourcesHost}/images/icon_up.gif" alt="[Up]" /></a>
	</div>
</div>