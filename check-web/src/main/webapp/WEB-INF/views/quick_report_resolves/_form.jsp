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
        	<t:inputfile path="listImages[${i.index}].file" 
        				 delPath="listImages[${i.index}].del"
        				 labelName="${labelQuickReportImage}"
        				 canBeDeleted="${not empty quick_report_resolve.listImages[i.index].id ? true : false}"/>
        </c:forEach>

		<%@include file="../share/_button_save_cancel.jsp" %>
			
</form:form>