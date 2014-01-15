<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="_message.jsp" %>
<spring:url    value="/organizations/${organization_id}/quick_reports/new" var="urlNewQuickReports"></spring:url>
<%@include file="../share/_message.jsp" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" />
<a href="${urlNewQuickReports}" class="btn btn-sm btn-primary">${buttonAdd}</a>
<br/>
<br/>

<c:if test="${not empty quick_reports}">
<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>${labelQuickReportDescription}</th>
			<th>${labelQuickReportSubmiiter}</th>
			<th>${textAction}</th>
		</tr> 
    </thead>
	<tbody>
		<c:forEach items="${quick_reports}" var="quick_report">
			<spring:url    value="/quick_reports/${quick_report.id}/edit" var="urlEditQuickReport"></spring:url>
			<spring:url	   value="/quick_reports/${quick_report.id}" 	  var="urlDestroyQuickReport"></spring:url>
			<spring:url    value="/quick_reports/${quick_report.id}" 	  var="urlShowQuickReport"></spring:url>
			<c:set 		   var="css_quick_report_id" 	value="quick_report_${quick_report.id}" />
			
			<tr>
				<td><a href="${urlShowQuickReport}">${quick_report.description}</a></td>
				<td>${quick_report.submitter.name}</td>
				<td>
					<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
						<a class="btn btn-xs btn-success" href="${urlEditQuickReport}">
							<i class="icon-edit bigger-120"></i>
						</a>
						<a onclick="document.getElementById('${css_quick_report_id}').submit();" class="btn btn-xs btn-danger">
						  	<i class="icon-trash bigger-120"></i>
						</a>
						<form:form  id="${css_quick_report_id}" action="${urlDestroyQuickReport}" method="DELETE"  >
        				</form:form>
					</div>
				</td>
			 </tr>
		</c:forEach>
	</tbody>
</table>
	
</c:if>