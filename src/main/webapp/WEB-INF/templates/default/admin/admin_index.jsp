<%@ include file="/WEB-INF/templates/default/common/common_header.jsp"%>

<html>
<head>
<title>Vertonur - Administration interface</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<frameset rows="*" cols="170,*" framespacing="0" frameborder="NO" border="0">
  <frame src="${contextPath}/templates/default/admin/menu.jsp" name="leftFrame" scrolling="NO" noresize>
  <frame src="${contextPath}/do/${requestScope.servicePath}" name="main">
</frameset>
<noframes><body>

</body></noframes>
</html>
