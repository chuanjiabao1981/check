package com.check.v3.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "check_templates")
public class CheckTemplate extends BaseEntity{

	private static final Logger logger = LoggerFactory.getLogger(CheckTemplate.class);

	private static final long serialVersionUID = -16541616454525383L;
	
    @Column(name = "name")
    @NotEmpty
	private String name;

    @OneToMany(mappedBy = "checkTemplate", cascade=CascadeType.ALL,orphanRemoval=true)
    private Set<CheckPoint> checkPoints 	= new HashSet<CheckPoint>();
    
    
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<CheckPoint> getCheckPoints() {
		return checkPoints;
	}

	public void setCheckPoints(Set<CheckPoint> checkPoints) {
		this.checkPoints = checkPoints;
	}
    
	public CheckPoint addCheckPoint(CheckPoint checkPoint)
	{
		return addCheckPoint(checkPoint,true);
	}
	public CheckPoint addCheckPoint(CheckPoint checkPoint,boolean set)
	{
		if (checkPoint == null){
			logger.trace("add null checkPoint to check Template");
			return checkPoint;
		}
		if (this.getCheckPoints().contains(checkPoint)){
			this.getCheckPoints().add(checkPoint);
		}else{
			this.getCheckPoints().add(checkPoint);
		}
		if (set){
			checkPoint.setCheckTemplate(this,false);
		}

		return checkPoint;
	}
	
	public CheckPoint buildCheckPoint()
	{
		CheckPoint checkPoint = new CheckPoint();
		checkPoint.setDepartment(this.getDepartment());
		this.addCheckPoint(checkPoint);
		return checkPoint;
	}
	
	
}
