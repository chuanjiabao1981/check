package com.check.v3.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="check_images")
@DiscriminatorValue("quick_report_image")
public class QuickReportImage extends CheckImage{

	
	@ManyToOne
    @JoinColumn(name="quick_report_id")
	private QuickReport quickReport;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7799486339748915395L;

	public QuickReport getQuickReport() 
	{
		return quickReport;
	}

	public void setQuickReport(QuickReport quickReport) {
		setQuickReport(quickReport,true);
	}
	public void setQuickReport(QuickReport quickReport,boolean add)
	{
		this.quickReport = quickReport;
		if (quickReport != null && add){
			this.quickReport.addImage(this,false);
		}
	}

	public boolean equals(Object object)
	{
		if (object == this){
			return true;
		}
		if ((object == null) || ! (object instanceof QuickReportImage)){
			return false;
		}
		
		final QuickReportImage other = (QuickReportImage)object;
		
		if ((other.getId()!=null) && (this.getId() !=null)){
			return other.getId().equals(this.getId());
		}
		return false;
	}


}
