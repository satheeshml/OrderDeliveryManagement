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
	<c:choose>
		<c:when test='${action == "CHANGE_PRICE"}'>
			<c:set var="event" value="CHANGE PRICE" />
			<c:set var="isCustomerDisabled" value="disabled" />
			<c:set var="isNameDisabled" value="disabled" />
			<c:set var="isCustomerPartDisabled" value="disabled" />
			<c:set var="isSupplierPartDisabled" value="disabled" />
			<c:set var="isPriceDisabled" value="" />
			<c:set var="formAction" value="changeItemPrice" />
		</c:when>
		<c:otherwise>
			<c:set var="event" value="SAVE" />
			<c:set var="isNameDisabled" value="" />
			<c:set var="isCustomerDisabled" value="" />
			<c:set var="isCustomerPartDisabled" value="" />
			<c:set var="isSupplierPartDisabled" value="" />
			<c:set var="formAction" value="item" />
		</c:otherwise>
	</c:choose>
	<jsp:include page="../base/_header.jsp" />
	<div class="container container-fluid">
		<form action="${formAction}" method="POST">
			<jsp:include page="../base/_info.jsp">
				<jsp:param value="${errorMsg}" name="errorMsg" />
				<jsp:param value="${successMsg}" name="successMsg" />
			</jsp:include>
			<input type="hidden" name="id" value="${item.id}" />
			<div class="form-group">
				<label for="customer">Customer:</label> <select class="form-control selectpicker"
					multiple="multiple" id="customer" name="customer" ${isCustomerDisabled} required>
					<c:forEach var="customer" items="${customerList}">
						<option value="${customer.id}">${customer.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="name">${name}:</label> <input type="text" class="form-control" id="name" name="name"
					placeholder="Enter Name" ${isNameDisabled} value="${item.name}" required />
			</div>
			<div class="form-group">
				<label for="customerPart">Customer Part:</label> <input type="text" class="form-control"
					id="customerPart" name="customerPart" placeholder="Enter Customer Part"
					value="${item.customerPart}" ${isCustomerPartDisabled} required />
			</div>
			<div class="form-group">
				<label for="supplierPart">Supplier Part:</label> <input type="text" class="form-control"
					id="supplierPart" name="supplierPart" placeholder="Enter Supplier Part"
					value="${item.supplierPart}" ${isSupplierPartDisabled} required />
			</div>

			<div class="form-group">
				<label for="price">Price:</label> <input type="text" class="form-control" id="price"
					name="price" placeholder="Enter Price" value="${item.price.price}" ${isPriceDisabled} required />
				<select class="form-control selectpicker" id="currency" name="currency" ${isPriceDisabled}
					required>
					<c:forEach var="currency" items="${currencyList}">
						<option value="${currency.id}">${currency.name}-${currency.country}</option>
					</c:forEach>
				</select>

			</div>
			<button type="submit" class="btn btn-default">${event}</button>
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
							<td><a href="changeItemPrice?id=${item.id}">${item.price.price}</a></td>
							<td><a href="udpateItem?id=${item.id}"> <span class="glyphicon glyphicon-edit"
									style="color: red"></span>
							</a></td>
							<td><a href="deleteItem?id=${item.id}"> <span
									class="glyphicon glyphicon-floppy-remove" style="color: red"></span>
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</div>
	<jsp:include page="../base/_footer.jsp" />
</body>
</html>