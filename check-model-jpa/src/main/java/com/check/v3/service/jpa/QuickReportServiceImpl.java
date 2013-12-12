package com.check.v3.service.jpa;



import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.repository.QuickReportRepository;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.exception.ImageTypeWrongException;

@Service("quickReportService")
@Repository
@Transactional
public class QuickReportServiceImpl implements QuickReportService{

	
	@PersistenceContext 
	private EntityManager em;

	@Resource
	private QuickReportRepository quickReportRepository;
	
	@Override
	@Transactional(readOnly=true)
	public QuickReport findById(Long id) {
		return quickReportRepository.findOne(id);
	}

	@Override
	@Transactional
	public QuickReport save(QuickReport quickReport) throws ImageTypeWrongException {
		return quickReportRepository.save(quickReport);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		quickReportRepository.delete(id);
	}

	@Override
	@Transactional
	public void delete(QuickReport quickReport) {
		quickReportRepository.delete(quickReport);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<QuickReport> findByOrganization(Organization organization,
			Pageable pageable) {
		return quickReportRepository.findByOrganization(organization, pageable);
	}

	
	@Override
	@Transactional(readOnly=true)
	public QuickReport findByIdWithMedia(Long id) {
		return this.quickReportRepository.findByIdWithMedia(id);
	}

	@Override
	@Transactional(readOnly=true)
	public QuickReport findByIdWithMediaAndResolve(Long id) {
		return this.quickReportRepository.findByIdWithMediaAndResolve(id);
	}

	@Override
	public Page<QuickReport> findAllByOrganizationWithMedia(
			Long organizationId, Pageable pageable) {
		return this.quickReportRepository.findAllByOrganizationWithMedia(organizationId, pageable);
	}

}
