<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${errorMsg !=null}">
		<div class="alert alert-danger">
			<strong>Error!</strong> ${errorMsg}
		</div>
	</c:when>
	<c:when test="${successMsg !=null}">
		<div class="alert alert-success">
			<strong>Success!</strong> ${successMsg}
		</div>
	</c:when>
</c:choose>