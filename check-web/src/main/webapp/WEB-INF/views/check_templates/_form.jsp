<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<%@include file="../share/_message.jsp" %>


<form:form modelAttribute="checkTemplate"  id="checkTemplate" name="checkTemplate" method="post" action="${param.post_url}" class="form-horizontal">
		<t:input path="name" label="${labelCheckTemplateName}"/>
      	<%@include file="../share/_button_save_cancel.jsp" %>
</form:form>