<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<jsp:directive.page contentType="text/html;charset=UTF-8" /> 
<spring:url value="/logout" var="logoutUrl"/>
	  
<h1>
	Hello world!  
</h1>
<shiro:authenticated>
	Hello, <shiro:principal/>, how are you today?
    <a href="${logoutUrl}">退出</a>
	
</shiro:authenticated>
<P>  The time on the server is ${serverTime}. </P>
