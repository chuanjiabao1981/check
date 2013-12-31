<!DOCTYPE HTML>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	  
<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<html>
	  <head>
		  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    	  <meta http-equiv="X-UA-Compatible" content="IE=8" />
    	  
    	  <meta charset="utf-8" />
    	  
		  <meta name="description" content="Common form elements and layouts" />
		  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
		  
		 <%@include file="_css.jsp" %>

		 <%@include file="_js.jsp" %>
		
    	   
	  </head>
	  <body> 
		<tiles:insertAttribute name="header" ignore="true" /> 
		<tiles:insertAttribute name="menu" ignore="true" /> 
		
		<div class="main-container" id="main-container">
			<tiles:insertAttribute name="body"/>
      	</div>
      	
      	<!-- basic scripts -->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
			<script type="text/javascript">
 				window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
			</script>
		<![endif]-->
		
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="assets/js/excanvas.min.js"></script>
		<![endif]-->
		
		<tiles:insertAttribute name="footer" ignore="true"/> 
		
   	  </body>
</html>