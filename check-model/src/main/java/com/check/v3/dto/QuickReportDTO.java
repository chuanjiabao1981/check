package com.check.v3.dto;

import org.joda.time.DateTime;

import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportLevel;
import com.check.v3.domain.QuickReportState;

public class QuickReportDTO {
	
	private Long					id;
	private UserDTO 				submitter;
	private UserDTO 				responsiblePerson;
	private OrganizationDTO			organizationDTO;
	private DateTime				deadLine;
	private QuickReportLevel		level;
	private QuickReportState		state;
	
	
	public QuickReportDTO(QuickReport quickReport)
	{
		this.id 					= quickReport.getId();
		this.submitter				= new UserDTO(quickReport.getSubmitter());
		this.responsiblePerson		= new UserDTO(quickReport.getResponsiblePerson());
		this.organizationDTO 		= new OrganizationDTO(quickReport.getOrganization(),false);
		this.deadLine 				= quickReport.getDeadline();
		this.level					= quickReport.getLevel();
		this.state 					= quickReport.getState();
	}


	public Long getId() {
		return id;
	}


	public UserDTO getSubmitter() {
		return submitter;
	}


	public UserDTO getResponsiblePerson() {
		return responsiblePerson;
	}


	public OrganizationDTO getOrganizationDTO() {
		return organizationDTO;
	}


	public DateTime getDeadLine() {
		return deadLine;
	}


	public QuickReportLevel getLevel() {
		return level;
	}


	public QuickReportState getState() {
		return state;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setSubmitter(UserDTO submitter) {
		this.submitter = submitter;
	}


	public void setResponsiblePerson(UserDTO responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}


	public void setOrganizationDTO(OrganizationDTO organizationDTO) {
		this.organizationDTO = organizationDTO;
	}


	public void setDeadLine(DateTime deadLine) {
		this.deadLine = deadLine;
	}


	public void setLevel(QuickReportLevel level) {
		this.level = level;
	}


	public void setState(QuickReportState state) {
		this.state = state;
	}

	
	
}
