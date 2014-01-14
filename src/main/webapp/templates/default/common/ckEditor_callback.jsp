<%@ page contentType="text/html;charset=utf-8" language="java"%>
<script type="text/javascript" src="${contextPath}/scripts/jquery-1.10.2.min.js"></script>

<script type="text/javascript">
window.parent.CKEDITOR.tools.callFunction( ${CKEditorFuncNum}, '${url}', function() {
	if(${isImage}){
		var input = $('<input>').attr({
		    type: 'hidden',
		    name: 'attachmentIds',
		    value: '${attachmentId}'
		});
		$('form', window.parent.document).append($(input));
		
		return true;
	}else{
		alert("上传的文件须是图片！");
		return false;
	}
});
</script>