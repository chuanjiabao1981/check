<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<jsp:directive.page contentType="text/html;charset=UTF-8" />
<spring:url    value="/departments/${department_id}/department_admins/new" var="urlNewDepartmentAdmin"></spring:url>
<a href="${urlNewDepartmentAdmin}">Add</a>
<c:if test="${not empty department_admins}">
	${fn:length(department_admins)}
	<table>
	<thead>
		<tr>
			<th>Account</th>
			<th>Name</th>
			<th>Delete</th>
			<th>Edit</th>
		</tr> 
    </thead>
	<tbody>
		<c:forEach items="${department_admins}" var="department_admin">
			<spring:url    value="/department_admins/${department_admin.id}" var="urlEditDepartment"></spring:url>
			<tr>
				<td>${department_admin.account}</td>
				<td>${department_admin.name}</td>
				<td>
					<form:form  action="${pageContext.request.contextPath}/department_admins/${department_admin.id}" method="DELETE">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">Delete</span>
        				</button> 
        			</form:form>
				
				</td>
				<td><a href="${urlEditDepartment}">Edit</a></td>
			 </tr>
		</c:forEach>
	</tbody>
	</table>
	
</c:if>