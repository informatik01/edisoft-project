<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Administrator Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/login.css" />" />
</head>
<body>
	<div class="login" style="margin-left: 30%">
		<h4>To access administration area you must first login with your credentials.</h4>
	</div>
	<div id="login-form">
		<form action="Login.do" method="post">
			<fieldset>
				<legend>Administrator Login</legend>
				<div id="login-error">${errorMessage}</div>
				<table>
				<tr>
					<th><label for="username">Username: </label></th>
					<td>
						<input type="text" id="username"
						name="username" size="35" placeholder="username"
						required="required" />
					</td>
				</tr>
				<tr>
					<th><label for="password">Password: </label></th>
					<td>
						<input type="password" id="password"
						name="password" size="35" placeholder="password"
						required="required" />
					</td>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<td><input type="submit" value="Login" /></td>
				</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>