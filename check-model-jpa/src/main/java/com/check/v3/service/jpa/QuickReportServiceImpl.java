package com.check.v3.service.jpa;


import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.repository.QuickReportRepository;
import com.check.v3.service.QuickReportService;

@Service("quickReportService")
@Repository
@Transactional
public class QuickReportServiceImpl implements QuickReportService{

	@Resource
	private QuickReportRepository quickReportRepository;
	
	@Override
	@Transactional(readOnly=true)
	public QuickReport findById(Long id) {
		return quickReportRepository.findOne(id);
	}

	@Override
	public QuickReport save(QuickReport quickReport) {
		return quickReportRepository.save(quickReport);
	}

	@Override
	public void deleteById(Long id) {
		quickReportRepository.delete(id);
	}

	@Override
	public void delete(QuickReport quickReport) {
		quickReportRepository.delete(quickReport);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<QuickReport> findByOrganization(Organization organization,
			Pageable pageable) {
		return quickReportRepository.findByOrganization(organization, pageable);
	}

	
	
}
