<table cellspacing="0" cellpadding="1" border="0" class="genmed">
	<c:choose>
		<c:when test="${htmlAllowed}">
			<tr>
				<td><input type="checkbox" id="disable_html"
					name="disable_html" ${htmlChecked} /></td>
				<td><label for="disable_html">disableHtml")}</label></td>
			</tr>
		</c:when>
		<c:otherwise>
			<input type="hidden" name="disable_html" value="1" />
		</c:otherwise>
	</c:choose>

	<tr>
		<td><input type="checkbox" id="attach_sig" name="attachSig"
			value="true" /></td>
		<td><label for="attach_sig"><fmt:message
					key="all.jsp.appendSignature" /></label></td>
	</tr>

	<c:if test="${requestScope.showTopicType}">
		<tr>
			<td colspan="2"><fmt:message
					key="post_options_tab.jsp.topic.typ" />: <input type="radio"
				<c:if test="${infoType=='NORMAL'||empty infoType}">checked="checked"</c:if>
				value="NORMAL" id="topic_type0" name="infoType" /><label
				for="topic_type0"><fmt:message
						key="post_options_tab.jsp.normal" /></label>&nbsp;&nbsp; <input
				<c:if test="${infoType=='STICKY'}">checked="checked"</c:if>
				type="radio" value="STICKY" id="topic_type1" name="infoType" /><label
				for="topic_type1"><fmt:message
						key="post_options_tab.jsp.sticky" /></label>&nbsp;&nbsp; <c:if
					test="${requestScope.enableAnnouncement}">
					<input type="radio" value="CATEGORY_ANNOUNCEMENT" id="topic_type2"
						<c:if test="${infoType=='CATEGORY_ANNOUNCEMENT'}">checked="checked"</c:if>
						name="infoType" />
					<label for="topic_type2"><fmt:message
							key="post_options_tab.jsp.annmt.forum" /></label>&nbsp;&nbsp;<input
						<c:if test="${infoType=='DEPARTMENT_ANNOUNCEMENT'}">checked="checked"</c:if>
						type="radio" value="DEPARTMENT_ANNOUNCEMENT" id="topic_type2"
						name="infoType" />
					<label for="topic_type2"><fmt:message
							key="post_options_tab.jsp.annmt.forumzone" /></label>&nbsp;&nbsp;<input
						<c:if test="${infoType=='SYSTEM_ANNOUNCEMENT'}">checked="checked"</c:if>
						type="radio" value="SYSTEM_ANNOUNCEMENT" id="topic_type2"
						name="infoType" />
					<label for="topic_type2"><fmt:message
							key="post_options_tab.jsp.annmt.sys" /></label>&nbsp;&nbsp;</c:if></td>
		</tr>
	</c:if>
</table>