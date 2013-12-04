<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@include file="_message.jsp" %>

<spring:url    value="/quick_reports/${quick_report.id}/edit" var="urlEditQuickReport"></spring:url>

<form:form  action="${urlEditQuickReport}" method="GET">
	<button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
     	<span class="ui-button-text">${buttonEdit}</span>
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
		<spring:url    value="${image.getImageUri('normal')}" var="urlImage"></spring:url>
	
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