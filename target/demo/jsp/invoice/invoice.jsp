<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
	<spring:message code="invoice.number" var="number" />
	<spring:message code="invoice.customer" var="customer" />

	<spring:message code="invoice.po" var="po" />
	<spring:message code="invoice.add.delivered.item" var="addDeliveredItem" />
	<spring:message code="invoice.remove.delivered.item"
		var="removeDeliveredItem" />

	<spring:message code="invoice.delivered.item" var="item" />
	<spring:message code="invoice.delivered.quantity" var="quantity" />

	<div class="container">
		<h1>Generate Invoice</h1>
		<form action="purchaseOrder" method="POST">
			<div class="form-group" id="customerPanel">
				<label for="customer">${customer}</label> <select
					class="form-control" id="customer" name="customer">
					<option value=""></option>
					<c:forEach var="customer" items="${customerList}">
						<option value="${customer.id}">${customer.name}</option>
					</c:forEach>
				</select>
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div>
</body>
<script type="text/javascript">
	function addNewPurchaseRequest(next) {
		var newPurchaseRequest = $('<div id="pr_'+next+'" class="form-group"></div>')
		var newLabelForMode = $('<label for="pr_'+next+'_mode">${mode}:</label>')
		var newMode = $(' <select class="form-control" id="pr_'+next+'_mode" name="pr_'+next+'_mode"><c:forEach var="mode" items="${modeList}"> <option value="${mode.id}">${mode.mode}</option> </c:forEach></select>');

		var count = $("#count").val();
		console.log(count);
		if (next == 1 || count == 0) {
			$("#purchaseRequestPanel").after(newPurchaseRequest);
		} else {
			var previousRequest = $("#pr_" + (next - 1));
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
			
		});
	}
	$(document).ready(function() {
		var next = 0;
		
		
		$("#customer").change(function(e) {
			e.preventDefault();
			console.log("Customer Changed");
				
			var customerId = $(this).val();
			if(customerId == null || customerId==""){
				return false;
			}
			console.log("Changed CustomerId : "+customerId);
			
			var newPurchaseOder = $('<div id="poPanel" class="form-group"></div>')
			var newLabelForPO = $('<label for="po">${po}</label>');
			
			var newPurchaseRequest = $('<div id="prPanel" class="form-group"></div>')
			var newLabelForPR = $('<label for="pr">Purchase Request</label>');
			
			//selectForPO.append(blankOptionForPO);
			console.log("Making Ajax call");
			$.ajax({url: "poByCustomer?customerId="+customerId, success: function(result){
				newPurchaseOder.append(newLabelForPO);
				newPurchaseOder.append($(result));
				$("#customerPanel").after(newPurchaseOder);
				console.log(result);
				$("#po").change(function(e) {
					e.preventDefault();
					console.log("Purchase Order Changed");
					var purchaseOrderId = $(this).val();
					console.log("Changed purchaseOrderId : "+purchaseOrderId);
					$.ajax({url: "prByPo1?poId="+purchaseOrderId, success: function(result){
						console.log(result)
						//$("#po").after(newPurchaseRequest);
						//newPurchaseRequest.append(newLabelForPR);
						//newPurchaseRequest.append($(result));
			        }});
					
				});
		    }});
			
		});
	});
</script>
</html>