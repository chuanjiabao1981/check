package com.check.v3.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.check.v3.domain.util.BaseEntityComparer;
import com.google.common.collect.Lists;

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
    
	@ManyToMany
    @JoinTable(name="organization_check_templates", 
                joinColumns={@JoinColumn(name="check_template_id")}, 
                inverseJoinColumns={@JoinColumn(name="organization_id")})
    private Set<Organization> organizations = new HashSet<Organization>();
    
	/*
	 * Modified by chensong.zjd, add relation between checkTemplate and checkTemplateReports
	 * */
	
	@OneToMany(mappedBy = "checkTemplate", cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<CheckTemplateReport> checkTemplateReports = new HashSet<CheckTemplateReport>();
	
    
	public Set<CheckTemplateReport> getCheckTemplateReports() {
		return checkTemplateReports;
	}

	public void setCheckTemplateReports(
			Set<CheckTemplateReport> checkTemplateReports) {
		this.checkTemplateReports = checkTemplateReports;
	}
	
	public CheckTemplateReport addCheckTemplateReport(CheckTemplateReport checkTemplateReport) {
		return addCheckTemplateReport(checkTemplateReport, true);
	}
	
	public CheckTemplateReport addCheckTemplateReport(CheckTemplateReport checkTemplateReport, boolean set) {
		if (null == checkTemplateReport){
			logger.trace("add null checkTemplateReport to checkTemplate");
			return checkTemplateReport;
		}
		
		if (!this.getCheckTemplateReports().contains(checkTemplateReport)){
			this.getCheckTemplateReports().add(checkTemplateReport);
		}
		
		if (set) {
			checkTemplateReport.setCheckTemplate(this, false);
		}
		
		return checkTemplateReport;
	}
	
	public CheckTemplateReport buildCheckTemplateReport(Organization organization) {
		CheckTemplateReport checkTemplateReport = new CheckTemplateReport();
		checkTemplateReport.setDepartment(this.getDepartment());
		checkTemplateReport.setOrganization(organization);
		this.addCheckTemplateReport(checkTemplateReport);
		
		return checkTemplateReport;
	}
	
	public List<CheckTemplateReport> getListCheckTemplateReports()
	{
		 ArrayList<CheckTemplateReport> l = Lists.newArrayList(this.checkTemplateReports.iterator());
		 Collections.sort(l,new BaseEntityComparer());
		 return l;
	}
	
	/*
	 * modify end.
	 * */
	

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
	
	public List<CheckPoint> getListCheckPoints()
	{
		 ArrayList<CheckPoint> l = Lists.newArrayList(checkPoints.iterator());
		 Collections.sort(l,new BaseEntityComparer());
		 return l;

	}

	public Set<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}
}