package com.check.v3.service.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.check.v3.domain.CheckImage;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.domain.QuickReportState;
import com.check.v3.domain.ResolveImage;
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
		//这个会设置resolve_order
		q.setState(QuickReportState.CLOSED);
		quickReportReporsitory.save(q);

		return s;
	}

	@Override
	@Transactional
	public QuickReportResolve save(QuickReportResolve quickReportResolve,List<MultipartFile> imageFiles) throws ImageTypeWrongException {
		FileAlignmentMediaResult result = null;
		result = FileAlignmentMedia.getResult(imageFiles, quickReportResolve.getImages());
		quickReportResolve.getImages().removeAll(result.getEmptyCheckImages());
		quickReportResolve.getImages().removeAll(result.getNeededDeleteCheckImages());
		QuickReportResolve q = save(quickReportResolve);
//		checkImageFileService.save(imageFiles, result.getNeededStoreCheckImages());
//		checkImageFileService.delete(result.getNeededDeleteCheckImages());
		return q;

	}

	@Override
	@Transactional
	public QuickReportResolve save(QuickReportResolve quickReportResolve,List<MultipartFile> newImageFiles,List<Long> needDeletedCheckImageIds)
	{
		SortedSet<CheckImage> 		neededStoreCheckImages 	= new TreeSet<CheckImage>();
		List<MultipartFile>		emptyFiles			   	= new ArrayList<MultipartFile>();
		SortedSet<CheckImage>		neededDeleteCheckImages = new TreeSet<CheckImage>();
		
		//需要被删除的image
		if (quickReportResolve.getImages() != null && needDeletedCheckImageIds != null){
			for(Long id : needDeletedCheckImageIds){
				for(CheckImage checkImage:quickReportResolve.getImages()){
					if (checkImage.getId().equals(id)){
						neededDeleteCheckImages.add(checkImage);
					}
				}
			}
			quickReportResolve.getImages().removeAll(neededDeleteCheckImages);
		}
		//新增加的image
		if (newImageFiles != null && newImageFiles.size() !=0){
			for(MultipartFile f  : newImageFiles){
				if (!f.isEmpty()){
					ResolveImage resolveImage = new ResolveImage();
					resolveImage.setSubmitter(quickReportResolve.getSubmitter());
					resolveImage.setDepartment(quickReportResolve.getDepartment());
					resolveImage.setName(FileAlignmentMedia.BuildImageName(resolveImage));
					quickReportResolve.addImage(resolveImage);
					neededStoreCheckImages.add(resolveImage);
				}else{
					emptyFiles.add(f);
				}
			}
			newImageFiles.removeAll(emptyFiles);
		}
		QuickReportResolve q = save(quickReportResolve);
		//checkImageFileService.save(newImageFiles, neededStoreCheckImages);
		//checkImageFileService.delete(neededDeleteCheckImages);
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

	@Override
	@Transactional
	public void delete(QuickReportResolve quickReportResolve) {
		quickReportResolveRepository.delete(quickReportResolve);
//		checkImageFileService.delete(quickReportResolve.getImages());
	}


}
