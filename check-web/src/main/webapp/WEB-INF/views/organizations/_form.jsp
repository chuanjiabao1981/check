<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<%@include file="../share/_message.jsp" %>
<form:form modelAttribute="organization"  id="organization" name="organization" method="post" action="${param.post_url}" class="form-horizontal" role="form">
		<t:input path="name" label="${labelOrganizationName}"/>
		<form:label path="checkTemplates">${labelOrganizationCheckTemplates}</form:label>
		<t:checkboxes path="checkTemplates" items="${checkTemplates}" label="name" value="id"/>
	    <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">${buttonSave}</span>
        </button> 
</form:form>