<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="constant" class="com.pespl.order.mgmt.delivery.ShipmentMode" scope="request" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="css/bootstrap-select.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
	<spring:message code="item.name" var="name" />
	<spring:message code="item.customer.part" var="customerPart" />
	<spring:message code="item.supplier.part" var="supplierPart" />
	<spring:message code="item.price" var="price" />
	<spring:message code="item.price.currency" var="currency" />
	<spring:message code="item.price.asOn" var="asOn" />
	
	<jsp:include page="../base/_header.jsp" />
	<div class="container container-fluid">
		<c:if test="${fn:length(itemList) gt 0}">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>${name}</th>
						<th>${customerPart}</th>
						<th>${supplierPart}</th>
						<th>${price}</th>
						<th>${currency}</th>
						<th>${asOn}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${itemList}">
						<tr>
							<td>${item.name}</td>
							<td>${item.customerPart}</td>
							<td>${item.supplierPart}</td>
							<td>${item.supplierPart}</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>
	<jsp:include page="../base/_footer.jsp" />
</body>
</html>