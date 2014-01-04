<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@include file="../share/_message.jsp" %>

<spring:url    value="/check_templates/${check_template_id}/check_points/new" var="urlNewCheckPoint"></spring:url>
<spring:url    value="/check_templates/" var="urlCheckTemplates"></spring:url>
<a href="${urlNewCheckPoint}" class="btn btn-sm btn-primary">${buttonAdd}</a>
<a href="${urlCheckTemplates}" class="btn btn-sm btn-success">${labelCheckTemplateIndex}</a>

<h4 class="header smaller lighter blue">${checkTemplate.name}</h4>
<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>${labelCheckPointName}</th>
			<th>${textAction}</th>
		</tr> 
    </thead>
    <tbody>
    	<c:forEach items="${checkTemplate.listCheckPoints}" var="checkPoint">
    		<spring:url    value="/check_points/${checkPoint.id}/edit"  var="urlEditCheckPoint"></spring:url>
    		<spring:url    value="/check_points/${checkPoint.id}"  		var="urlDestroyCheckPoint"></spring:url>
    		<c:set 		   var="css_check_point_id" 	value="check_point_${checkPoint.id}" />
    		
    		<tr>
				<td>${checkPoint.name}</td>
				<td>
					<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
						<a class="btn btn-xs btn-success" href="${urlEditCheckPoint}">
							<i class="icon-edit bigger-120"></i>
						</a>
						<a onclick="document.getElementById('${css_check_point_id}').submit();" class="btn btn-xs btn-danger">
						  	<i class="icon-trash bigger-120"></i>
						</a>
						<form:form  id="${css_check_point_id}" action="${urlDestroyCheckPoint}" method="DELETE"  >
        				</form:form>
					</div>
				</td>
			 </tr>
    	</c:forEach>
    </tbody>
</table>
