<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="path" 	 	required="true" type="java.lang.String"%>
<%@attribute name="labelName" 	required="true" type="java.lang.String"%>
<%@attribute name="cssClass" 	required="false" type="java.lang.String"%>
<%@attribute name="items" 		required="true"  type="java.lang.Object"%>
<%@attribute name="itemValue" 	required="false" type="java.lang.String"%>
<%@attribute name="itemLabel" 	required="false" type="java.lang.String"%>
<spring:bind path="${path}">
	<div class="form-group ${status.error ? 'has-error' : '' }">
		<label class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right" for="${path}">${labelName}</label>
		<div class="col-xs-12 col-sm-5">
			<span class="block input-icon input-icon-right">
				<form:select path="${path}" class="col-xs-12 col-sm-5 width-100">
					<c:if test="${empty itemValue}">
						<form:options items="${items}"  cssClass="${empty cssClass ? 'width-100' : cssClass}"/>
					</c:if>
					<c:if test="${not empty itemValue}">
						<form:options items="${items}"  itemValue="${itemValue}" itemLabel="${itemLabel}" cssClass="${empty cssClass ? 'width-100' : cssClass}"/>
					</c:if>
	            </form:select>
	        </span>
        </div>
        <c:if test="${status.error}">
                <div class="help-block col-xs-12 col-sm-reset inline">${status.errorMessage}</div>
        </c:if>
	</div>
</spring:bind>
