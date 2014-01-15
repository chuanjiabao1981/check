package com.check.v3.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="check_images")
@DiscriminatorValue("check_template_report_item_image")
public class CheckTemplateReportItemImage extends CheckImage{

	private static final long serialVersionUID = -5361683846248345877L;
	
	@ManyToOne
    @JoinColumn(name="check_template_report_item_id")
	private CheckTemplateReportItem checkTemplateReportItem;

	public CheckTemplateReportItem getCheckTemplateReportItem() {
		return checkTemplateReportItem;
	}

	public void setCheckTemplateReportItem(
			CheckTemplateReportItem checkTemplateReportItem) {
		this.setCheckTemplateReportItem(checkTemplateReportItem, true);
	}
	
	public void setCheckTemplateReportItem(CheckTemplateReportItem checkTemplateReportItem, boolean add) {
		this.checkTemplateReportItem = checkTemplateReportItem;
		if(null != checkTemplateReportItem && add) {
			checkTemplateReportItem.addImage(this, false);
		}
	}

	public boolean equals(Object object)
	{
		if (object == this){
			return true;
		}
		if ((object == null) || ! (object instanceof CheckTemplateReportItemImage)){
			return false;
		}
		
		final CheckTemplateReportItemImage other = (CheckTemplateReportItemImage)object;
		
		if ((other.getId()!=null) && (this.getId() !=null)){
			return other.getId().equals(this.getId());
		}
		return false;
	}
}
