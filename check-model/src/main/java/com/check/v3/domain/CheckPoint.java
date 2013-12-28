package com.check.v3.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "check_points")
public class CheckPoint extends BaseEntity{

	private static final long serialVersionUID = 8446041535331722636L;
	
	@Column(name = "name")
	@NotEmpty
	private String name;
	@ManyToOne
    @JoinColumn(name="check_template_id")
	private CheckTemplate checkTemplate;
	
	
	
	public String getName() {
		return name;
	}
	public CheckTemplate getCheckTemplate() {
		return checkTemplate;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCheckTemplate(CheckTemplate checkTemplate) {
		setCheckTemplate(checkTemplate,true);
	}
	
	
	public void setCheckTemplate(CheckTemplate checkTemplate,boolean add)
	{
		this.checkTemplate = checkTemplate;
		if (checkTemplate != null && add){
			this.checkTemplate.addCheckPoint(this,false);
		}
	}
	

}
