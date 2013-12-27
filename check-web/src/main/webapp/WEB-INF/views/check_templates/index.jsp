<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@include file="../share/_message.jsp" %>


<spring:url    value="/check_templates/new" var="urlNewCheckTemplates"></spring:url>

<jsp:directive.page contentType="text/html;charset=UTF-8" />
<a href="${urlNewCheckTemplates}">${buttonAdd}</a>
<c:if test="${not empty check_templates}">
	<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Submitter</th>
			<th>Delete</th>
			<th>Edit</th>
		</tr> 
    </thead>
	<tbody>
		<c:forEach items="${check_templates}" var="checkTemplate">
			<spring:url    value="/check_templates/${checkTemplate.id}/edit"  var="urlEditCheckTemplate"></spring:url>
			<spring:url	   value="/check_templates/${checkTemplate.id}" 	  var="urlDestroyCheckTemplate"></spring:url>
			<spring:url    value="/check_templates/${checkTemplate.id}"		  var="urlShowCheckTemplate"></spring:url>	
			<tr>
				<td><a href="${urlShowCheckTemplate}">${checkTemplate.name}</a></td>
				<td>
					<form:form  action="${urlDestroyCheckTemplate}" method="DELETE">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">${buttonDestroy}</span>
        				</button> 
        			</form:form>
				
				</td>
				<td>
					<form:form  action="${urlEditCheckTemplate}" method="GET">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">${buttonEdit}</span>
        				</button> 
        			</form:form>
				
				</td>
			 </tr>
		</c:forEach>
	</tbody>
</table>
	
</c:if>