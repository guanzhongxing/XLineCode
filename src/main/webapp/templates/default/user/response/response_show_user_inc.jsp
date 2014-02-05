<span class="genmed"><b>${author.name}</b></span>
<br />
<span class="gensmall"> <c:set var="attmInfo"
		value="${author.avatar.attmInfo}" /> <c:choose>
		<c:when test="${attmInfo.attachmentType=='LOCAL'}">
			<c:set var="avatarUrl"
				value="${contextPath}/local/image.do?id=${author.avatar.id}" />
		</c:when>
		<c:otherwise>
			<c:set var="avatarUrl" value="${attmInfo.downloadUrl}" />
		</c:otherwise>
	</c:choose> <img src="${avatarUrl}" border="0" alt="[Avatar]"
	width="${avatarWidth}" height="${avatarHeight}" /><br /> <br /> <fmt:message
		key="responses_show.jsp.response.userRegistrationDate" />: <fmt:formatDate
		value="${author.regTime}" dateStyle="medium" /> <fmt:formatDate
		value="${author.regTime}" pattern="hh:mm" /> <br /> <fmt:message
		key="responses_show.jsp.response.userTotalResponses" />:${author.statistician.responseNum}
	<br /> <c:set var="isOffline" value="true" /> <c:forEach
		var="userSession" items="${requestScope.loginUserSessions}">
		<c:if test="${author.id==userSession.value.userId}">
			<span class="online"><bean:message key="all.jsp.online" /></span>
			<c:set var="isOffline" value="false" />
		</c:if>
	</c:forEach> <c:if test="${isOffline==true}">
		<span class="offline"><bean:message key="all.jsp.offline" /></span>
	</c:if>
</span>
<br />