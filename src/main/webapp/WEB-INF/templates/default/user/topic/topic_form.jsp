<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>
<%@ include
	file="/WEB-INF/templates/default/user/common/ckEditor_init.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/tabs.css" />
<script type="text/javascript"
	src="${resourcesHost}/javascripts/post.js"></script>


<sf:form action="http://upload.qiniu.com" styleId="post"
	modelAttribute="topic" method="post" enctype="multipart/form-data">
	<table cellspacing="0" cellpadding="10" width="100%" align="center"
		border="0">
		<tr>
			<td class="bodyline">
				<table cellspacing="2" cellpadding="2" width="100%" align="center"
					border="0">
					<tr>
						<td align="right"><input type="hidden" name="token"
							value="${requestScope.qiniuToken}" /> <input type="file"
							name="file" /><input type="submit"
							name="submit" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</sf:form>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>