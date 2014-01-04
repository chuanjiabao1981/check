<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@include file="../share/_message.jsp" %>
<spring:url    value="/check_templates/new" var="urlNewCheckTemplates"></spring:url>
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<a href="${urlNewCheckTemplates}" class="btn btn-sm btn-primary">${buttonAdd}</a>
<br/>
<br/>
<c:if test="${not empty check_templates}">
<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>${labelCheckTemplateName}</th>
			<th>${textAction}</th>
		</tr> 
    </thead>
	<tbody>
		<c:forEach items="${check_templates}" var="checkTemplate">
			<spring:url    value="/check_templates/${checkTemplate.id}/edit"  var="urlEditCheckTemplate"></spring:url>
			<spring:url	   value="/check_templates/${checkTemplate.id}" 	  var="urlDestroyCheckTemplate"></spring:url>
			<spring:url    value="/check_templates/${checkTemplate.id}"		  var="urlShowCheckTemplate"></spring:url>
			<c:set 		   var="css_check_tempalte_id" 	value="check_template_${organization.id}" />
			<tr>
				<td><a href="${urlShowCheckTemplate}">${checkTemplate.name}</a></td>
				<td>
					<div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
						<a class="btn btn-xs btn-success" href="${urlEditCheckTemplate}">
							<i class="icon-edit bigger-120"></i>
						</a>
						<a onclick="document.getElementById('${css_check_tempalte_id}').submit();" class="btn btn-xs btn-danger">
						  	<i class="icon-trash bigger-120"></i>
						</a>
						<form:form  id="${css_check_tempalte_id}" action="${urlDestroyCheckTemplate}" method="DELETE"  >
        				</form:form>
					</div>
				</td>
			 </tr>
		</c:forEach>
	</tbody>
</table>
	
</c:if>