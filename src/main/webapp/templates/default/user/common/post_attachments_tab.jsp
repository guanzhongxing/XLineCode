<%@ include file="/scripts/attachments.jsp"%>

<table cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>
		<td colspan="2" id="tdAttachPanel" align="center">
			<table border="0" cellpadding="3" cellspacing="0" width="100%" id="tblAttachments">
				<tr>
					<td>
						<span class="gensmall">
						<b><fmt:message key="message.jsp.maxToAttach"/>:</b> ${requestScope.maxAttachments}
						<c:set var="maxSize" value="${requestScope.maxAttmSize / 1048576}" />
						<c:if test="${maxSize>1}">
							<b>/<fmt:message key="message.jsp.maxAttmSize"/>:</b> <font color="red">${maxSize} MB</font>
						</c:if>
						</span>
					</td>
				</tr>
				
				<tr>
					<td align="center">
						<div id="edit_attach"></div>

						<div id="attachmentFields">
						</div>			   
					</td>
				</tr>
				
				<tr>
					<td align="center" class="row3"><input type="button" name="add_attach" value="<fmt:message key="message.jsp.addAnotherFile"/>" class="mainoption" onclick="addAttachmentFields()" /></td>
				</tr>
			</table>
		</td>
	</tr>
</table>