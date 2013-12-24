package com.check.v3.dto;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.domain.util.BaseEntityComparer;
import com.google.common.collect.Lists;

public class QuickReportDetailDTO {

	private QuickReportDTO quickReport;
	private List<QuickReportResolveDTO> quickReportResolves = new LinkedList<QuickReportResolveDTO>();
	
	
	public QuickReportDetailDTO(QuickReport q)
	{
		quickReport = new QuickReportDTO(q);
		if (q.getResolves() != null && q.getResolves().size() != 0){
			List<QuickReportResolve> s = Lists.newArrayList(q.getResolves().iterator());
			Collections.sort(s,new BaseEntityComparer());
			for(QuickReportResolve quickReportResolve:s){
				QuickReportResolveDTO r = new QuickReportResolveDTO(quickReportResolve);
				this.quickReportResolves.add(r);
			}
		}

	}
	public QuickReportDTO getQuickReport() {
		return quickReport;
	}
	public List<QuickReportResolveDTO> getQuickReportResolves() {
		return quickReportResolves;
	}
	public void setQuickReport(QuickReportDTO quickReport) {
		this.quickReport = quickReport;
	}
	public void setQuickReportResolves(
			List<QuickReportResolveDTO> quickReportResolves) {
		this.quickReportResolves = quickReportResolves;
	}
	
	
}
