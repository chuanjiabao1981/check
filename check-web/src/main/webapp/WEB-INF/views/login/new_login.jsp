<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:url value="/login" var="loginUrl"/>

<!-- TODO::i18n -->
<div>
	<form id="loginform" name="loginform" method="post" action="${loginUrl}">
		<fieldset>
			<label class="block clearfix">
				<span class="block input-icon input-icon-right">
					<input name="username" id="username" type="text" class="form-control" placeholder="Username" />
					<i class="icon-user"></i>
				</span>
			</label>
			<label class="block clearfix">
				<span class="block input-icon input-icon-right">
					<input name="password" id="password" type="password" class="form-control" placeholder="Password" />
					<i class="icon-lock"></i>
				</span>
			</label>
			<div class="space"></div>
			<div class="clearfix">
				<label class="inline">
					<input type="checkbox" class="ace" />
					<span class="lbl"> Remember Me</span>
				</label>

				<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
					<i class="icon-key"></i>
					Login
				</button>
			</div>
			<div class="space-4"></div>
  		</fieldset>
	</form>

	
</div>