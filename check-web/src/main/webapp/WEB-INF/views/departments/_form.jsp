<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<spring:message code="label_department_name" var="labelDepartmentName" />

<form:form modelAttribute="department"  id="department" name="department" method="post" action="${param.formUrl}">
		<form:label path="name">
            ${labelDepartmentName }* 
        </form:label>
        <form:input path="name" />
        <div>
            <form:errors path="name" cssClass="error" />
        </div>
        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">Save</span>
        </button> 
</form:form>
