<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@include file="../share/_message.jsp" %>



<form:form modelAttribute="quick_report_resolve"  
 		  id="quick_report_resolve" 
 		  name="quick_report_resolve" 
 		  method="post" 
 		  enctype="multipart/form-data"
 		  class="form-horizontal" 
 		  role="form" 
 		  action="${param.post_url}">
 		<t:input  path="description" 		label="${labelQuickReportResolveDescription}"/>
		
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