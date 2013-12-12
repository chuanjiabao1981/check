package com.check.v3.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.service.exception.ImageTypeWrongException;

public interface QuickReportService {
	
	public QuickReport 			findById(Long id);
	public QuickReport			findByIdWithMedia(Long id);
	public QuickReport			findByIdWithMediaAndResolve(Long id);
	public QuickReport 			save(QuickReport quickReport) throws ImageTypeWrongException;
	public Page<QuickReport>	findByOrganization(Organization organization,Pageable pageable);
	public Page<QuickReport>	findAllByOrganizationWithMedia(Long organizationId,Pageable pageable);
	public void 	   			deleteById(Long id);
	public void 	   			delete(QuickReport quickReport);	
}
