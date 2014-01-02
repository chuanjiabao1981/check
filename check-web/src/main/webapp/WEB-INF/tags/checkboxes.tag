<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="items" 	 required="true" type="java.util.List" %>
<%@attribute name="label"    required="true" type="java.lang.String"%>
<%@attribute name="value"    required="true" type="java.lang.String"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="required" required="false" type="java.lang.Boolean"%>
<spring:bind path="${path}">
	<div class="form-group">
		<c:forEach items="${items}" var="item">
			<c:forEach items="${status.actualValue}" var="i">
				<c:if test="${item[value] == i[value]}">
					<c:set var="checked" value="checked" />
				</c:if> 
			</c:forEach>
			<form:checkbox path="${path}" label="${item[label]}" value="${item[value]}" checked="${checked}"/>
			<c:set var="checked" value="" />
		</c:forEach>
	</div>
</spring:bind>
