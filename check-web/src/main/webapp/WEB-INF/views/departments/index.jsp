<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" /> 
<spring:url    value="/departments" var="urlEditDepartment"></spring:url>
<spring:url    value="/departments" var="urlDepartmentAdminIndex"></spring:url>
<c:if test="${not empty departments}"> 
<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Delete</th>
			<th>Edit</th>
			<th>Department Admins</th>
		</tr> 
    </thead>
	<tbody>
		<c:forEach items="${departments}" var="department">
			<tr>
				<td>${department.name}</td>
				<td>
					<form:form  action="${pageContext.request.contextPath}/departments/${department.id}" method="DELETE">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">Delete</span>
        				</button> 
        			</form:form>
        		</td>
        		<td><a href="${urlEditDepartment}/${department.id}/edit">Edit</a></td>
        		<td><a href="${urlDepartmentAdminIndex}/${department.id}/department_admins">Department Admins</a></td>
			 </tr>
		</c:forEach>
	</tbody>
</table>
</c:if>