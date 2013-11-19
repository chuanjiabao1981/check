<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<spring:message code="label_department_admin_name" var="labelDepartmentAdminName" />
<spring:message code="label_department_admin_account" var="labelDepartmentAdminAccount"/>
<spring:message code="label_department_admin_password" var="labelDepartmentAdminPassword"/>

<form:form modelAttribute="department_admin"  id="department_admin" name="department_admin" method="post" action="${param.post_url}">
		<form:label path="account">
			${labelDepartmentAdminAccount}*
		</form:label>
		<form:input path="account"/>
		<div>
            <form:errors path="account" cssClass="error" />
		</div>
		<form:label path="name">
            ${labelDepartmentAdminName}* 
        </form:label>
        <form:input path="name" />
        <div>
            <form:errors path="name" cssClass="error" />
        </div>
        <form:label path="password">
                    ${labelDepartmentAdminPassword}* 
        </form:label>
         <form:input path="password" />
         <div>
            <form:errors path="password" cssClass="error" />
        </div>
        <form:hidden path="department" value="${department_id}" />
        
        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">Save</span>
        </button> 
</form:form>