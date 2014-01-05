<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@include file="../share/_message.jsp" %>
<spring:url    value="/quick_reports/${quick_report.id}/edit" var="urlEditQuickReport"></spring:url>
<spring:url	   value="/quick_reports/${quick_report.id}/quick_report_resolves/new" var="urlNewQuickReportResolve"/>
<div class="col-xs-12">
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
</div>


<!-- div class="row"-->
<div class="col-xs-12">
	<div class="timeline-container">
		<div class="timeline-label">
			<span class="label label-primary arrowed-in-right label-lg">
				<b>${labelQuickReportResolve}</b>
			</span>
		</div>
		<div class="timeline-items">
			<c:forEach items="${quick_report.resolves}" var="resolve">
				<spring:url value="/quick_report_resolves/${resolve.id}/edit" var="urlEditQuickReportResolve"/>
				<spring:url value="/quick_report_resolves/${resolve.id}"      var="urlDestroyQuickReportResolve"/>
				<div class="timeline-item clearfix">
					<div class="timeline-info">
						<i class="timeline-indicator icon-star btn btn-warning no-hover green"></i>
					</div>
					<div class="widget-box transparent">
						<div class="widget-header widget-header-small">
							<h5 class="smaller">
								<a href="#" class="blue">${resolve.submitter.name}</a>
								<span class="grey">${labelQuickReportResolveHandle}</span>
							</h5>
							<span class="widget-toolbar no-border">
								<i class="icon-time bigger-110"></i>
								16:22
							</span>
							<span class="widget-toolbar">
								<a href="#" data-action="collapse">
									<i class="icon-chevron-up"></i>
								</a>
							</span>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<div class="clearfix">
									<div class="pull-left">
										${resolve.description}
									</div>
									<c:set var="quick_report_resolve_images" value="${resolve.listImages}" scope="request" />
									<jsp:include page="_quick_report_resolve_images.jsp"/>
								</div>
								<div class="space-6"></div>
								<div class="widget-toolbox clearfix">
									<div class="pull-right action-buttons">
										<a href="${urlEditQuickReportResolve}">
											<i class="icon-pencil blue bigger-125"></i>
										</a>

										<a onclick="document.getElementById('quick_report_resolve_${resolve.id}').submit();" href="#">
											<i class="icon-remove red bigger-125"></i>
										</a>
										<form:form  id="quick_report_resolve_${resolve.id}" action="${urlDestroyQuickReportResolve}" method="DELETE"  >
        								</form:form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<!--/div-->
