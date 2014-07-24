<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ include file="/WEB-INF/templates/common/taglibs.jsp"%>
<c:set var="contextPath" value="${applicationScope.contextPath}" />
<fmt:setLocale value="${sessionScope.userSession.locale}" />
<c:set var="locale" value="${sessionScope.userSession.locale}" scope="page" />