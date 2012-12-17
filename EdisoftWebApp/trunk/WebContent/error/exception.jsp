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
		<h1>There was an error in Web Application</h1><br />
		<p><b>Error message:</b> ${pageContext.exception}<p><br />
 		<p><a href="#" onclick="toggle_visibility('error_details')">Click here to see stack trace</a></p>
 		<div id="error_details" style="display: none">
 		
		<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
			<pre>${trace}</pre>
		</c:forEach>
		
		</div>
 	</div>
 	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
 	</div>
</body>
</html>