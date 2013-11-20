<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" />

<spring:url    value="/organizations/new" var="urlNewOrganization"></spring:url>

<a href="${urlNewOrganization}">Add</a>

<c:if test="${not empty organizations}">
	${fn:length(organizations)}
	<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Delete</th>
			<th>Edit</th>
		</tr> 
    </thead>
	<tbody>
		<c:forEach items="${organizations}" var="organization">
			<spring:url    value="/organizations/${organization.id}" var="urlEditDepartment"></spring:url>
			<spring:url	   value="/organizations/${organization.id}" var="urlDelDepartment"></spring:url>
			<tr>
				<td>${organization.name}</td>
				<td>
					<form:form  action="${urlDelDepartment}" method="DELETE">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">Delete</span>
        				</button> 
        			</form:form>
				
				</td>
				<td>
					<form:form  action="${urlEditDepartment}" method="GET">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">Edit</span>
        				</button> 
        			</form:form>
				</td>
			 </tr>
		</c:forEach>
	</tbody>
	</table>
	
</c:if>