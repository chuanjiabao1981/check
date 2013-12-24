package com.check.v3.service.jpa;



import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.CheckImage;
import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportImage;
import com.check.v3.repository.QuickReportRepository;
import com.check.v3.service.CheckImageFileService;
import com.check.v3.service.QuickReportImageService;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.exception.ImageTypeWrongException;
import com.check.v3.service.tools.CheckImageFileUploadUtil;

@Service("quickReportService")
@Repository
@Transactional
public class QuickReportServiceImpl implements QuickReportService{

	
	@PersistenceContext 
	private EntityManager em;

	@Resource
	private QuickReportRepository quickReportRepository;
	
	
	@Resource
	CheckImageFileService checkImageFileService;
	
	@Resource
	QuickReportImageService quickReportImageService;
	
	
	@Override
	@Transactional(readOnly=true)
	public QuickReport findById(Long id) {
		return quickReportRepository.findOne(id);
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
	public Page<QuickReport> findAllByOrganizationIdWithMedia(
			Long organizationId, Pageable pageable) {
		return this.quickReportRepository.getAllByOrganizationIdWithMedia(organizationId, pageable);
	}
	
	//这个是用于web服务
	@Override
	@Transactional
	public QuickReport save(QuickReport quickReport) throws ImageTypeWrongException {

		
		List<CheckImage> checkImages = CheckImageFileUploadUtil.getNeededHandleCheckImage(quickReport.getImages());
		for(CheckImage checkImage:checkImages){
			quickReportImageService.save((QuickReportImage) checkImage);
		}
		QuickReport 			 q 	= quickReportRepository.save(quickReport);

		return q;
	}
	//这个是用于api服务
	@Override
	@Transactional
	public QuickReport save(QuickReport quickReport,List<MultipartFile> newImageFiles,List<Long> needDeletedCheckImageIds) {
		List<CheckImage> checkImages = CheckImageFileUploadUtil.getNeededHandleCheckImage(quickReport.getImages(),newImageFiles,needDeletedCheckImageIds);
		for(CheckImage checkImage:checkImages){
			quickReportImageService.save((QuickReportImage) checkImage);
		}
		QuickReport 			 q 	= quickReportRepository.save(quickReport);

		return q;
	}

	@Override
	public Page<QuickReport> findAllByOrganizationIdWithMediaAndResolve(
			Long organizationId, Pageable pageable) {
		return quickReportRepository.getAllByOrganizationIdWithMediaAndResolve(organizationId, pageable);
	}

}
