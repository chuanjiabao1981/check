<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<jsp:directive.page contentType="text/html;charset=UTF-8" /> 
<spring:url    value="/quick_report/${quick_report_id}/quick_report_resolves" var="urlCreateQuickReportResolve"></spring:url>
<jsp:include page="_form.jsp">
	   <jsp:param name="post_url" value="${urlCreateQuickReportResolve}" />
</jsp:include>
