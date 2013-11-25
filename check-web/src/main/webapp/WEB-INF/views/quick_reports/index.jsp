<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:message code="button_add" 			var="buttonAdd"/>
<spring:message code="button_save"			var="buttonSave"/>
<spring:message code="button_destroy"		var="buttonDestroy"/>
<spring:message code="button_edit" 			var="buttonEdit"/>
<spring:url    value="/organizations/${organization_id}/quick_reports/new" var="urlNewQuickReports"></spring:url>

<jsp:directive.page contentType="text/html;charset=UTF-8" />
<a href="${urlNewQuickReports}">${buttonAdd}</a>
<c:if test="${not empty quick_reports}">
	${fn:length(quick_reports)}
	<table>
	<thead>
		<tr>
			<th>Desc</th>
			<th>Submitter</th>
			<th>Delete</th>
			<th>Edit</th>
		</tr> 
    </thead>
	<tbody>
		<c:forEach items="${quick_reports}" var="quick_report">
			<spring:url    value="/quick_reports/${quick_report.id}/edit" var="urlEditQuickReport"></spring:url>
			<spring:url	   value="/quick_reports/${quick_report.id}" 	  var="urlDestroyQuickReport"></spring:url>
			<tr>
				<td>${quick_report.description}</td>
				<td>${quick_report.submitter.name}</td>
				<td>
					<form:form  action="${urlDestroyQuickReport}" method="DELETE">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">${buttonDestroy}</span>
        				</button> 
        			</form:form>
				
				</td>
				<td>
					<form:form  action="${urlEditQuickReport}" method="EDIT">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">${buttonEdit}</span>
        				</button> 
        			</form:form>
				
				</td>
			 </tr>
		</c:forEach>
	</tbody>
</table>
	
</c:if>