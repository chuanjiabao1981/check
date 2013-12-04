package com.check.v3.service.jpa;


import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.CheckImage;
import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportImage;
import com.check.v3.repository.QuickReportRepository;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.exception.ImageTypeWrongException;

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
	public QuickReport save(QuickReport quickReport) throws ImageTypeWrongException {
		
		List<QuickReportImage>  needRemoved = new LinkedList<QuickReportImage>();
		int idx = 0;
		for(QuickReportImage image:quickReport.getImages()){
			idx++;
			if (image.getFile() == null || image.getFile().isEmpty()){
				needRemoved.add(image);
				continue;
			}
			if (image.getFile().getContentType() == null || !image.getFile().getContentType().equals("image/jpeg")){
				System.err.println(image.getFile().getContentType());
				throw new ImageTypeWrongException(idx-1);
			}
		}
		
		//remove empty image
		for(QuickReportImage i : needRemoved){
			quickReport.removeImage(i);
		}
		for(QuickReportImage image:quickReport.getImages()){
			if (image.getName() == null){
				image.setName(BuildImageName(image));
			}
		}
		
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

	
	private String BuildImageName(CheckImage i)
	{
		DateTime s = new DateTime();
		return s.toString("yyyy-MM-dd")+"/"+i.getClass().getSimpleName()+"/"+UUID.randomUUID();
	}

}
