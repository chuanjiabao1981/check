package com.check.v3.dto;

import org.joda.time.DateTime;

import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportLevel;
import com.check.v3.domain.QuickReportState;

public class QuickReportDTO {
	
	private Long					id;
	private Long					submitterId;
	private String					submitterName;
	private Long					responsiblePeronId;
	private String					responsiblePersonName;
	private Long					organizationId;
	private String					organizationName;
	private DateTime				deadLine;
	private QuickReportLevel		level;
	private QuickReportState		state;
	
	
	public QuickReportDTO(QuickReport quickReport)
	{
		this.id 					= quickReport.getId();
		this.deadLine 				= quickReport.getDeadline();
		this.level					= quickReport.getLevel();
		this.state 					= quickReport.getState();
	}


	
	
}
