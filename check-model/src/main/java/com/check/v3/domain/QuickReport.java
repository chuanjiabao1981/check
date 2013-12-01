package com.check.v3.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="quick_reports")
@NamedQueries({
	@NamedQuery(name="QuickReport.findAllByOrganizationId",query="select distinct u from User u where u.department.id = :id and u.role in :roles")
})
public class QuickReport extends BaseEntity {

	private static final Logger logger = LoggerFactory.getLogger(QuickReport.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 5794345832867208189L;

	@ManyToOne
    @JoinColumn(name="submitter_id")
	private User 				submitter;
	
	@ManyToOne
    @JoinColumn(name="responsible_person_id")
	private User				responsiblePerson = null;
	
	@ManyToOne
    @JoinColumn(name="organization_id")
	@NotNull
	private Organization		organization;
	
	@Column(name = "dead_line")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(iso=ISO.DATE)
	private DateTime 		 	deadline;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    @NotNull
    private QuickReportLevel 	level = QuickReportLevel.HIGH;
    
	private String				description;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    @NotNull
	private QuickReportState    state  		= QuickReportState.OPENED;
    
	@OneToMany(mappedBy = "quickReport", cascade={CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    private List<QuickReportImage> images 	= new ArrayList<QuickReportImage>();
	
	

	public User getSubmitter() {
		return submitter;
	}
	public Organization getOrganization() {
		return organization;
	}
	
	public DateTime getDeadline() {
		return deadline;
	}
	

	public QuickReportLevel getLevel() {
		return level;
	}
    @Column(name="description")
	@NotEmpty
	public String getDescription() {
		return description;
	}
	
	@Enumerated(EnumType.STRING)
	@NotNull
	public QuickReportState getState() {
		return state;
	}
	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
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
	public User getResponsiblePerson() {
		return responsiblePerson;
	}
	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}
	public void setDescription(String d)
	{
		this.description = d;
	}
	
	public List<QuickReportImage> getImages() {
		return images;
	}
	public void setImages(List<QuickReportImage> images) {
		this.images = images;
	}
	public QuickReportImage addImage(QuickReportImage image)
	{
		return addImage(image,true);
	}
	public QuickReportImage addImage(QuickReportImage image,boolean set)
	{
		if (image == null){
			logger.trace("add null image to quick report");
			return image;
		}
		
		if (this.getImages().contains(image)){
			this.getImages().set(this.getImages().indexOf(image), image);
		}else{
			this.getImages().add(image);
		}
		if (set){
			image.setQuickReport(this,false);
		}

		return image;
	}
	
	public void removeImage(QuickReportImage image)
	{
		this.getImages().remove(image);
		image.setQuickReport(null);
	}
	public boolean equals(Object object)
	{
		if (object == this){
			return true;
		}
		if ((object == null) || ! (object instanceof QuickReport)){
			return false;
		}
		
		final QuickReport other = (QuickReport)object;
		
		if ((other.getId()!=null) && (this.getId() !=null)){
			return other.getId().equals(this.getId());
		}
		return false;

	}
	

}
