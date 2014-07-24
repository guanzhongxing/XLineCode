<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ include file="/templates/common/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="utf-8" />
<title>{pageTitle}</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/style.css" />
</head>

<body bgcolor="#FFFFFF" text="#000000" link="#01336B" vlink="#01336B">
<span class="gen"><a name="top"></a></span>

<table border="0" cellpadding="3" cellspacing="1" width="100%"
	class="forumline">
	<tr>
		<th class="thcornerl" width="150" height="26"><fmt:message
			key="all.jsp.author" /></th>
		<th class="thcornerr"><fmt:message
			key="topic_review.jsp.messageTitle" /></th>
	</tr>

	<c:if test="${not empty requestScope.responses}">
		<c:forEach var="response" items="${requestScope.responses}"
			varStatus="rowCounter">
			<c:choose>
				<c:when test="${rowCounter.count%2==0}">
					<c:set var="rowColor" value="row1" />
				</c:when>
				<c:otherwise>
					<c:set var="rowColor" value="row2" />
				</c:otherwise>
			</c:choose>

			<c:set var="author" value="${response.author}" />

			<tr>
				<td align="left" valign="top" class="row1"><span class="name"><b>${author.name}</b></span>
				</td>

				<td class="row1" height="28" valign="top">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100%"><img
							src="${contextPath}/templates/default/images/icon_minipost.gif"
							width="12" height="9" border="0" alt="Post" /> <span
							class="postdetails"> <fmt:formatDate
							value="${response.createdTime}" dateStyle="medium" /> <fmt:formatDate
							value="${response.createdTime}" pattern="hh:mm" /> <span
							class="gen">&nbsp;</span>&nbsp;&nbsp;&nbsp;<fmt:message
							key="topic_review.jsp.subject" />: ${response.subject} </span></td>
					</tr>
					<tr>
						<td colspan="2">
						<hr />
						</td>
					</tr>
					<tr>
						<td colspan="2"><span class="postbody">${response.content}</span>
						</td>
					</tr>
				</table>
				</td>
			</tr>

			<tr>
				<td colspan="2" height="1" class="spacerow"><img
					src="${contextPath}/templates/${templateName}/images/spacer.gif"
					alt="" width="1" height="1" /></td>
			</tr>
		</c:forEach>
	</c:if>

	<c:set var="currentTopic" value="${requestScope.currentTopic}" />

	<tr>
		<td align="left" valign="top" class="row1"><span class="name"><b>${currentTopic.author.name}</b></span>
		</td>

		<td class="row1" height="28" valign="top">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%"><img
					src="${contextPath}/templates/default/images/icon_minipost.gif"
					width="12" height="9" border="0" alt="Post" /> <span
					class="postdetails"> <fmt:formatDate
					value="${currentTopic.createdTime}" dateStyle="medium" /> <fmt:formatDate
					value="${currentTopic.createdTime}" pattern="hh:mm" /> <span
					class="gen">&nbsp;</span>&nbsp;&nbsp;&nbsp;<fmt:message
					key="topic_review.jsp.subject" />: ${currentTopic.subject} </span></td>
			</tr>
			<tr>
				<td colspan="2">
				<hr />
				</td>
			</tr>
			<tr>
				<td colspan="2"><span class="postbody">${currentTopic.content}</span>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td colspan="2" height="1" class="spacerow"><img
			src="${contextPath}/templates/${templateName}/images/spacer.gif"
			alt="" width="1" height="1" /></td>
	</tr>
</table>
</body>
</html>
