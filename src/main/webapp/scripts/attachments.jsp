<!-- Used jsp form instead of js to enable jsp el -->
<script type="text/javascript">
	var panelOpen = false;
	var total = 0;
	var ignoreStart = false;
	var maxAttachments = ${requestScope.maxAttachments};
	var counter = 0;
	
	<c:if test="${requestScope.attachmentsEnabled}">
		var template = "<div id='attach_#counter#'><table width='100%' class='gensmall'><tr><td>filename</td>";
		template += "<td><input type='file' size='50' name='upload'></td></tr>";
		template += "<tr><td>description</td>";
		template += "<td><input type='text' name='comment_#counter#' size='50'>";
		template += "&nbsp;&nbsp;<a href='javascript:removeAttach(#counter#)' class='gensmall'>[remove]</a></td></tr>";
		template += "</table><div style='border-top: 1px dashed #000;'>&nbsp;</div></div>";
	
		function addAttachmentFields()
		{
			if (counter < maxAttachments) {
				var s = template.replace(/#counter#/g, total);
				$("#attachmentFields").append(s);
				$("#total_files").val(++total);
	
				counter++;
	
				defineAttachmentButtonStatus();
			}
		}
	
		function removeAttach(index)
		{
			$("#attach_" + index).empty();
			counter--;
			defineAttachmentButtonStatus();
		}
	
		function defineAttachmentButtonStatus()
		{
			var disabled = !(counter < maxAttachments);
			document.forms[0].add_attach.disabled = disabled;
			document.forms[0].add_attach.style.color = disabled ? "#cccccc" : "#000000";
		}
	</c:if>

	<c:if test="${not empty attachments}">
		var templateEdit = "<table width='100%'><tr><td class='row2 gen'>filename</td>";
		templateEdit += "<td class='row2 gen'>#name#</td></tr>";
		templateEdit += "<tr><td class='row2 gen'>description</td>";
		templateEdit += "<td class='row2' valign='middle'><input type='text' size='50' name='edit_comment_#id#' value='#value#'>";
		templateEdit += "&nbsp;&nbsp;<span class='gensmall'><input type='checkbox' onclick='configureAttachDeletion(#id#, this);'>remove</span></td></tr>";
		templateEdit += "<tr><td colspan='2' width='100%' class='row3'></td></tr></table>";
		
		function editAttachments()
		{
			var data = new Array();
			<c:forEach var="attm" items="${requestScope.attachments}">
				var attach_${attm.id} = new Array();
	
				attach_${attm.id}["filename"] = "${attm.info.fileName}";
				attach_${attm.id}["description"] = "${attm.info.comment}";
				attach_${attm.id}["id"] = "${attm.id}";
	
				data.push(attach_${attm.id});
			</c:forEach>
			
			counter = data.length;
			<c:if test="${requestScope.attachmentsEnabled}">defineAttachmentButtonStatus();</c:if>
			
			for (var i = 0; i < data.length; i++) {
				var a = data[i];
				var s = templateEdit.replace(/#value#/, a["description"]);
				s = s.replace(/#name#/, a["filename"]);
				s = s.replace(/#id#/g, a["id"]);
	
				var v = document.getElementById("edit_attach").innerHTML;
				v += s;
				document.getElementById("edit_attach").innerHTML = v;
				document.post.edit_attach_ids.value += a["id"] + ",";
			}
		}
	
		function configureAttachDeletion(id, f)
		{
			if (f.checked) {
				document.post.delete_attach.value += id + ",";
			}
			else {
				var p = document.post.delete_attach.value.split(",");
				document.post.delete_attach.value = "";
				for (var i = 0; i < p.length; i++) {
					if (p[i] != id) {
						document.post.delete_attach.value += p[i] + ",";
					}
				}
			}
		}
	</c:if>
</script>