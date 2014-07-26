
<c:choose>
	<c:when test="${topic.infoType=='STICKY'}">
		<img src="${resourcesHost}/images/folder_sticky.gif"
			alt="<fmt:message key="folder_descriptions.htm.sticky" />" />
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${not topic.readToUser && topic.locked}">
				<img
					src="${resourcesHost}/images/folder_lock_new.gif"
					alt="<fmt:message key="folder_descriptions.htm.unReadBlocked" />" />
			</c:when>
			<c:when test="${not topic.readToUser && topic.hot}">
				<img
					src="${resourcesHost}/images/folder_unRead_hot.gif"
					alt="<fmt:message key="folder_descriptions.htm.unReadHot" />" />
			</c:when>
			<c:when test="${not topic.readToUser}">
				<img src="${resourcesHost}/images/folder_unRead.gif"
					alt="<fmt:message key="folder_descriptions.htm.unRead" />" />
			</c:when>
			<c:when test="${topic.readToUser && topic.locked}">
				<img src="${resourcesHost}/images/folder_lock.gif"
					alt="<fmt:message key="folder_descriptions.htm.readBlocked" />" />
			</c:when>
			<c:when test="${topic.readToUser && topic.hot}">
				<img src="${resourcesHost}/images/folder_hot.gif"
					alt="<fmt:message key="folder_descriptions.htm.readHot" />" />
			</c:when>
			<c:otherwise>
				<img src="${resourcesHost}/images/folder.gif"
					alt="<fmt:message key="folder_descriptions.htm.read" />" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
