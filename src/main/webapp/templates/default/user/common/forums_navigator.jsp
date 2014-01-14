<script type="text/javascript">
	function constructURL(){
		var ids=$("#select option:selected").val();
		var idArray=ids.split("_");
		var forumName=$("#forum"+idArray[1]).attr("value");
		document.location="displayTopics.do?forumzoneId="+idArray[0]+"&forumId="+idArray[1]+"&forumName="+forumName;
	}
</script>

<table cellspacing="0" cellpadding="0" border="0">
	<tr>			  
		<td nowrap="nowrap">
				<span class="gensmall"><fmt:message key="forums_navigator.jsp.goTo"/>:&nbsp;</span>
				
				<c:forEach var="forumzone" items="${requestScope.forumzones}">
					<c:set var="forums" value="${forumzone.forums}" />
					<c:forEach var="forum" items="${forums}">
						<input type="hidden" id="forum${forum.id}" value="${forum.name}" />
					</c:forEach>
				</c:forEach>
				
				<select id="select" onchange="if(this.options[this.selectedIndex].value != -1){constructURL();}" name="select">
					<option value="-1" selected="selected"><fmt:message key="forums_navigator.jsp.goToSelectForum"/></option>				
					<c:forEach var="forumzone" items="${requestScope.forumzones}">
                    	<optgroup label="${forumzone.name}">
						
						<c:set var="forums" value="${forumzone.forums}" />
						<c:forEach var="forum" items="${forums}">
							<option value="${forumzone.id}_${forum.id}">${forum.name}</option>
						</c:forEach>
						
                  		</optgroup>
					</c:forEach>
				</select>
		</td>
	</tr>
</table>