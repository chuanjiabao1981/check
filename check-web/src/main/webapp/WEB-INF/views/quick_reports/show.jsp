<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@include file="_message.jsp" %>

<spring:url    value="/quick_reports/${quick_report.id}/edit" var="urlEditQuickReport"></spring:url>
<spring:url	   value="/quick_reports/${quick_report.id}/quick_report_resolves/new" var="urlNewQuickReportResolve"/>
<div class="col-xs-12 widget-container-span ui-sortable">
	<div class="widget-box">
		<div class="widget-header header-color-blue">
			<h5>${labelQuickReportDescription}</h5>
			<div class="widget-toolbar">
				<a href="#" data-action="collapse">
					<i class="1 bigger-125 icon-chevron-up"></i>
				</a>
			</div>
			<div class="widget-toolbar no-border">
				<a href="${urlNewQuickReportResolve}">
					<button class="btn btn-xs btn-light bigger">
						<i class="icon-wrench"></i>
						${buttonResolve}
					</button>
				</a>
				
				<a href="${urlEditQuickReport}">
					<button class="btn btn-xs btn-light bigger">
						<i class="icon-pencil"></i>
							${buttonEdit}
					</button>
				</a>
								
			</div>
		</div>
		<div class="widget-body">
			<div class="widget-main no-padding">
				<%@include file="_quick_report.jsp" %>
			</div>
		</div>
	</div>
</div>

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
		<c:forEach items="${resolve.listImages}" var="image">
			<spring:url value="/check-data/${image.getName('normal')}.jpg" var="urlImage"></spring:url>
			<tr>
				<td>${labelQuickReportResolveImage}</td>
				<td><img src="${urlImage}"></td>
			</tr>		
		</c:forEach>
	
	</c:forEach>
</table>
