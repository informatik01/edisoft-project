<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Error page</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css" />" />
<script type="text/javascript" src="<c:url value="/js/utils.js" />"></script>
</head>
<body>
	<div id="container">
	<%@ include file="/WEB-INF/jspf/top-logo.jspf"%>
	<div id="main">
		<button onclick="history.back()">Back to Previous Page</button>
		<h1>Page Not Found.</h1><br />
		<h4 style="text-decoration: underline;"><a href="#" onclick="toggle_visibility('error_details')">Click to see error details:</a></h4>
		<div id="error_details" style="display: none">
			<p><b>Error code:</b> ${pageContext.errorData.statusCode}</p>
			<p><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p><br />
		</div>
 	</div>
 	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
 	</div>
</body>
</html>