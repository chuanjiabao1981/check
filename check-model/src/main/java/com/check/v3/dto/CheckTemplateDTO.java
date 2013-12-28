package com.check.v3.dto;

import java.util.LinkedList;
import java.util.List;

import com.check.v3.domain.CheckPoint;
import com.check.v3.domain.CheckTemplate;

public class CheckTemplateDTO {

	private Long id;
	private String name;
	private List<CheckPointDTO> checkPoints = new LinkedList<CheckPointDTO>();
	
	public CheckTemplateDTO(CheckTemplate checkTemplate)
	{
		id 		= checkTemplate.getId();
		name 	= checkTemplate.getName();
		for(CheckPoint checkPoint:checkTemplate.getListCheckPoints()){
			CheckPointDTO checkPointDTO = new CheckPointDTO(checkPoint);
			checkPoints.add(checkPointDTO);
		}
	}
	public Long getId() {
		return id;
	}
	public List<CheckPointDTO> getCheckPoints() {
		return checkPoints;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCheckPoints(List<CheckPointDTO> checkPoints) {
		this.checkPoints = checkPoints;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
