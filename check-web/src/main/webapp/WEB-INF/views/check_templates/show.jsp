<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@include file="../share/_message.jsp" %>

<spring:url    value="/check_templates/${check_template_id}/check_points/new" var="urlNewCheckPoint"></spring:url>

<a href="${urlNewCheckPoint}">${buttonAdd}</a>

${checkTemplate.name}

<table>
	<thead>
		<tr>
			<th>Name</th>
			<th>Delete</th>
			<th>Edit</th>
		</tr> 
    </thead>
    <tbody>
    	<c:forEach items="${check_template.listCheckPoints}" var="checkPoint">
    		<spring:url    value="/check_points/${checkPoint.id}/edit"  var="urlEditCheckPoint"></spring:url>
    		<spring:url    value="/check_points/${checkPoint.id}"  		var="urlDestroyCheckPoint"></spring:url>
    		<tr>
				<td>${checkPoint.name}</td>
				<td>
					<form:form  action="${urlDestroyCheckPoint}" method="DELETE">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">${buttonDestroy}</span>
        				</button> 
        			</form:form>
				
				</td>
				<td>
					<form:form  action="${urlEditCheckPoint}" method="GET">
						 <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            				<span class="ui-button-text">${buttonEdit}</span>
        				</button> 
        			</form:form>
				
				</td>
			 </tr>
    	</c:forEach>
    </tbody>
</table>
