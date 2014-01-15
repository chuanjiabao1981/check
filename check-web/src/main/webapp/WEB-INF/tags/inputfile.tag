<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="delPath"  required="false" type="java.lang.String"%>
<%@attribute name="labelName" required="false" type="java.lang.String"%>
<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="required" required="false" type="java.lang.Boolean"%>
<%@attribute name="canBeDeleted" required="false" type="java.lang.Boolean" %>

<%@include file="../views/share/_message.jsp" %>

<spring:bind path="${path}">
	<div class="form-group ${status.error ? 'has-error' : '' }">
		<label class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right" for="${path}">${labelName}</label>
		<div class="col-xs-12 col-sm-5">
			<span class="input-icon input-icon-right">
	            <form:input path="${path}" cssClass="${empty cssClass ? 'width-90' : cssClass}" type="file"/>
	        </span>
	        <span>
	        	 <c:if test="${canBeDeleted}">
	            	<form:checkbox path="${delPath}"/> ${buttonDestroy}
	            </c:if>
	        </span>
        </div>
        <c:if test="${status.error}">
                <div class="help-block col-xs-12 col-sm-reset inline">${status.errorMessage}</div>
        </c:if>
	</div>
</spring:bind>
