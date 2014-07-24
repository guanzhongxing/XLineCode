</td>
</tr>

<c:if
	test="${sessionScope.userSession.admin||sessionScope.userSession.moderator}">
	<tr>
		<td align="center"><span class="gen"><a
				href="${contextPath}/do/admin/index" id="adminpanel" rel="nofollow"><fmt:message
						key="footer.jsp.controlPanel" /></a></span></td>
	</tr>
</c:if>

<tr>
	<td align="center"><span class="copyright"> <fmt:message
				key="footer.jsp.author" /> <br> <fmt:message
				key="footer.jsp.copyright" /> <script
				src="http://s17.cnzz.com/stat.php?id=5150539&web_id=5150539&show=pic"
				language="JavaScript"></script>
	</span></td>
</tr>
</table>

<!-- Baidu Button BEGIN -->
<script type="text/javascript" id="bdshare_js"
	data="type=slide&amp;img=0&amp;pos=right&amp;uid=3900420"></script>
<script type="text/javascript" id="bdshell_js"></script>
<script type="text/javascript">
	var bds_config = {
		"bdTop" : 75
	};
	document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion="
			+ Math.ceil(new Date() / 3600000);
</script>
<!-- Baidu Button END -->
</body>
</html>