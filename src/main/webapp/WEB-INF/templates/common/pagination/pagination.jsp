<!-- ------------------------------------------------------------------------------- -->
<!-- -- Pagination base code inspired from PHPBB's generate_pagination() function -- -->
<!-- ------------------------------------------------------------------------------- -->

<!-- 
	The variables this page needs are size,offset,and start
	size:The size of the collection that needs to be paging
	offset:which is set in application scope,a list's entry
		number per page
	start:which will be send back to server to get the next
		cycle of paged entries in a collection
 -->
 <script type="text/javascript" src="${resourcesHost}/javascripts/pagination.js"></script>
 <c:set var="offset" value="${requestScope.pageCxt.offset}" />
 
<c:if test="${requestScope.pageCxt.size>offset}">
	<div class="pagination">
		<c:set var="totalPages" value="${requestScope.pageCxt.totalPages}" />
		<c:set var="currentPage" value="${requestScope.pageCxt.currentPage}" />
		<c:set var="url" value="${requestScope.pageCxt.url}&offset=${offset}" />
		
		<!-- Previous page -->
		<c:if test="${currentPage>1}">
			<c:set var="start" value="${(currentPage - 2)*offset}" />
			<a href="${url}&start=${start}">&#9668;</a>
		</c:if>
		<!-------- end ------>

		<c:choose>
			<c:when test="${totalPages>10}">
			
				<!-- Always write the first 3 links -->
				<c:forEach var="index" begin="1" end="3">
					<%@ include file="/WEB-INF/templates/common/pagination/page_link.jsp" %>
				</c:forEach>
				<!--------------- end --------------->
				
				<!-- Intermediate links -->
				<c:choose>
					<c:when test="${currentPage > 1 && currentPage < totalPages}">
						<c:if test="${(currentPage > 5) }"><span class="gensmall">...</span></c:if>
						
						<c:choose>
							<c:when test="${currentPage > 4}">
								<c:set var="min" value="${currentPage - 1}" />
							</c:when>
							<c:otherwise>
								<c:set var="min" value="4" />
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${currentPage < totalPages - 4}">
								<c:set var="max" value="${currentPage+2}" />
							</c:when>
							<c:otherwise>
								<c:set var="max" value="${totalPages-2}" />
							</c:otherwise>
						</c:choose>
		
						<c:if test="${max >= min + 1}">
							<c:forEach var="index" begin="${min}" end="${max-1}">
								<%@ include file="/WEB-INF/templates/common/pagination/page_link.jsp" %>
							</c:forEach>
						</c:if>
		
						<c:if test="${currentPage<totalPages-4}"><span class="gensmall">...</span></c:if>
					</c:when>
					<c:otherwise>
						<span class="gensmall">...</span>
					</c:otherwise>
				</c:choose>
				<!-- end -->
	
				<!-- Write the last 3 links -->
				<c:forEach var="index" begin="${totalPages-2}" end="${totalPages}">
					<%@ include file="/WEB-INF/templates/common/pagination/page_link.jsp" %>
				</c:forEach>
				<!------------ end ----------->
				
			</c:when>
			<c:otherwise>
				<c:forEach var="index" begin="1" end="${totalPages}">
					<%@ include file="/WEB-INF/templates/common/pagination/page_link.jsp" %>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<!-- ------------- -->
		<!-- Next page -->
		<!-- ------------- -->
		<c:if test="${currentPage<totalPages}">
			<c:set var="start" value="${currentPage*offset}" />
			<a href="${url}&start=${start}"">&#9658;</a>
		</c:if>

		<a href="#goto" onClick="return overlay(this, 'goToBox', 'rightbottom');" rel="nofollow"><fmt:message key="pagination.jsp.goTo"/></a>
		<div id="goToBox">
			<div class="title"><fmt:message key="pagination.jsp.goToPage"/>...</div>
			<div class="form">
				<input type="text" style="width: 50px;" id="pageToGo">
				<input type="button" value="<fmt:message key="pagination.jsp.goTo"/>" onClick="goToAnotherPage(${totalPages}, '${url}', ${offset});">
				<input type="button" value="<fmt:message key="pagination.jsp.cancel"/>" onClick="document.getElementById('goToBox').style.display = 'none';">
			</div>
		</div>
	</div>
</c:if>