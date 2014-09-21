<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${resourcesHost}/css/style.css" />

<sf:form action="${contextPath}/rankings/points" method="put">
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="3" height="25"><fmt:message
					key="ranking_points_config.jsp.title" /></th>
		</tr>

		<tr>
			<td align="left" colspan="3"><span class="gensmall"><c:if
						test="${updated}">
						<fmt:message key="message.update.points.success" />
					</c:if> </span></td>
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
</sf:form>

