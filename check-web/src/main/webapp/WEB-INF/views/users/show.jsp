<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<jsp:directive.page contentType="text/html;charset=UTF-8" /> 
    
	<spring:message code="label_user_info" var="labelUserInfo" />
	<spring:message code="label_user_name" var="labelUserName" />
	<spring:message code="label_user_account" var="labelUserAccount" />
    <spring:url value="/users" var="editUserUrl"/>    

    <h1>${labelUserInfo }</h1>

    <div id="userInfo">

        <c:if test="${not empty message}">
            <div id="message" class="${message.type}">${message.message}</div>
        </c:if>

        <table>
            <tr>
                <td>${labelUserName }</td>
                <td>${user.name}</td>
            </tr>
            <tr>
                <td>${labelUserAccount }</td>
                <td>${user.account}</td>
            </tr>   
                                                                          
        </table>         
       
        <a href="${editUserUrl}/${user.id}/edit">Edit user info</a>     
                      
    </div>

