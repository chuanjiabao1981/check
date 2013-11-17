<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<jsp:directive.page contentType="text/html;charset=UTF-8" /> 
<spring:url    value="/departments" var="urlDepartments"></spring:url>
<jsp:include page="_form.jsp">
	    <jsp:param name="formUrl" value="${urlDepartments}" />
</jsp:include>