package com.check.v3.dto;

import com.check.v3.domain.CheckPoint;

public class CheckPointDTO {

	private Long id;
	private String name;
	
	public CheckPointDTO(CheckPoint checkPoint)
	{
		id 		= checkPoint.getId();
		name	= checkPoint.getName();
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
