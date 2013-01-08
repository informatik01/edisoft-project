<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<button onclick="history.back()">Back to List of all Invoices</button>
		<c:if test="${empty invoice}">
			<h4>There are currently no Invoice document in the database.</h4>
		</c:if>
		
		<div id="invoice-details">
			<table>
				<caption>Invoice header</caption>
				<tr>
					<th>Document UID</th>
					<td><input  type="text" id="uid" value="${invoice.uid}" disabled="disabled"/></td>
					<th>Document Type</th>
					<td><input  type="text" id="uid" value="${invoice.documentType}" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Receiver System Type</th>
					<td><input  type="text" id="uid" value="${invoice.receiverSystemType}" readonly="readonly" /></td>
					<th>Document Number</th>
					<td><input  type="text" id="uid" value="${invoice.documentNumber}" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Document Date1</th>
					<td><input  type="text" id="uid"
						value="<fmt:formatDate value="${invoice.documentDate1}" pattern="dd/MM/yyy" />" readonly="readonly" />
					</td>
					<th>Document Date2</th>
					<td><input  type="text" id="uid"
						value="<fmt:formatDate value="${invoice.documentDate2}" pattern="dd/MM/yyy" />" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>Sender ILN</th>
					<td><input  type="text" id="uid" value="${invoice.senderILN}" readonly="readonly" /></td>
					<th>Sender Code by Receiver</th>
					<td><input  type="text" id="uid" value="${invoice.senderCodeByReceiver}" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Sender Name</th>
					<td><input  type="text" id="uid" value="${invoice.senderName}" readonly="readonly" /></td>
					<th>Sender Address</th>
					<td><input  type="text" id="uid" value="${invoice.senderAddress}" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Receiver ILN</th>
					<td><input  type="text" id="uid" value="${invoice.receiverILN}" readonly="readonly" /></td>
					<th>Receiver Code by Receiver</th>
					<td><input  type="text" id="uid" value="${invoice.receiverCodeByReceiver}" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Receiver Name</th>
					<td><input  type="text" id="uid" value="${invoice.receiverName}" readonly="readonly" /></td>
					<th>Receiver Address</th>
					<td><input  type="text" id="uid" value="${invoice.receiverAddress}" readonly="readonly" /></td>
				</tr>
			</table>
		<br />
		<h3><i>Invoice details</i></h3>
		<hr />
		<c:forEach items="${invoice.details}" var="detail">
		<table>
				<tr>
					<th>Line Number</th>
					<td><input type="text" value="${detail.lineNumber}" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Supplier Item Code</th>
					<td><input type="text" value="${detail.supplierItemCode}" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Item Description</th>
					<td><input type="text" value="${detail.itemDescription}" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Invoice Quantity</th>
					<td><input type="text" value="${detail.invoiceQuantity}" readonly="readonly" /></td>
				</tr>
				<tr>
					<th>Invoice Unit Net Price</th>
					<td><input type="text" value="${detail.invoiceUnitNetPrice}" readonly="readonly" /></td>
				</tr>
		</table>
		<hr />
		</c:forEach>
		</div>
	</div>
	<button onclick="history.back()">Back to List of all Invoices</button>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>