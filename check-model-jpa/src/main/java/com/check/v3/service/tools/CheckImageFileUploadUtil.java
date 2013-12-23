package com.check.v3.service.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.CheckImage;
import com.check.v3.domain.QuickReportImage;
import com.check.v3.domain.util.BaseEntityComparer;
import com.check.v3.service.exception.ImageTypeWrongException;
import com.google.common.collect.Lists;

public class CheckImageFileUploadUtil {

	public static List<CheckImage> getNeededHandleCheckImage(Set<? extends CheckImage> checkImageSet)
	{
		
		int idx = 0;
		ArrayList<CheckImage> checkImages = Lists.newArrayList(checkImageSet.iterator());
		List<CheckImage> result 		  = Lists.newArrayList();

		Collections.sort(checkImages,new BaseEntityComparer());
		for(CheckImage checkImage:checkImages){
			idx++;
			if (checkImage != null && checkImage.getFile() != null && !checkImage.getFile().isEmpty()){
				MultipartFile f = checkImage.getFile();
				if ((!("image/jpeg".equalsIgnoreCase(f.getContentType()))) &&
						(!("image/png".equalsIgnoreCase(f.getContentType())))){
						throw new ImageTypeWrongException(idx-1);
					}

			}
		}
		for(CheckImage checkImage:checkImages){
			if (checkImage.getId() == null){
				if (checkImage.getFile() == null || checkImage.getFile().isEmpty()){
					checkImageSet.remove(checkImage);
				}
			}else{
				if (checkImage.isDel()){
					checkImageSet.remove(checkImage);
				}
			}
		}
		checkImages 	= Lists.newArrayList(checkImageSet.iterator());
		Collections.sort(checkImages,new BaseEntityComparer());
		Iterator<CheckImage> i = checkImages.iterator();
		while(i.hasNext()){
			CheckImage checkImage = (CheckImage) i.next();
			if (checkImage.getId() == null){
				//new image
				checkImage.setOriginalName(checkImage.getFile().getOriginalFilename());
				result.add(checkImage);
			}else{
				//change image
				checkImage.setOriginalName(checkImage.getFile().getOriginalFilename());
				//为了变化
				checkImage.setUpdatedAt(new DateTime());
			}
		}
		
		return result;
	}
	public static List<CheckImage> getNeededHandleCheckImage(Set<? extends CheckImage> checkImageSet,
															 List<MultipartFile> newImageFiles,
															 List<Long> needDeletedCheckImageIds
															 )
	{
		List<CheckImage> result 		  		= Lists.newArrayList();
		Iterator<? extends CheckImage> 	 	i	= checkImageSet.iterator();
		
		int newCheckImageSize					= 0;
		for(CheckImage checkImage:checkImageSet){
			if (checkImage.getId() == null){
				newCheckImageSize++;
			}
		}
		if (newImageFiles.size() != newCheckImageSize){
			throw new RuntimeException("new CheckImage size("+newCheckImageSize+") is not equals file size("+newImageFiles.size()+")");
		}
		if (needDeletedCheckImageIds != null){
			for(Long id : needDeletedCheckImageIds){
				i	= checkImageSet.iterator();
				while(i.hasNext()){
					CheckImage checkImage = i.next();
					if (checkImage != null && checkImage.getId() != null && checkImage.getId().equals(id)){
						i.remove();
					}
				}
			}
		}
		i	= checkImageSet.iterator();
		for(MultipartFile f:newImageFiles){
			CheckImage checkImage = null;
			do{
				checkImage = i.next();
			}while(checkImage.getId()!=null && i.hasNext());
			if (checkImage == null || checkImage.getId() !=null){
				throw new RuntimeException("can't get a new CheckImage!");
			}
			if (!f.isEmpty()){
				if (f.getOriginalFilename() != null && !f.getOriginalFilename().isEmpty()){
					checkImage.setOriginalName(f.getOriginalFilename());
				}
				checkImage.setFile(f);
				result.add(checkImage);
			}else{
				i.remove();
			}
		}
		return result;
	}
}
