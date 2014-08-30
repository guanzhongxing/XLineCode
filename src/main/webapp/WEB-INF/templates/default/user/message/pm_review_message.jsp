<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ include file="/WEB-INF/templates/common/taglibs.jsp"%>
<fmt:message var="title" key="all.jsp.privateMsg" />

<html>
	<head>
		<meta http-equiv="Content-Style-Type" content="text/css" />
		<link rel="stylesheet" type="text/css" href="${resourcesHost}/css/style.css" />
	</head>

	<c:set var="reviewedPm" value="${requestScope.thePm}" />
	<body bgcolor="#FFFFFF" text="#000000" link="#01336B" vlink="#01336B">
	<span class="gen"><a name="top"></a></span>
	<table cellspacing="0" cellpadding="10" width="100%" align="center" border="0">
		<tbody>
			<tr>
				<td class="bodyline">
					<table class="forumline" cellspacing="1" cellpadding="4" width="100%" border="0">
						<tbody>
							<tr>
								<th class="thhead" nowrap="nowrap" colspan="3"><fmt:message key="pm_list.jsp.inbox"/></th>
							</tr>
							<tr>
								<td class="row2"><span class="genmed"><fmt:message key="pm.jsp.from"/>:</span></td>
								<td class="row2" width="100%" colspan="2"><span class="genmed"><a class="nav" href="${contextPath}/users/${reviewedPm.author.id}">${reviewedPm.author.name}</a></span></td>
							</tr>
							<tr>
								<td class="row2"><span class="genmed"><fmt:message key="pm.jsp.to"/>:</span></td>
								<td class="row2" width="100%" colspan="2"><span class="genmed"><a class="nav" href="${contextPath}/users/${reviewedPm.receiver.id}">${reviewedPm.receiver.name}</a></span></td>
							</tr>
							<tr>
								<td class="row2"><span class="genmed"><fmt:message key="pm.jsp.date"/>:</span></td>
								<td class="row2" width="100%" colspan="2"><span class="genmed">${reviewedPm.createdTime}</span></td>
							</tr>
							<tr>
								<td class="row2"><span class="genmed"><fmt:message key="all.jsp.msgSubject"/>:</span></td>
								<td class="row2" width="100%"><span class="genmed">${reviewedPm.subject}</span></td>
								<td class="row2" nowrap="nowrap" align="right">&nbsp;</td>
							</tr>
							<tr>
								<td class="row1" valign="top" colspan="3">
									<span class="postbody">${reviewedPm.content}
									<hr/>
										${reviewedPm.author.signature}
									</span>
								</td>
							</tr>
						</tbody>
					</table>
			  </td>
			</tr>
		</tbody>
	</table>
	</body>
</html>