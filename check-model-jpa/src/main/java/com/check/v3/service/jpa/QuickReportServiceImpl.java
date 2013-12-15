package com.check.v3.service.jpa;



import java.util.ArrayList;
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
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.repository.QuickReportRepository;
import com.check.v3.service.CheckImageFileService;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.exception.ImageTypeWrongException;
import com.check.v3.service.tools.FileAlignmentMedia;
import com.check.v3.service.tools.FileAlignmentMedia.FileAlignmentMediaResult;

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
	public void delete(QuickReport quickReport) {
		quickReportRepository.delete(quickReport);
		checkImageFileService.delete(quickReport.getImages());
		if (quickReport.getResolves()!=null){
			for(QuickReportResolve r : quickReport.getResolves()){
				if (r.getImages()!=null){
					checkImageFileService.delete(r.getImages());
				}
			}
		}
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
	
	//* 这个假设imageFiles.size  和 quickReport.checkImages.size是相同的和最大image个数相同，这个只有web的时候使用
	@Override
	public QuickReport save(QuickReport quickReport,List<MultipartFile> imageFiles) throws ImageTypeWrongException 
	{
		
		FileAlignmentMediaResult result = null;
		result = FileAlignmentMedia.getResult(imageFiles, quickReport.getImages());
		quickReport.getImages().removeAll(result.getEmptyCheckImages());
		quickReport.getImages().removeAll(result.getNeededDeleteCheckImages());
		QuickReport q = save(quickReport);
		checkImageFileService.save(imageFiles, result.getNeededStoreCheckImages());
		checkImageFileService.delete(result.getNeededDeleteCheckImages());
		return q;
	}

	//这个是用于api服务
	@Override
	public QuickReport save(QuickReport quickReport,List<MultipartFile> newImageFiles,List<Long> needDeletedCheckImageIds) {
		List<CheckImage> 		neededStoreCheckImages 	= new ArrayList<CheckImage>();
		List<MultipartFile>		emptyFiles			   	= new ArrayList<MultipartFile>();
		List<CheckImage>		neededDeleteCheckImages = new ArrayList<CheckImage>();
		
		//需要被删除的image
		if (quickReport.getImages() != null && needDeletedCheckImageIds != null){
			for(Long id : needDeletedCheckImageIds){
				for(QuickReportImage quickReportImage:quickReport.getImages()){
					if (quickReportImage.getId().equals(id)){
						neededDeleteCheckImages.add(quickReportImage);
					}
				}
			}
			quickReport.getImages().removeAll(neededDeleteCheckImages);
		}
		//新增加的image
		if (newImageFiles != null && newImageFiles.size() !=0){
			for(MultipartFile f  : newImageFiles){
				if (!f.isEmpty()){
					QuickReportImage quickReportImage = new QuickReportImage();
					quickReportImage.setSubmitter(quickReport.getSubmitter());
					quickReportImage.setDepartment(quickReport.getDepartment());
					quickReportImage.setName(FileAlignmentMedia.BuildImageName(quickReportImage));
					quickReport.addImage(quickReportImage);
					neededStoreCheckImages.add(quickReportImage);
				}else{
					emptyFiles.add(f);
				}
			}
			newImageFiles.removeAll(emptyFiles);
		}
		QuickReport q = save(quickReport);
		checkImageFileService.save(newImageFiles, neededStoreCheckImages);
		checkImageFileService.delete(neededDeleteCheckImages);
		return q;
	}

}
