package com.check.v3.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.check.v3.domain.util.BaseEntityComparer;
import com.check.v3.domain.util.CheckImageUtil;
import com.google.common.collect.Lists;

@Entity
@Table(name="check_template_report_items")

public class CheckTemplateReportItem extends BaseEntity{

	private static final long serialVersionUID = -5320172590211268812L;
	private static final Logger logger = LoggerFactory.getLogger(CheckTemplateReportItem.class);

	@ManyToOne
	@JoinColumn(name="check_template_report_id")
	private CheckTemplateReport checkTemplateReport;
	
	/*
	 * Here there is a question. The check point is a static field, for each template checking, 
	 * there would be a checkTemplateItem for the checkPoint. 
	 * */
	@OneToOne
	@NotEmpty
	@NotNull
	private CheckPoint checkPoint;
	
	@Column(name = "has_problem")
	@NotEmpty
	private boolean isIssue = true;
	
	@ManyToOne
    @JoinColumn(name="responsible_person_id")
	private User responsiblePerson = null;
	
	@Column(name = "dead_line")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(iso=ISO.DATE)
	private DateTime deadline;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "level")
    @NotNull
    private IssueLevel level = IssueLevel.HIGH;

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	@NotNull
	private IssueStatus status = IssueStatus.OPENED;
	
	@OneToMany(mappedBy = "checkTemplateReportItem", cascade=CascadeType.ALL,orphanRemoval=true)
    private Set<CheckTemplateReportItemImage> images = new HashSet<CheckTemplateReportItemImage>();
	@OneToMany(mappedBy = "checkTemplateReportItem", cascade={CascadeType.REMOVE})
	private Set<CheckTemplateReportItemResolve> resolves = new HashSet<CheckTemplateReportItemResolve>();
	
	@Column(name="resolve_num")
	private Integer resolveNum=0;
	
	@Column(name="description")
	private String description = "";

	public CheckTemplateReport getCheckTemplateReport() {
		return checkTemplateReport;
	}

	public void setCheckTemplateReport(CheckTemplateReport checkTemplateReport) {
		this.setCheckTemplateReport(checkTemplateReport, true);
	}
	
	public void setCheckTemplateReport(CheckTemplateReport checkTemplateReport, boolean add)
	{
		this.checkTemplateReport = checkTemplateReport;
		if(null != this.checkTemplateReport && add) {
			this.checkTemplateReport.addCheckTemplateReportItem(this, false);
		}
	}

	public CheckPoint getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(CheckPoint checkPoint) {
		this.checkPoint = checkPoint;
	}

	public boolean isIssue() {
		return isIssue;
	}

	public void setIssue(boolean isIssue) {
		this.isIssue = isIssue;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public DateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(DateTime deadline) {
		this.deadline = deadline;
	}

	public IssueLevel getLevel() {
		return level;
	}

	public void setLevel(IssueLevel level) {
		this.level = level;
	}

	public IssueStatus getStatus() {
		return status;
	}

	public void setStatus(IssueStatus status) {
		this.status = status;
	}

	public Set<CheckTemplateReportItemImage> getImages() {
		return images;
	}

	public void setImages(Set<CheckTemplateReportItemImage> images) {
		this.images = images;
	}
	public CheckTemplateReportItemImage addImage(CheckTemplateReportItemImage image)
	{
		return addImage(image,true);
	}
	public CheckTemplateReportItemImage addImage(CheckTemplateReportItemImage image,boolean set)
	{
		if (image == null){
			logger.trace("add null image to check template item");
			return image;
		}
		image.setSubmitter(this.checkTemplateReport.getSubmitter());
		this.getImages().add(image);
		
		if (set){
			image.setCheckTemplateReportItem(this,false);
		}

		return image;
	}
	
	public CheckImage buildCheckTemplateReportItemImage() {
		CheckTemplateReportItemImage checkTemplateReportItemImage = new CheckTemplateReportItemImage();
		checkTemplateReportItemImage.setSubmitter(this.getCheckTemplateReport().getSubmitter());
		checkTemplateReportItemImage.setDepartment(this.getDepartment());
		checkTemplateReportItemImage.setName(CheckImageUtil.BuildImageName(checkTemplateReportItemImage));
		this.addImage(checkTemplateReportItemImage);
		return checkTemplateReportItemImage;
	}
	
	public void removeImage(CheckTemplateReportItemImage image)
	{
		this.getImages().remove(image);
		image.setCheckTemplateReportItem(null);
	}
	
	public CheckTemplateReportItemImage removeImage(Long id) {
		for(CheckTemplateReportItemImage image : this.images){
			if (id == image.getId()){
				this.removeImage(image);
				return image;
			}
		}
		return null;
	}
	
	public CheckTemplateReportItemImage getImage(Long id)
	{
		for(CheckTemplateReportItemImage image :this.images){
			if (id == image.getId()){
				return image;
			}
		}
		return null;
	}
	
	public Set<CheckTemplateReportItemResolve> getResolves() {
		return resolves;
	}

	public void setResolves(Set<CheckTemplateReportItemResolve> resolves) {
		this.resolves = resolves;
	}

	public Integer getResolveNum() {
		return resolveNum;
	}

	public void setResolveNum(Integer resolveNum) {
		this.resolveNum = resolveNum;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CheckTemplateReportItemResolve addResolve(CheckTemplateReportItemResolve resolve) {
		return this.addResolve(resolve);
	}

	public CheckTemplateReportItemResolve addResolve(CheckTemplateReportItemResolve resolve, boolean set) {
		if(null == resolve) {
			logger.info("add null resolve to resolves");
			return null;
		}
		resolve.setSubmitter(this.getCheckTemplateReport().getSubmitter());
		this.resolves.add(resolve);
		if(set) {
			resolve.setCheckTemplateReportItem(this, false);
		}
		return resolve;
	}
	
	public CheckTemplateReportItemResolve buildCheckTemplateReportItemResolve() {
		CheckTemplateReportItemResolve resolve = new CheckTemplateReportItemResolve();
		resolve.setDepartment(this.getDepartment());
		this.addResolve(resolve);
		return resolve;
	}
	
	public void incResolveNum()
	{
		resolveNum++;
	}
	public void decResolveNum()
	{
		resolveNum--;
		if (resolveNum <=0){
			resolveNum =0;
		}
	}
	
	public void removeResolve(CheckTemplateReportItemResolve resolve)
	{
		this.getResolves().remove(resolve);
		resolve.setCheckTemplateReportItem(null);
	}
	
	public CheckTemplateReportItemResolve removeResolve(Long id)
	{
		for(CheckTemplateReportItemResolve resolve : this.resolves) {
			if(id == resolve.getId()) {
				return resolve;
			}
		}
		
		return null;
	}
	
	public List<CheckTemplateReportItemResolve> getListResolves()
	{
		 ArrayList<CheckTemplateReportItemResolve> l = Lists.newArrayList(resolves.iterator());
		 Collections.sort(l,new BaseEntityComparer());
		 return l;
	}
	
	public boolean equals(Object object)
	{
		if (object == this){
			return true;
		}
		if ((object == null) || ! (object instanceof CheckTemplateReportItem)){
			return false;
		}
		
		final CheckTemplateReportItem other = (CheckTemplateReportItem)object;
		
		if ((other.getId()!=null) && (this.getId() !=null)){
			return other.getId().equals(this.getId());
		}
		return false;
	}
}
