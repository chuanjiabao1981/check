package com.check.v3.service;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.service.exception.ImageTypeWrongException;

public interface QuickReportService {
	
	public QuickReport 			findById(Long id);
	public QuickReport			findByIdWithMedia(Long id);
	public QuickReport			findByIdWithMediaAndResolve(Long id);
	public QuickReport 			save(QuickReport quickReport) throws ImageTypeWrongException;
	public QuickReport			save(QuickReport quickReport,List<MultipartFile> imageFiles) throws ImageTypeWrongException;
	public QuickReport			save(QuickReport quickReport,List<MultipartFile> newImageFiles,List<Long> needDeletedCheckImageIds) throws ImageTypeWrongException;
	public QuickReport			save(QuickReport quickReport,List<MultipartFile> newImageFiles,Map<Long,MultipartFile> editImageFiles,List<Long> needDeletedCheckImageIds) throws ImageTypeWrongException;
	public Page<QuickReport>	findByOrganization(Organization organization,Pageable pageable);
	public Page<QuickReport>	findAllByOrganizationIdWithMedia(Long organizationId,Pageable pageable);
	public Page<QuickReport>	findAllByOrganizationIdWithMediaAndResolve(Long organizationId,Pageable pageable);
	public void 	   			delete(QuickReport quickReport);	
}
