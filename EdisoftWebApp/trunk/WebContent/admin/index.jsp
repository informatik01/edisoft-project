<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Home Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css" />" />
</head>
<body>
	<div id="container">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div id="main">
		<h1>Welcome!</h1>
		<h3>You are in the Edisoft administration area.</h3>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>