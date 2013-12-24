<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<%@include file="_message.jsp" %>



<form:form modelAttribute="quick_report_resolve"  
 		  id="quick_report_resolve" 
 		  name="quick_report_resolve" 
 		  method="post" enctype="multipart/form-data" action="${param.post_url}">
		<form:label path="description">
			${labelQuickReportDescription}
		</form:label>
		<form:input path="description"/>
		<div>
            <form:errors path="description" cssClass="error" />
		</div>
		
		<c:forEach items="${quick_report_resolve.listImages}" varStatus="i">
            <label for="image_files[]">tupian</label>
            <form:input path="listImages[${i.index}].file" type="file"/>
            <c:if test="${not empty quick_report_resolve.listImages[i.index].id}">
                 delete<form:checkbox path="listImages[${i.index}].del"/>
            </c:if>
            <div>
            	 <form:errors path="listImages[${i.index}].file" cssClass="error" />
            </div>
        </c:forEach>
		
			
        <button type="submit" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
            <span class="ui-button-text">${buttonSave}</span>
        </button> 
</form:form>