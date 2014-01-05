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
			<a class="menu-toggler" id="menu-toggler" href="#">
				<span class="menu-text"></span>
			</a>
			<div class="main-content">
				<%@include file="_breadcrumbs.jsp" %>
				<div class="page-content">
					<%@include file="_page_header.jsp" %>
					<div class="row">
						<!--div class="col-xs-12"-->
							<tiles:insertAttribute name="body"/>
						<!-- /div-->
					</div>
				</div>
			</div><!-- /.main-content -->
      	</div>
      	
      	<!-- basic scripts -->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='${pageContext.request.contextPath}/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
			<script type="text/javascript">
 				window.jQuery || document.write("<script src='${pageContext.request.contextPath}/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
			</script>
		<![endif]-->
		
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${pageContext.request.contextPath}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->
		
		<script src="${pageContext.request.contextPath}/assets/js/jquery.colorbox-min.js"></script>

		<!--[if lte IE 8]>
		  <script src="${pageContext.request.contextPath}/assets/js/excanvas.min.js"></script>
		<![endif]-->
		
		<script src="${pageContext.request.contextPath}/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/chosen.jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/fuelux/fuelux.spinner.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/date-time/bootstrap-datepicker.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/date-time/bootstrap-timepicker.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/date-time/moment.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/date-time/daterangepicker.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/bootstrap-colorpicker.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery.knob.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery.autosize.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery.inputlimiter.1.3.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/jquery.maskedinput.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/bootstrap-tag.min.js"></script>
		
		<!-- ace scripts -->

		<script src="${pageContext.request.contextPath}/assets/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/ace.min.js"></script>
		
		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			jQuery(function($) {
				var colorbox_params = {
					reposition:true,
					scalePhotos:true,
					scrolling:false,
					previous:'<i class="icon-arrow-left"></i>',
					next:'<i class="icon-arrow-right"></i>',
					close:'&times;',
					current:'{current} of {total}',
					maxWidth:'100%',
					maxHeight:'100%',
					onOpen:function(){
						document.body.style.overflow = 'hidden';
					},
					onClosed:function(){
						document.body.style.overflow = 'auto';
					},
					onComplete:function(){
						$.colorbox.resize();
					}
				};

			$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
			$("#cboxLoadingGraphic").append("<i class='icon-spinner orange'></i>");//let's add a custom loading icon
		});
		</script>

		<tiles:insertAttribute name="footer" ignore="true"/> 
		
   	  </body>
</html>