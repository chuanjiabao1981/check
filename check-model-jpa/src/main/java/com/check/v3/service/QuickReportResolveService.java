package com.check.v3.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.QuickReportResolve;
import com.check.v3.service.exception.ImageTypeWrongException;

public interface QuickReportResolveService {
	public QuickReportResolve findById(Long id);
	public QuickReportResolve findByIdWithMedia(Long id);
	public QuickReportResolve save(QuickReportResolve quickReportResolve); 
	public QuickReportResolve save(QuickReportResolve quickReportResolve,List<MultipartFile> imageFiles) throws ImageTypeWrongException;

}
