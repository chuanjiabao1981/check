<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>


<spring:message code="label_user_edit" 		var="labelUserEdit" />
<spring:message code="label_user_name" 		var="labelUserName" />
<spring:message code="label_user_account" 	var="labelUserAccount" />
<spring:message code="label_user_password"  var="labelUserPassword" />
<spring:message code="label_user_role"		var="labelUserRole" />

<spring:message code="button_save" 			var="buttonSave"/>

    <form:form modelAttribute="user" id="userEditForm" method="post" action="${param.post_url}">

        <c:if test="${not empty message}">
            <div id="message" class="${message.type}">${message.message}</div>
        </c:if>
		<form:label path="account">
            ${labelUserAccount }* 
        </form:label>
        <form:input path="account" />
        <div>
            <form:errors path="account" cssClass="error" />
        </div>
        <form:label path="name">
            ${labelUserName }* 
        </form:label>
        <form:input path="name" />
        <div>
            <form:errors path="name" cssClass="error" />
        </div>

        
        
	    <form:label path="password">${labelUserPassword }*</form:label>
	    <form:password path="password" />
	    <div>
            <form:errors path="password" cssClass="error" />
        </div>
	    <form:label path="role">${labelUserRole}*</form:label>
	    <form:select path="role">
	         <form:options items="${roles}" />
	    </form:select>
	    
	    <form:label path="organizations">sssss</form:label>
	    <form:checkboxes items="${organizations}" path="organizations" id="organizations" itemLabel="name" itemValue="id" name="organizations[]"/>
	    
        <form:hidden path="version" />
        <div></div>
        
        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">Save</span>
        </button> 
                      
    </form:form>