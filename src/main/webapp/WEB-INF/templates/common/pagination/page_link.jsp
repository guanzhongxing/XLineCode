<c:set var="start" value="${offset*(index-1)}" />
<c:choose>
	<c:when test="${index!=currentPage}">
		<a href="${url}&start=${start}"">${index}</a>
	</c:when>
	<c:otherwise>
		<span class="current">${index}</span>
		</c:otherwise>
</c:choose>