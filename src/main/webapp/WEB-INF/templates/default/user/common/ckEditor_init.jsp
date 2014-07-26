<script
	src="${resourcesHost}/javascripts/ckeditor_4.2.2_standard/ckeditor/ckeditor.js"></script>
<script>
	<c:choose>
	<c:when test="${requestScope.attachmentsEnabled}">
	window.onload = function() {
		CKEDITOR.replace('content', {
			filebrowserUploadUrl : '${contextPath}/file/upload.do'
		});
	};
	</c:when>
	<c:otherwise>
	window.onload = function() {
		CKEDITOR.replace('content');
	};
	</c:otherwise>
	</c:choose>
</script>