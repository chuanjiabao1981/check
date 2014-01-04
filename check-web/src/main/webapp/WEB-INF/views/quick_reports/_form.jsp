<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@include file="../share/_message.jsp" %>

<form:form modelAttribute="quick_report"  
 		  id="quick_report" 
 		  name="quick_report" 
 		  method="post" enctype="multipart/form-data" action="${param.post_url}"
 		  class="form-horizontal" 
 		  role="form">
 		<t:select path="level" 				labelName="${labelQuickReportLevel}" items="${levels}"/>
	 	<t:select path="responsiblePerson"  labelName="${labelPleaseSelect}" items="${responsiblePersons}" itemValue="id" itemLabel="name"/>
	 	<t:input  path="deadline" 		    label="${labelQuickReportDeadLine}"/>
	 	<t:input  path="description" 		label="${labelQuickReportDescription}"/>
		<c:if test="${empty quick_report.id}">
			<form:hidden path="organization" value="${organization_id}" />
		</c:if>
		
		<c:forEach items="${quick_report.listImages}" varStatus="i">
        	<t:inputfile path="listImages[${i.index}].file" 
        				 delPath="listImages[${i.index}].del"
        				 labelName="${labelQuickReportImage}"
        				 canBeDeleted="${not empty quick_report.listImages[i.index].id ? true : false}"/>
        </c:forEach>

		<%@include file="../share/_button_save_cancel.jsp" %>
</form:form>