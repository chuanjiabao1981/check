		<table class="table table-striped table-bordered table-hover">
			<tr>
				<td>
					${labelQuickReportDescription}
				</td>
				<td>
					${quick_report.description}
				</td>
			</tr>
			<tr>
				<td>
					${labelQuickReportPerson}
				</td>
				<td>
					<c:if test="${not empty quick_report.responsiblePerson}">
						${quick_report.responsiblePerson.name}
					</c:if>
				</td>
			</tr>
			<tr>
				<td>
					${labelQuickReportSubmiiter}
				</td>
				<td>
					${quick_report.submitter.name}
				</td>
			</tr>
			<tr>
				<td>
					${labelQuickReportLevel}
				</td>
				<td>
					${quick_report.level.text}
				</td>
			</tr>
			<tr>
				<td>
					${labelQuickReportCreatedAt}
				</td>
				<td>
					${quick_report.createdAt}
				</td>
			</tr>
			<tr>
	  			<td>
	  				${labelQuickReportImage}
	  			</td>
	  			<td>
					<div class="row-fluid">
						<ul class="ace-thumbnails">
							<c:forEach items="${quick_report.listImages}" var="image">
								<spring:url    value="/check-data/${image.getName('normal')}.jpg" 		var="urlImage"></spring:url>
								<spring:url    value="/check-data/${image.getName('thumbnail')}.jpg"	var="urlThumbnailImage"></spring:url>
									<a href="${urlImage}" data-rel="colorbox" class="cboxElement">
										<img alt="150x150" src="${urlThumbnailImage}">
									</a>
							</c:forEach>
						</ul>
					</div>
	  			</td>
			</tr>
		</table>