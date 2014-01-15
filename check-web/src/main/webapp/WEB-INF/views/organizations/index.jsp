<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@include file="../share/_message.jsp" %>

<spring:url    value="/organizations/new" var="urlNewOrganization"></spring:url>
<a href="${urlNewOrganization}" class="btn btn-sm btn-primary">${buttonAdd}</a>
<br/>
<br/>
<c:if test="${not empty organizations}">
<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>${labelOrganizationName}</th>
			<th>${textAction}</th>
		</tr> 
    </thead>
	<tbody>
		<c:forEach items="${organizations}" var="organization">
			<spring:url    value="/organizations/${organization.id}" var="urlEditDepartment"></spring:url>
			<spring:url	   value="/organizations/${organization.id}" var="urlDelDepartment"></spring:url>
			<tr>
				<td>${organization.name}</td>
				<td>
				<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
					<a class="btn btn-xs btn-success" href="${urlEditDepartment}">
					<i class="icon-edit bigger-120"></i>
					</a>
					<a onclick="document.getElementById('organization_${organization.id}').submit();" class="btn btn-xs btn-danger">
						  	<i class="icon-trash bigger-120"></i>
					</a>
					<form:form  id="organization_${organization.id}" action="${urlDelDepartment}" method="DELETE"  >
        			</form:form>
				</div>
				</td>
			 </tr>
		</c:forEach>
	</tbody>
</table>
	
</c:if>