<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>
<%@ include file="/WEB-INF/templates/default/user/common/header.jsp"%>

<table cellspacing="0" cellpadding="10" width="100%" align="center" border="0">
	<tr>
		<td class="bodyline" valign="top">
		<table cellspacing="2" cellpadding="2" width="100%" align="center">
			<tr>
				<td valign="middle" nowrap="nowrap" align=left class="gensmall">
					<a class="maintitle" href="${contextPath}/forums"><fmt:message
					key="display_topics.jsp.forums" /></a>
				</td>
				<td valign="bottom" align="left" colspan="2">
				</td>
				<td class="nav" nowrap="nowrap" align="right">
				</td>
			</tr>
		</table>

		<table class="forumline" cellspacing="1" cellpadding="4" width="100%"
			border="0">
			<tr>
				<th class="thcornerl" nowrap="nowrap" align="center" colspan="6"
					height="25">&nbsp;<fmt:message key="display_topics.jsp.topic" />&nbsp;</th>
			</tr>

			<tr align="center">
				<td valign="middle" align="center" colspan="6"
					height="28">
					<img src="${resourcesHost}/images/404.gif" />
				</td>
			</tr>
		</table>


		<table cellspacing="2" cellpadding="2" width="100%" align="center"
			border="0">
			<tr>
				<td valign="middle" align="right"></td>

				<td valign="middle" align="right" width="50">
				</td>
			</tr>

			<tr>
				<td align="left" colspan="3"><span class="nav"></span></td>
			</tr>
		</table>

		<table cellspacing="0" cellpadding="5" width="100%" border="0">
			<tr>
				<td align="left" class="gensmall"></td>
				<td align="right"></td>
			</tr>
		</table>

		<table cellspacing="0" cellpadding="0" width="100%" align="center"
			border="0">
			<tr>
				<td valign="top" align="left">
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<%@ include file="/WEB-INF/templates/default/common/footer.jsp"%>