package com.check.v3.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;

public interface QuickReportService {
	
	public QuickReport 			findById(Long id);
	public QuickReport 			save(QuickReport quickReport);
	public Page<QuickReport>	findByOrganization(Organization organization,Pageable pageable);
	public void 	   			deleteById(Long id);
	public void 	   			delete(QuickReport quickReport);	
}
