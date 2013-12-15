package com.check.v3.service.jpa;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.domain.QuickReportState;
import com.check.v3.repository.QuickReportRepository;
import com.check.v3.repository.QuickReportResolveRepository;
import com.check.v3.service.CheckImageFileService;
import com.check.v3.service.QuickReportResolveService;
import com.check.v3.service.exception.ImageTypeWrongException;
import com.check.v3.service.tools.FileAlignmentMedia;
import com.check.v3.service.tools.FileAlignmentMedia.FileAlignmentMediaResult;

@Service("quickReportResolveServiceImpl")
@Repository
@Transactional
public class QuickReportResolveServiceImpl implements QuickReportResolveService{

	@Resource
	private QuickReportResolveRepository quickReportResolveRepository;
	@Resource
	private QuickReportRepository quickReportReporsitory;
	@Resource
	CheckImageFileService checkImageFileService;

	@Override
	@Transactional
	public QuickReportResolve save(QuickReportResolve quickReportResolve) {

		QuickReportResolve s = quickReportResolveRepository.save(quickReportResolve);

		QuickReport q = quickReportResolve.getQuickReport();
		if (q.getState() == QuickReportState.OPENED){
			q.setState(QuickReportState.CLOSED);
			quickReportReporsitory.save(q);
		}

		return s;
	}

	@Override
	@Transactional
	public QuickReportResolve save(QuickReportResolve quickReportResolve,List<MultipartFile> imageFiles) throws ImageTypeWrongException {
		FileAlignmentMediaResult result = null;
		result = FileAlignmentMedia.getResult(imageFiles, quickReportResolve.getImages());
		quickReportResolve.getImages().removeAll(result.getEmptyCheckImages());
		quickReportResolve.getImages().removeAll(result.getNeededDeleteCheckImages());
		QuickReportResolve q = quickReportResolveRepository.save(quickReportResolve);
		checkImageFileService.save(imageFiles, result.getNeededStoreCheckImages());
		checkImageFileService.delete(result.getNeededDeleteCheckImages());
		return q;

	}

	@Override
	@Transactional(readOnly=true)
	public QuickReportResolve findById(Long id) {
		return this.quickReportResolveRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly=true)
	public QuickReportResolve findByIdWithMedia(Long id) {
		return this.quickReportResolveRepository.findByIdWithMedia(id);
	}


}
