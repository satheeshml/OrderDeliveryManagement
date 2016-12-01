<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="constant" class="com.pespl.order.mgmt.po.PurchaseOrder"
	scope="request" />

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
	<spring:message code="po.number" var="number" />
	<spring:message code="po.customer" var="customer" />

	<spring:message code="po.add.pr" var="addPurchaseRequest" />
	<spring:message code="po.remove.pr" var="removePurchaseRequest" />
	<spring:message code="po.pr.mode" var="mode" />

	<spring:message code="po.pr.add.prItem" var="addPurchaseItem" />
	<spring:message code="po.pr.remove.prItem" var="removePurchaseItem" />
	<spring:message code="po.pr.prItem.item" var="newItem" />
	<spring:message code="po.pr.prItem.quantity" var="quantity" />

	<div class="container">
		<h1>Purchase Order</h1>
		<form action="purchaseOrder" method="POST">
			<div class="form-group">
				<label for="number">${number}:</label> <input type="text"
					class="form-control" id="number" name="poNumber"
					placeholder="Enter Purchase Number" />
			</div>
			<div class="form-group" id="customerPanel">
				<label for="customer">${customer}</label> <select
					class="form-control" id="customer" name="customer">
					<c:forEach var="customer" items="${customerList}">
						<option value="${customer.id}">${customer.name}</option>
					</c:forEach>
				</select>
			</div>

			<hr id="purchaseRequestPanel"
				style="border-width: 4px; border-color: #8fabd8;">

			<button id="b1" class="btn add-more-request btn-default" type="button">${addPurchaseRequest}</button>
			<input id="count" type="hidden" name="count" value="0" />
			<input id="requestLastId" type="hidden" name="requestLastId" value="1" />

			<button type="submit" class="btn btn-default">Submit</button>
		</form>

		<c:if test="${fn:length(poList) gt 0}">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>${number}</th>
						<th>${customer}</th>
						<th>${mode}</th>
						<th>${item}</th>
						<th>${quantity}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="po" items="${poList}">
						<tr>
							<td>${po.number}</td>
							<td>${po.customer.id}</td>
							<td>${po.purchaseRequests[0].mode.id}</td>
							<td>${po.purchaseRequests[0].purchaseItem[0].id}</td>
							<td>${po.purchaseRequests[0].purchaseItem[0].quantity}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
<script type="text/javascript">
	function addNewPurchaseRequest(next) {
		var newPurchaseRequest = $('<div id="pr_'+next+'" class="form-group"></div>')
		var newLabelForMode = $('<label for="pr_'+next+'_mode">${mode}:</label>')
		var newMode = $(' <select class="form-control" id="pr_'+next+'_mode" name="pr_'+next+'_mode"><c:forEach var="mode" items="${modeList}"> <option value="${mode.id}">${mode.mode}</option> </c:forEach></select>');
		
		var count = $("#count").val();
		console.log(count);
		if(next==1 || count==0){
			$("#purchaseRequestPanel").after(newPurchaseRequest);	
		}else{
			var previousRequest = $("#pr_"+(next-1));
			previousRequest.after(newPurchaseRequest);
		}
	
		newPurchaseRequest.append(newLabelForMode).append(newMode);
		//var newIn = '<input autocomplete="off" class="input form-control" id="field' + next + '" name="field' + next + '" type="text">';
		//var newInput = $(newIn);
		var purchaseItemCounter = $('<input id="pr_'+next+'_itemCounter" type="hidden" name="pr_'+next+'_itemCounter" value="1"/>');
		newPurchaseRequest.append(purchaseItemCounter);
		var newPurchaseItem = $('<button id="add_item_'+next+'" class="btn add-more-item btn-default" type="button">${addPurchaseItem}</button>');
		newPurchaseRequest.append($("<br>"));
		newPurchaseRequest.append(newPurchaseItem);
		var removeButton = $('<button id="remove'+next+'" class="btn btn-danger remove-me btn-default" >${removePurchaseRequest}</button>');
		
		newPurchaseRequest.append(removeButton);
		var newDivider = $('<hr id="purchaseRequestPanel_'+next+'" style="border-width: 3px; border-color: #8fabd8;">');		
		newPurchaseRequest.append(newDivider);
		
		$("#count").val(parseInt(count)+1);
		$("#requestLastId").val(next);
		$('#remove'+next).click(function(e) {
			e.preventDefault();
			var fieldNum = this.id.charAt(this.id.length - 1);
			var fieldID = "#pr_" + fieldNum;
			var divider = "#purchaseRequestPanel_" + fieldNum;
			$(this).remove();
			$(fieldID).remove();
			$(divider).remove();
			var countValue = parseInt($("#count").val())-1;
			console.log("Count Value ==> "+countValue);
			$("#count").val(countValue)
		});
		$(".add-more-item").click(function(e) {
			e.preventDefault();
			var fieldNum = this.id.charAt(this.id.length - 1);
			addPurchaseItem(fieldNum);
		});
	}	
	function addPurchaseItem(requestId){
		var currentItemId = $('#pr_'+requestId+'_itemCounter').val();
		var nextItemId = parseInt(currentItemId) + 1;
		var newPurchaseItem = $('<div id="pr_'+requestId+'_prItem_'+nextItemId+'" class="form-group"></div>');
		var itemContainer = $('<div class="form-group"></div>');
		var quantityContainer = $('<div class="form-group"></div>');
		
		var newLabelForItem = $('<label for="pr_'+requestId+'_prItem_'+nextItemId+'">${newItem}:</label>');
		var newItem = $('<select class="form-control" id="pr_'+requestId+'_prItem_'+nextItemId+'_item" name="pr_'+requestId+'_prItem_'+nextItemId+'_item"><c:forEach var="item" items="${itemList}"> <option value="${item.id}">${item.name}</option> </c:forEach></select>');
		itemContainer.append(newLabelForItem).append(newItem);
		var newLabelForQuantity = $('<label for="pr_'+requestId+'_prItem_'+nextItemId+'">${quantity}:</label>');
		var newQuantity = $(' <input type="number" min="1"  class="form-control" id="pr_'+requestId+'_prItem_'+nextItemId+'_quantity" name="pr_'+requestId+'_prItem_'+nextItemId+'_quantity" placeholder="Enter Quantity" />');
		quantityContainer.append(newLabelForQuantity).append(newQuantity);
		newPurchaseItem.append(itemContainer);
		newPurchaseItem.append(quantityContainer);
		
		var removeButton = $('<button id="pr_'+requestId+'_removeItem_'+nextItemId+'" class="btn btn-danger remove-item btn-default" >${removePurchaseItem}</button>');
		newPurchaseItem.append(removeButton);
		
		var newDivider = $('<hr id="pr_'+requestId+'_prItemPanel_'+nextItemId+'" style="border-width: 1px; border-color: #8fabd8;">');
		newPurchaseItem.append(newDivider);
		
		$('#add_item_'+requestId).before(newPurchaseItem);
		
		$('#pr_'+requestId+'_itemCounter').val(nextItemId);
		
		$("#pr_"+requestId+"_removeItem_"+nextItemId).click(function(e) {
			e.preventDefault();
			var fields = this.id.split("_");
			var item = "#pr_"+fields[1]+"_prItem_"+fields[3];
			var divider ="#pr_"+fields[1]+"_prItemPanel_" + fields[3];
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