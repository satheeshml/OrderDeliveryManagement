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

	<spring:message code="shipmentMode.column1" var="column1" />
	<spring:message code="shipmentMode.column2" var="column2" />

	<div class="container">
		<h1>Shipment Mode</h1>
		<form class="form-inline" action="shipment" method="POST">
			<div class="form-group">
				<label for="mode" class="sr-only">ShipmentMode:</label> <input
					type="text" class="form-control" id="mode" name="mode"
					placeholder="Enter shipment mode" required/>
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
		<c:if test="${fn:length(modeList) gt 0}">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>${column1}</th>
						<th>${column2}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="mode" items="${modeList}">
						<tr>
							<td>${mode.id}</td>
							<td>${mode.mode}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>