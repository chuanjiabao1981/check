package com.check.v3.dto;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.check.v3.domain.CheckImage;
import com.check.v3.domain.QuickReportResolve;

public class QuickReportResolveDTO {
	private Long 					id;
	private Long					submitterId;
	private String					submitterName;
	private String 					description;
	private List<CheckImageInfo> 	images = new LinkedList<CheckImageInfo>();



	public QuickReportResolveDTO(QuickReportResolve quickReportResolve)
	{
		BeanUtils.copyProperties(quickReportResolve, this,new String[]{"images"});
		
		this.setSubmitterId(quickReportResolve.getSubmitter().getId());
		this.setSubmitterName(quickReportResolve.getSubmitter().getName());
		if (quickReportResolve.getImages() != null && quickReportResolve.getImages().size() != 0){
			for(CheckImage checkIamge:quickReportResolve.getImages()){
				CheckImageInfo checkImageInfo = new CheckImageInfo();
				checkImageInfo.setId(checkIamge.getId());
				checkImageInfo.setPath(checkIamge.getName());
				images.add(checkImageInfo);
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


	public void setId(Long id) {
		this.id = id;
	}


	public void setSubmitterId(Long submitterId) {
		this.submitterId = submitterId;
	}


	public void setSubmitterName(String submitterName) {
		this.submitterName = submitterName;
	}


	public String getDescription() {
		return description;
	}


	public List<CheckImageInfo> getImages() {
		return images;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setImages(List<CheckImageInfo> images) {
		this.images = images;
	}
	
	
	
}
