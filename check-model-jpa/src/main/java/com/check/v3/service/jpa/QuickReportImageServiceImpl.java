package com.check.v3.service.jpa;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.QuickReportImage;
import com.check.v3.repository.QuickReportImageRepository;
import com.check.v3.service.QuickReportImageService;

@Service("quickReportImageService")
@Repository
@Transactional
public class QuickReportImageServiceImpl implements QuickReportImageService {

	@Resource 
	QuickReportImageRepository quickReportImageRepository;

	@Override
	public void delete(QuickReportImage quickReportImage) {
		this.quickReportImageRepository.delete(quickReportImage);
	}
}
