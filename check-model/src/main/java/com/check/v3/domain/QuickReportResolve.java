package com.check.v3.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="resolves")
@DiscriminatorValue("quick_report_resolve")
public class QuickReportResolve extends Resolve{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2367372042841847575L;
	
	@ManyToOne
    @JoinColumn(name="quick_report_id")
	@NotNull
	private QuickReport quickReport;


	public QuickReport getQuickReport() {
		return quickReport;
	}


	public void setQuickReport(QuickReport quickReport) {
		setQuickReport(quickReport,true);
	}
	

	public void setQuickReport(QuickReport quickReport,boolean add)
	{
		this.quickReport = quickReport;
		if (quickReport != null && add){
			this.quickReport.addResolve(this,false);
		}

	}
	
	
	

}
