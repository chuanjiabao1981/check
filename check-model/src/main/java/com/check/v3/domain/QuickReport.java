package com.check.v3.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name="reports")
@DiscriminatorValue("quick_report")
public class QuickReport extends Report implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5416218183521951530L;
	
	private User 				submitter;
	private User 				solver;
	private DateTime 		 	deadline;
	private QuickReportLevel 	level;
	private String				desc;
	private QuickReportState    state;
	private Organization		organization;
	private Department 			department;

}
