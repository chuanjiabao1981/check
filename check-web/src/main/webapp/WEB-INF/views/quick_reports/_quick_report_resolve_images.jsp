<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<div class="pull-right ace-thumbnails">
	<c:forEach items="${quick_report_resolve_images}" var="image">
		<spring:url    value="/check-data/${image.getName('normal')}.jpg" 		var="urlImage"></spring:url>
		<spring:url    value="/check-data/${image.getName('thumbnail')}.jpg"	var="urlThumbnailImage"></spring:url>
		<a href="${urlImage}" data-rel="colorbox" class="cboxElement">
			<img  width="36" height="36" src="${urlThumbnailImage}">
		</a>
	</c:forEach>
</div>