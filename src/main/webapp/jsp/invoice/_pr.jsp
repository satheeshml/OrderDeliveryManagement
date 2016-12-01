<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<spring:message code="invoice.delivered.item" var="item" />
<spring:message code="invoice.delivered.quantity" var="quantity" />

<c:forEach var="pr" items="${prList}">
	<hr id="purchaseRequestPanel_${pr.id}" style="border-width: 4px; border-color: #8fabd8;">
	<c:set var="removeRequestField" value="remove_${pr.id}" />
	<div id="request_${pr.id}">
		<c:forEach var="pi" items="${pr.purchaseItems}">
			<c:set var="itemField" value="item_${pr.id}_${pi.id}_${pi.item.id}" />
			<c:set var="qtyField" value="quantity_${pr.id}_${pi.id}_${pi.item.id}" />
			<c:set var="removeItemField" value="item_${pr.id}_${pi.id}" />
			<div id="purchaseItem_${pr.id}_${pi.id}">
				<c:if test="${pi.pendingQuantity != 0}">

					<div class="form-group">
						<label for="${itemField}">${item}:</label> <input type="text" class="form-control"
							id="${itemField}" name="${itemField}"
							value="${pi.item.customerPart}-${pi.item.supplierPart}-${pi.item.name}" disabled="disabled" />
					</div>
					<div class="form-group">
						<input type="hidden" name="ordered_quantity_${pr.id}_${pi.id}_${pi.item.id}"
							value="${pi.quantity}" /> <label for="${qtyField}">${quantity}:</label> <input type="number"
							min="1" max="${pi.pendingQuantity}" class="form-control" id="${qtyField}" name="${qtyField}"
							value="${pi.pendingQuantity}" required/>
					</div>
					<button id="${removeItemField}" class="btn btn-danger remove-item btn-default">Remove
						Item</button>
					<hr id="purchaseRequestItem_${pr.id}_${pi.id}"
						style="border-width: 3px; border-color: #8fabd8;">
				</c:if>
			</div>
		</c:forEach>
		<button id="${removeRequestField}" class="btn btn-danger remove-request btn-default">Remove
			Request</button>
	</div>
</c:forEach>
