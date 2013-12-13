package com.check.v3.dto;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;

import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportImage;
import com.check.v3.domain.QuickReportLevel;
import com.check.v3.domain.QuickReportState;

public class QuickReportDTO {
	
	public class CheckImageInfo{
		private Long id;
		private String path;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		
		
	}
	private Long					id;
	private Long					submitterId;
	private String					submitterName;
	private Long					responsiblePeronId;
	private String					responsiblePersonName;
	private Long					organizationId;
	private String					organizationName;
	@JsonSerialize(using = JodaDateTimeSerializer.class)
	private DateTime				deadline;
	private QuickReportLevel		level;
	private QuickReportState		state;
	private String					description;
	private List<CheckImageInfo> 	images = new LinkedList<CheckImageInfo>();

	public QuickReportDTO(QuickReport quickReport)
	{
		BeanUtils.copyProperties(quickReport, this,new String[]{"images"});

		this.setSubmitterId(quickReport.getSubmitter().getId());
		this.setSubmitterName(quickReport.getSubmitter().getName());
		if (quickReport.getResponsiblePerson() != null){
			this.setResponsiblePeronId(quickReport.getResponsiblePerson().getId());
			this.setResponsiblePersonName(quickReport.getResponsiblePerson().getName());
		}
		this.setOrganizationId(quickReport.getOrganization().getId());
		this.setOrganizationName(quickReport.getOrganization().getName());
		if (quickReport.getImages() != null){
			for(QuickReportImage image:quickReport.getImages()){
				CheckImageInfo c = new CheckImageInfo();
				c.setId(image.getId());
				c.setPath(image.getName());
				images.add(c);
			}
		}
	}


	public Long getId() {
		return id;
	}


	public Long getSubmitterId() {
		return submitterId;
	}


	public String getSubmitterName() {
		return submitterName;
	}


	public Long getResponsiblePeronId() {
		return responsiblePeronId;
	}


	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}


	public Long getOrganizationId() {
		return organizationId;
	}


	public String getOrganizationName() {
		return organizationName;
	}


	public DateTime getDeadline() {
		return deadline;
	}


	public QuickReportLevel getLevel() {
		return level;
	}


	public QuickReportState getState() {
		return state;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setSubmitterId(Long submitterId) {
		this.submitterId = submitterId;
	}


	public void setSubmitterName(String submitterName) {
		this.submitterName = submitterName;
	}


	public void setResponsiblePeronId(Long responsiblePeronId) {
		this.responsiblePeronId = responsiblePeronId;
	}


	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}


	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}


	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}


	public void setDeadline(DateTime deadline) {
		this.deadline = deadline;
	}


	public void setLevel(QuickReportLevel level) {
		this.level = level;
	}


	public void setState(QuickReportState state) {
		this.state = state;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<CheckImageInfo> getImages() {
		return images;
	}


	public void setImages(List<CheckImageInfo> images) {
		this.images = images;
	}


	
	
	


	
	
}
