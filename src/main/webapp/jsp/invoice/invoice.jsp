<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<!-- Bootstrap Date-Picker Plugin -->
<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="css/bootstrap-datepicker3.css" />
<link rel="stylesheet" href="css/common.css" />
<script type="text/javascript" src="js/invoice/invoice.js"></script>
</head>
<body>
	<spring:message code="invoice.number" var="number" />
	<spring:message code="invoice.customer" var="customer" />

	<spring:message code="invoice.po" var="po" />
	<spring:message code="invoice.add.delivered.item" var="addDeliveredItem" />
	<spring:message code="invoice.remove.delivered.item" var="removeDeliveredItem" />

	<spring:message code="invoice.delivered.item" var="item" />
	<spring:message code="invoice.delivered.quantity" var="quantity" />
	<jsp:include page="../base/_header.jsp" />

	<div class="container container-fluid">
		<form action="invoice" method="POST">
			<jsp:include page="../base/_info.jsp" />
			<div class="form-group">
				<label for="invoice">${number}</label> <input type="text" class="form-control" id="invoice"
					name="invoice" placeholder="Enter Invoice Number" required />
			</div>
			
			<div class='form-group' id="invoice_date_panel">
				<label for="invoice_date">Invoice Date</label> <input class="form-control" id="invoice_date" name="invoice_date"
					placeholder="DD/MM/YYYY" type="text" required />
			</div>
			
			<div class="form-group" id="customerPanel">
				<label for="customer">${customer}</label> <select class="form-control" id="customer"
					name="customer">
					<option value=""></option>
					<c:forEach var="customer" items="${customerList}">
						<option value="${customer.id}">${customer.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class='form-group' id="etaPanel">
				<label for="eta">ETA</label> <input class="form-control" id="eta" name="eta"
					placeholder="DD/MM/YYYY" type="text" required />
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div>
	<jsp:include page="../base/_footer.jsp" />
</body>
</html>