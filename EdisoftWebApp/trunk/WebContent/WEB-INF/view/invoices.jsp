<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Home Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value="/css/main.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/jquery.tablesorter.css" />" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/jquery.tablesorter.pager.css" />" />
<script type="text/javascript" src="<c:url value="/js/utils.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-1.8.3.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.tablesorter.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.tablesorter.pager.js" />"></script>
<script type="text/javascript">
		$(document).ready(function() {
			$("table")
			.tablesorter({sortList: [[3, 1]]})
			.tablesorterPager({container: $("#pager"), size: 5});
		});
</script>
<body>
	<div id="container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<div id="main">
			<br />
			<h2>List of all Invoices:</h2>
			<c:if test="${empty invoices}">
				<h4>There are currently no Invoice documents in the database.</h4>
			</c:if>

			<table class="document tablesorter">
				<col class="wide" />
				<thead>
					<tr class="header">
						<th>Document UID</th>
						<th>Document Type</th>
						<th>Document Number</th>
						<th>Document Date1</th>
						<th>Document Date2</th>
						<th>Sender Name</th>
						<th>Receiver Name</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${invoices}" var="invoice">
					<tr onclick="goToLocation('invoice-details?uid=${invoice.uid}')">
						<td><a href="#" title="Click to see details">${invoice.uid}</a></td>
						<td><a href="#" title="Click to see details">${invoice.documentType}</a></td>
						<td><a href="#" title="Click to see details">${invoice.documentNumber}</a></td>
						<td><a href="#" title="Click to see details">${invoice.documentDate1}</a></td>
						<td><a href="#" title="Click to see details">${invoice.documentDate2}</a></td>
						<td><a href="#" title="Click to see details">${invoice.senderName}</a></td>
						<td><a href="#" title="Click to see details">${invoice.receiverName}</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div id="pager" class="pager">
				<form>
					<img src="<c:url value="/img/tablesorter/pager/first.png" />" class="first"/>
					<img src="<c:url value="/img/tablesorter/pager/prev.png" />" class="prev"/>
					<input type="text" class="pagedisplay"/>
					<img src="<c:url value="/img/tablesorter/pager/next.png" />" class="next"/>
					<img src="<c:url value="/img/tablesorter/pager/last.png" />" class="last"/>
					<select class="pagesize">
						<option selected="selected" value="5">5</option>
						<option value="10">10</option>
						<option value="30">30</option>
						<option  value="50">50</option>
					</select>
				</form>
			</div>
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>