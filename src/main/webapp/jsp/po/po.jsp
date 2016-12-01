<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="constant" class="com.pespl.order.mgmt.po.PurchaseOrder" scope="request" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<!-- Bootstrap Date-Picker Plugin -->
<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="css/bootstrap-datepicker3.css" />
<link rel="stylesheet" href="css/common.css" />
</head>
<body>
	<spring:message code="po.number" var="number" />
	<spring:message code="po.customer" var="customerTxt" />
	<spring:message code="po.amend" var="amendTxt" />
	<spring:message code="po.remove" var="removeTxt" />

	<spring:message code="po.add.pr" var="addPurchaseRequest" />
	<spring:message code="po.remove.pr" var="removePurchaseRequest" />
	<spring:message code="po.pr.mode" var="mode" />

	<spring:message code="po.pr.add.prItem" var="addPurchaseItem" />
	<spring:message code="po.pr.remove.prItem" var="removePurchaseItem" />
	<spring:message code="po.pr.prItem.item" var="newItem" />
	<spring:message code="po.pr.prItem.quantity" var="quantity" />
	<jsp:include page="../base/_header.jsp" />
	<div class="container container-fluid">
		<form action="purchaseOrder" method="POST">
			<jsp:include page="../base/_info.jsp" />

			<div class="form-group">
				<label for="number">${number}:</label> <input type="number" min="0" class="form-control"
					id="number" name="poNumber" placeholder="Enter Purchase Number" required />
			</div>
			
			<div class="form-group" id="customerPanel">
				<label for="customer">${customer}</label> <select class="form-control" id="customer"
					name="customer">
					<c:forEach var="customer" items="${customerList}">
						<option value="${customer.id}">${customer.name}</option>
					</c:forEach>
				</select>
			</div>

			<hr id="purchaseRequestPanel" style="border-width: 4px; border-color: #8fabd8;">

			<button id="b1" class="btn add-more-request btn-default" type="button">${addPurchaseRequest}</button>
			<input id="count" type="hidden" name="count" value="0" /> <input id="requestLastId"
				type="hidden" name="requestLastId" value="1" />

			<button type="submit" class="btn btn-default" onsubmit="validate()">Submit</button>
		</form>
		<br> <br>
		<c:if test="${fn:length(poList) gt 0}">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>${number}</th>
						<th>${customerTxt}</th>
						<th>${amendTxt}</th>
						<th>${removeTxt}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="po" items="${poList}">
						<tr>
							<td>${po.number}</td>
							<td>${po.customer.name}</td>
							<td><a href="amendPO?po_id=${po.id}"> <span class="glyphicon glyphicon-edit"
									style="color: green"></span>
							</a></td>
							<td><a href="deletePurchaseOrder?id=${po.id}"> <span
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
<script type="text/javascript">
	var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
	var options = {
		format : 'dd/mm/yyyy',
		container : container,
		todayHighlight : true,
		autoclose : true,
	};

	function addNewPurchaseRequest(next) {
		var newPurchaseRequest = $('<div id="pr_' + next + '" class="form-group"></div>')
		var newLabelForMode = $('<label for="pr_' + next + '_mode">${mode}:</label>')
		var newMode = $('<select class="form-control" id="pr_' + next + '_mode" name="pr_' + next
			+ '_mode"><c:forEach var="mode" items="${modeList}"> <option value="${mode.id}">${mode.mode}</option> </c:forEach></select>');

		var count = $("#count").val();
		console.log(count);
		if (next == 1 || count == 0) {
			$("#purchaseRequestPanel").after(newPurchaseRequest);
		} else {
			var previousRequest = $("#pr_" + (next - 1));
			previousRequest.after(newPurchaseRequest);
		}

		newPurchaseRequest.append(newLabelForMode).append(newMode);

		var newPODateLabel = $('<label for="pr_'+next+'_poDate">Purchase Order Date</label>');
		var newPODate = $('<input class="form-control" id="pr_' + next + '_poDate" name="pr_' + next + '_poDate" placeholder="DD/MM/YYYY" type="text" required/>');
		newPODate.datepicker(options);
		newPurchaseRequest.append(newPODateLabel).append(newPODate);
		
		var newPILabel = $('<label for="pr_' + next + '_pi">PerformaInvoice Date</label>"');
		var newPI = $('<input class="form-control" id="pr_' + next + '_pi" name="pr_' + next + '_pi" placeholder="DD/MM/YYYY" type="text" required/>');
		newPI.datepicker(options);
		newPurchaseRequest.append(newPILabel).append(newPI);
		
		var purchaseItemCounter = $('<input id="pr_' + next + '_itemCounter" type="hidden" name="pr_' + next + '_itemCounter" value="1"/>');
		newPurchaseRequest.append(purchaseItemCounter);
		var newPurchaseItem = $('<button id="add_item_' + next + '" class="btn add-more-item btn-default" type="button">${addPurchaseItem}</button>');
		newPurchaseRequest.append($("<br>"));
		newPurchaseRequest.append(newPurchaseItem);
		var removeButton = $('<button id="remove' + next + '" class="btn btn-danger remove-me btn-default" >${removePurchaseRequest}</button>');

		newPurchaseRequest.append(removeButton);
		var newDivider = $('<hr id="purchaseRequestPanel_' + next + '" style="border-width: 3px; border-color: #8fabd8;">');
		newPurchaseRequest.append(newDivider);

		$("#count").val(parseInt(count) + 1);
		$("#requestLastId").val(next);
		$('#remove' + next).click(function(e) {
			e.preventDefault();
			var fieldNum = this.id.charAt(this.id.length - 1);
			var fieldID = "#pr_" + fieldNum;
			var divider = "#purchaseRequestPanel_" + fieldNum;
			$(this).remove();
			$(fieldID).remove();
			$(divider).remove();
			var countValue = parseInt($("#count").val()) - 1;
			console.log("Count Value ==> " + countValue);
			$("#count").val(countValue)
		});
		$(".add-more-item").click(function(e) {
			e.preventDefault();
			var fieldNum = this.id.charAt(this.id.length - 1);
			addPurchaseItem(fieldNum);
		});
	}
	function addPurchaseItem(requestId) {
		var currentItemId = $('#pr_' + requestId + '_itemCounter').val();
		var nextItemId = parseInt(currentItemId) + 1;
		var newPurchaseItem = $('<div id="pr_' + requestId + '_prItem_' + nextItemId + '" class="form-group"></div>');
		var itemContainer = $('<div class="form-group"></div>');
		var quantityContainer = $('<div class="form-group"></div>');

		var newLabelForItem = $('<label for="pr_' + requestId + '_prItem_' + nextItemId + '">${newItem}:</label>');
		var newItem = $('<select class="form-control" id="pr_' + requestId + '_prItem_' + nextItemId + '_item" name="pr_' + requestId + '_prItem_' + nextItemId
			+ '_item"><c:forEach var="item" items="${itemList}"> <option value="${item.id}">${item.name}</option> </c:forEach></select>');
		itemContainer.append(newLabelForItem).append(newItem);
		var newLabelForQuantity = $('<label for="pr_' + requestId + '_prItem_' + nextItemId + '">${quantity}:</label>');
		var newQuantity = $(' <input type="number" min="1"  class="form-control" id="pr_' + requestId + '_prItem_' + nextItemId + '_quantity" name="pr_' + requestId + '_prItem_' + nextItemId
			+ '_quantity" placeholder="Enter Quantity" required/>');
		quantityContainer.append(newLabelForQuantity).append(newQuantity);
		newPurchaseItem.append(itemContainer);
		newPurchaseItem.append(quantityContainer);

		var removeButton = $('<button id="pr_' + requestId + '_removeItem_' + nextItemId + '" class="btn btn-danger remove-item btn-default" >${removePurchaseItem}</button>');
		newPurchaseItem.append(removeButton);

		var newDivider = $('<hr id="pr_' + requestId + '_prItemPanel_' + nextItemId + '" style="border-width: 1px; border-color: #8fabd8;">');
		newPurchaseItem.append(newDivider);

		$('#add_item_' + requestId).before(newPurchaseItem);

		$('#pr_' + requestId + '_itemCounter').val(nextItemId);

		$("#pr_" + requestId + "_removeItem_" + nextItemId).click(function(e) {
			e.preventDefault();
			var fields = this.id.split("_");
			var item = "#pr_" + fields[1] + "_prItem_" + fields[3];
			var divider = "#pr_" + fields[1] + "_prItemPanel_" + fields[3];
			$(this).remove();
			$(item).remove();
			$(divider).remove();
			console.log("remove item clicked");
		});

	}
	$(document).ready(function() {
		var next = 0;
		$(".add-more-request").click(function(e) {
			e.preventDefault();
			next = next + 1;
			addNewPurchaseRequest(next);
		});
	});
</script>
</html>