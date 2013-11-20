<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<spring:message code="label_organization_name" var="labelOrganizationName" />
<spring:message code="button_save" var="buttonSave"/>

<form:form modelAttribute="organization"  id="organization" name="organization" method="post" action="${param.post_url}">
		<form:label path="name">
			${labelOrganizationName}*
		</form:label>
		<form:input path="name"/>
		<div>
            <form:errors path="name" cssClass="error" />
		</div>
        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">${buttonSave}</span>
        </button> 
</form:form>