package com.check.v3.dto;

import java.util.List;


public class QuickReportPageDTO {

	
	private int totalPages;
	private int currentPage;
	private long totalRecords;
	
	private List<QuickReportDTO> quickReports;

	
	public QuickReportPageDTO()
	{
		
	}
	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public List<QuickReportDTO> getQuickReports() {
		return quickReports;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public void setQuickReports(List<QuickReportDTO> quickReports) {
		this.quickReports = quickReports;
	}
	

}
