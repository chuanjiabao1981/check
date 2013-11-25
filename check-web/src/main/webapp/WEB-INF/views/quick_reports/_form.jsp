<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<spring:message code="button_save" 			var="buttonSave"/>

<spring:message code="label_quick_report_level" 			 var="labelQuickReportLevel" />
<spring:message code="label_quick_report_responsible_person" var="labelQuickReportPerson" />
<spring:message code="label_quick_report_dead_line" 		 var="labelQuickReportDeadLine" />
<spring:message code="label_quick_report_description"        var="labelQuickReportDescription"/>


<form:form modelAttribute="quick_report"  id="quick_report" name="quick_report" method="post" action="${param.post_url}">
		<form:label path="level">
			${labelQuickReportLevel}*
		</form:label>
		<form:select path="level">
	         <form:options items="${level.text}"  />
	    </form:select>
		<div>
            <form:errors path="level" cssClass="error" />
		</div>
		<form:label path="responsiblePerson">
			${labelQuickReportPerson}
		</form:label>
		<form:select path="responsiblePerson">
			<form:options items="${responsiblePersons}" />
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
		
        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">${buttonSave}</span>
        </button> 
</form:form>