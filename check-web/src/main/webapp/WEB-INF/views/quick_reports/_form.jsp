<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<%@include file="_message.jsp" %>



<form:form modelAttribute="quick_report"  
 		  id="quick_report" 
 		  name="quick_report" 
 		  method="post" enctype="multipart/form-data" action="${param.post_url}">
		<form:label path="level">
			${labelQuickReportLevel}
		</form:label>
		<form:select path="level">
	         <form:options items="${levels}"  />
	    </form:select>
		<div>
            <form:errors path="level" cssClass="error" />
		</div>
		<form:label path="responsiblePerson">
			${labelQuickReportPerson}
		</form:label>
		<form:select path="responsiblePerson">
		    <form:option value="" label="${labelPleaseSelect}"/>
			<form:options items="${responsiblePersons}" itemValue="id" itemLabel="name"/>
		</form:select>
		<div>
            <form:errors path="level" cssClass="error" />
		</div>
		<form:label path="deadline">
			${labelQuickReportDeadLine}
		</form:label>
		<form:input path="deadline"/>
		<div>
            <form:errors path="deadline" cssClass="error" />
		</div>
		<form:label path="description">
			${labelQuickReportDescription}
		</form:label>
		<form:input path="description"/>
		<div>
            <form:errors path="description" cssClass="error" />
		</div>
		
		<c:forEach items="${quick_report.images}" varStatus="i">
		  	<form:label path="images[${i.index}].file">tupian</form:label>
            <form:input path="images[${i.index}].file" type="file"/>
    	</c:forEach>
		
		<form:hidden path="organization" value="${quick_report.organization.id}" />
			
        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">${buttonSave}</span>
        </button> 
</form:form>