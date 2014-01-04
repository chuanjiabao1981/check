<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<spring:url    value="/" var="defaultUrlCancel"></spring:url>
<c:set var="nameSave" 	value="${param.nameSave}" />
<c:set var="nameCancel" value="${param.nameCancel}" />
<c:set var="urlCancel"  value="${param.urlCancel}" />
<div class="clearfix form-actions">
	<div class="col-md-offset-3 col-md-9">
		<button class="btn btn-info" type="submit">
			<i class="icon-ok bigger-110"></i>
			${empty nameSave ? buttonSave : nameSave}
		</button>
		&nbsp; &nbsp; &nbsp;
		<a class="btn btn-gray" href="${empty urlCancel ? defaultUrlCancel : urlCancel}">
			<i class="icon-undo bigger-110"></i>
				${empty nameCancel ? buttonCancel : nameCancel}
		</a>
	</div>
</div>