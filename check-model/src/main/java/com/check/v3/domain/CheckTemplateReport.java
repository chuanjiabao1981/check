package com.check.v3.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.check.v3.domain.util.BaseEntityComparer;
import com.google.common.collect.Lists;


@Entity
@Table(name = "check_template_reports")
public class CheckTemplateReport extends BaseEntity {

	private static final long serialVersionUID = 5440050549125739601L;
	private static final Logger logger = LoggerFactory.getLogger(CheckTemplateReport.class);

	
	@ManyToOne
    @JoinColumn(name="check_template_id")
	@NotNull
	@NotEmpty
	private CheckTemplate checkTemplate;
	
	@ManyToOne
    @JoinColumn(name="organization_id")
	@NotNull
	@NotEmpty
	private Organization organization;
	
	@ManyToOne
    @JoinColumn(name="submitter_id")
	@NotEmpty
	private User submitter;
	
	@OneToMany(mappedBy = "CheckTemplateReport", cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<CheckTemplateReportItem> checkTemplateReportItems = new HashSet<CheckTemplateReportItem>();

	
	public Set<CheckTemplateReportItem> getCheckTemplateReportItems() {
		return checkTemplateReportItems;
	}

	public void setCheckTemplateReportItems(
			Set<CheckTemplateReportItem> checkTemplateReportItems) {
		this.checkTemplateReportItems = checkTemplateReportItems;
	}

	public CheckTemplate getCheckTemplate() {
		return this.checkTemplate;
	}
	
	public void setCheckTemplate(CheckTemplate checkTemplate) {
		setCheckTemplate(checkTemplate,true);
	}
	
	public void setCheckTemplate(CheckTemplate checkTemplate, boolean add)
	{
		this.checkTemplate = checkTemplate;
		
		if(null != this.checkTemplate && add) {
			this.checkTemplate.addCheckTemplateReport(this, false);
		}
	}
	
	public User getSubmitter() {
		return submitter;
	}

	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}
	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	public CheckTemplateReportItem addCheckTemplateReportItem(CheckTemplateReportItem checkTemplateReportItem) {
		return this.addCheckTemplateReportItem(checkTemplateReportItem, true);
	}
	
	public CheckTemplateReportItem addCheckTemplateReportItem(CheckTemplateReportItem checkTemplateReportItem, boolean set) {
		if (null == checkTemplateReportItem){
			logger.trace("add null checkTemplateReportItem to checkTemplateReport");
			return checkTemplateReportItem;
		}
		
		if (!this.getCheckTemplateReportItems().contains(checkTemplateReportItem)){
			this.getCheckTemplateReportItems().add(checkTemplateReportItem);
		}
		
		if (set) {
			checkTemplateReportItem.setCheckTemplateReport(this, false);
		}
		
		return checkTemplateReportItem;
	}
	
	public CheckTemplateReportItem buildCheckTemplateReportItem(CheckPoint checkPoint) {
		CheckTemplateReportItem checkTemplateReportItem = new CheckTemplateReportItem();
		checkTemplateReportItem.setCheckPoint(checkPoint);
		this.addCheckTemplateReportItem(checkTemplateReportItem);
		return checkTemplateReportItem;
	}
	
	public List<CheckTemplateReportItem> getListCheckTemplateReportItems()
	{
		 ArrayList<CheckTemplateReportItem> l = Lists.newArrayList(this.checkTemplateReportItems.iterator());
		 Collections.sort(l,new BaseEntityComparer());
		 return l;
	}
}