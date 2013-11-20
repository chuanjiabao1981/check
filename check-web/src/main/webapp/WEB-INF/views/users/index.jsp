<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" />

<spring:url    value="/users/new" var="urlNewUser"></spring:url>

<a href="${urlNewUser}">Add</a>

<c:if test="${not empty users}">
	${fn:length(users)}
	<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Account</th>
			<th>Delete</th>
			<th>Edit</th>
		</tr> 
    </thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<spring:url    value="/users/${user.id}/edit" var="urlEditUser"></spring:url>
			<spring:url	   value="/users/${user.id}" var="urlDelUser"></spring:url>
			<tr>
				<td>${user.name}</td>
				<td>${user.account}</td>
				<td>
					<form:form  action="${urlDelUser}" method="DELETE">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">Delete</span>
        				</button> 
        			</form:form>
				
				</td>
				<td>
					<form:form  action="${urlEditUser}" method="GET">
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