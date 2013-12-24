package com.check.v3.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class QuickReportResolveRequestDTO {

	private Long					id;
	@NotEmpty
	private String 					description;
	
	
    private List<Long>				neededdeleteImagesId;

	
	public Long getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Long> getNeededdeleteImagesId() {
		return neededdeleteImagesId;
	}
	public void setNeededdeleteImagesId(List<Long> neededdeleteImagesId) {
		this.neededdeleteImagesId = neededdeleteImagesId;
	}
	
}
