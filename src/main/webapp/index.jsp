<%@ page contentType="text/html;charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/2002/REC-xhtml1-20020801/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>${applicationScope.globalConfig.forumPageTitle}</title>
</head>
<body>
<script type="text/javascript">
document.location = "${contextPath}/displayForums.do";
</script>
<noscript>
<h1>${applicationScope.globalConfig.forumPageTitle}</h1>
<p>Javascript must be enabled to run this website. Please check your
browser settings or use a Javascript-capable browser.</p>
</noscript>
</body>
</html>