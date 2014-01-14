<%@ include file="/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/style.css" />

<html:form action="admin/ranking/pointsconfig/update" method="post">
	<input type="hidden" name="pointConfig" value="true" />
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="3" height="25"><fmt:message
					key="ranking_points_config.jsp.title" /></th>
		</tr>

		<tr>
			<td align="left" colspan="3"><span class="gensmall"> <logic:messagesPresent
						message="true">
						<html:messages id="msg" message="true">
							<bean:write name="msg" />
							<br />
						</html:messages>
					</logic:messagesPresent>
			</span></td>
		</tr>
		
		<tr>
			<td class="row2"><span class="gen"><b><fmt:message
							key="ranking_points_config.jsp.topic.points" /></b></span></td>
			<td class="row2" align="center"><span class="gen"><b><fmt:message
							key="ranking_points_config.jsp.rsp.points" /></b></span></td>
			<td class="row2" align="center"><span class="gen"><b><fmt:message
							key="ranking_points_config.jsp.upload.attm.points" /></b></span></td>
		</tr>

		<tr>
			<td class="row2"><span class="gen"><input
					name="topicPoints" type="text" value="${config.infoPoints}" /> <html:errors
						property="topicPoints" /></td>
			<td class="row2" align="center"><span class="gen"><input
					name="rspPoints" type="text" value="${config.cmtPoints}" /> <html:errors
						property="rspPoints" /></td>
			<td class="row2" align="center"><span class="gen"><input
					name="uploadAttmPoints" type="text"
					value="${config.uploadAttmPoints}" /> <html:errors
						property="uploadAttmPoints" /></td>
		</tr>

		<tr align="center">
			<td class="catbottom" colspan="3" height="28"><input
				class="mainoption" type="submit"
				value="<fmt:message key="ranking_points_config.jsp.update" />"
				name="submit" /></td>
		</tr>
	</table>
</html:form>

