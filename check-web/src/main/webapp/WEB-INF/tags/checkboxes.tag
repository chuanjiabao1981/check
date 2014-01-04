<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="path" 		 required="true" type="java.lang.String"%>
<%@attribute name="items" 	 	 required="true" type="java.util.List" %>
<%@attribute name="label"    	 required="true" type="java.lang.String"%>
<%@attribute name="value"    	 required="true" type="java.lang.String"%>
<%@attribute name="labelName"    required="true" type="java.lang.String"%>

<%@attribute name="cssClass" required="false" type="java.lang.String"%>
<%@attribute name="required" required="false" type="java.lang.Boolean"%>
<spring:bind path="${path}">
	<div class="form-group">
		<label class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right for="${path}">${labelName}</label>
		<div class="col-xs-12 col-sm-5">
		<c:forEach items="${items}" var="item">
			<c:set var="checked" value="false" />
			<c:forEach items="${status.actualValue}" var="i" >
				<c:if test="${item[value] == i[value]}">
					<c:set var="checked" value="checked" />
				</c:if> 
			</c:forEach>
				<label>
					<input id="${path}${index.index+1}"  name ="${path}" type="checkbox" class="ace"  ${checked} value="${item[value]}"/>
                    <span class="lbl">   ${item[label]}</span>
				</label>
				<br/>
			<c:set var="checked" value="" />
		</c:forEach>
		</div>
	</div>
	<input type="hidden" name="_${path}" value="on"/>			
	
</spring:bind>
