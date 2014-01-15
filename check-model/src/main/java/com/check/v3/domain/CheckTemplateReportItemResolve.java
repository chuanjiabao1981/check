package com.check.v3.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="resolves")
@DiscriminatorValue("check_template_report_item_resolve")
public class CheckTemplateReportItemResolve extends Resolve{

	private static final long serialVersionUID = -9005951568649492816L;

	@ManyToOne
    @JoinColumn(name="check_template_report_item_id")
	@NotNull
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
			checkTemplateReportItem.addResolve(this, false);
		}
	}
}