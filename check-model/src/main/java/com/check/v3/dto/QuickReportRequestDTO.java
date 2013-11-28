package com.check.v3.dto;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReportLevel;
import com.check.v3.domain.User;

public class QuickReportRequestDTO {
	
	private Long					id;
	
	private Long					responsiblePersonId;
	
	@NotNull
	private Long					organizationId;
	
	@JsonSerialize(using = JodaDateTimeSerializer.class)
	private DateTime				deadline;
	
	@NotNull
	private QuickReportLevel		level;
	
	@NotEmpty
	private String 					description;
	
    @JsonIgnore
	private Organization			organization;
    @JsonIgnore
    private User					responsiblePerson;
	
	public Long getId() {
		return id;
	}
	public Long getResponsiblePersonId() {
		return responsiblePersonId;
	}
	
	public Long getOrganizationId() {
		return organizationId;
	}
	public QuickReportLevel getLevel() {
		return level;
	}
	public String getDescription() {
		return description;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setResponsiblePersonId(Long responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public void setLevel(QuickReportLevel level) {
		this.level = level;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Organization getOrganization() {
		return organization;
	}
	public User getResponsiblePerson() {
		return responsiblePerson;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
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
	
}
