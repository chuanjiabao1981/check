<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@include file="_message.jsp" %>

<spring:url    value="/quick_reports/${quick_report.id}/edit" var="urlEditQuickReport"></spring:url>
<spring:url	   value="/quick_reports/${quick_report.id}/quick_report_resolves/new" var="urlNewQuickReportResolve"/>
<form:form  action="${urlEditQuickReport}" method="GET">
	<button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
     	<span class="ui-button-text">${buttonEdit}</span>
    </button> 
</form:form>
<form:form  action="${urlNewQuickReportResolve}" method="GET">
	<button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
     	<span class="ui-button-text">${buttonResolve}</span>
    </button> 
</form:form>
<table>
	<tr>
		<td>
			${labelQuickReportDescription}
		</td>
		<td>
			${quick_report.description}
		</td>
	</tr>
	<tr>
		<td>
			${labelQuickReportSubmiiter}
		</td>
		<td>
			${quick_report.submitter.name}
		</td>
	</tr>
	<tr>
		<td>
			${labelQuickReportCreatedAt}
		</td>
		<td>
			${quick_report.createdAt}
		</td>
	</tr>
	<c:forEach items="${quick_report.images}" var="image">
		<spring:url    value="/check-data/${image.getName('normal')}.jpg" var="urlImage"></spring:url>
	
		<tr>
			<td>
				${labelQuickReportImage}
			</td>
			<td>
				<img src="${urlImage}"/>
			</td>
		</tr>
	</c:forEach>
</table>
<table>
	<c:forEach items="${quick_report.resolves}" var="resolve">
		<spring:url value="/quick_report_resolves/${resolve.id}/edit" var="urlEditQuickReportResolve"/>
		<spring:url value="/quick_report_resolves/${resolve.id}"      var="urlDestroyQuickReportResolve"/>
		<form:form  action="${urlEditQuickReportResolve}" method="GET">
			<button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
     			<span class="ui-button-text">${buttonEdit}</span>
    		</button> 
		</form:form>
		<form:form  action="${urlDestroyQuickReportResolve}" method="DELETE">
			<button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
     			<span class="ui-button-text">${buttonDestroy}</span>
    		</button> 
		</form:form>
		<tr>
			<td>${labelQuickReportResolveDescription}</td>
			<td>${resolve.description}</td>
		</tr>
		<c:forEach items="${resolve.images}" var="image">
			<spring:url value="/check-data/${image.getName('normal')}.jpg" var="urlImage"></spring:url>
			<tr>
				<td>${labelQuickReportResolveImage}</td>
				<td><img src="${urlImage}"></td>
			</tr>		
		</c:forEach>
	
	</c:forEach>
</table>
${quick_report.resolves.size() }
