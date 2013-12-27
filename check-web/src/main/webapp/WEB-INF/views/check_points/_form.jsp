<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>


<%@include file="../share/_message.jsp" %>


<form:form modelAttribute="checkPoint"  id="checkPoint" name="checkPoint" method="post" action="${param.post_url}">
		<form:label path="name">
			${labelCheckPointName}*
		</form:label>
		<form:input path="name"/>
		<div>
            <form:errors path="name" cssClass="error" />
		</div>
        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">${buttonSave}</span>
        </button> 
</form:form>