<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<spring:message code="invoice.po" var="po" />
<label for="po">${po}</label>
<select class="form-control" id="po" name="purchaseOrder">
	<option value=""></option>
	<c:forEach var="purchaseOrder" items="${poList}">
		<option value="${purchaseOrder.id}">${purchaseOrder.number}</option>
	</c:forEach>
</select>
