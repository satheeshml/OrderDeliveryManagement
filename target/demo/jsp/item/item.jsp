<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="constant"
	class="com.pespl.order.mgmt.delivery.ShipmentMode" scope="request" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<spring:message code="item.name" var="name" />
	<spring:message code="item.customer.part" var="customerPart" />
	<spring:message code="item.supplier.part" var="supplierPart" />
	<spring:message code="item.price" var="price" />

	<div class="container">
		<h1>Item</h1>
		<form action="item" method="POST">
			<div class="form-group">
				<label for="name">${name}:</label> <input type="text"
					class="form-control" id="name" name="name" placeholder="Enter Name"/>
			</div>
			<div class="form-group">
				<label for="customerPart">Customer Part:</label> <input type="text"
					class="form-control" id="customerPart" name="customerPart"  placeholder="Enter Customer Part"/>
			</div>
			<div class="form-group">
				<label for="supplierPart">Supplier Part:</label> <input type="text"
					class="form-control" id="supplierPart" name="supplierPart" placeholder="Enter Supplier Part"/>
			</div>
			<div class="form-group">
				<label for="price">Price:</label> <input type="text"
					class="form-control" id="price" name= "price" placeholder="Enter Price" />
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
		
		<c:if test="${fn:length(itemList) gt 0}">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>${name}</th>
						<th>${customerPart}</th>
						<th>${supplierPart}</th>
						<th>${price}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${itemList}">
						<tr>
							<td>${item.name}</td>
							<td>${item.customerPart}</td>
							<td>${item.supplierPart}</td>
							<td>${item.price.price}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		
	</div>

</body>
</html>