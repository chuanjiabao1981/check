package com.check.v3.service.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.CheckImage;
import com.check.v3.service.exception.ImageTypeWrongException;

//这个类的目的是返回
//1. 空的多媒体文件（MultipartFile）对应的空的多媒体对象（CheckImage）
//2. 需要保存的多媒体文件（MultipartFile）和对应的多媒体对象（CheckImage）
public class FileAlignmentMedia {

	
	public static class FileAlignmentMediaResult
	{
		List<MultipartFile> 		emptyFiles;
		Set<CheckImage>			emptyCheckImages;
		Set<CheckImage>			neededStoreCheckImages;
		Set<CheckImage>			neededDeleteCheckImages;
		
		
		public List<MultipartFile> getEmptyFiles() {
			return emptyFiles;
		}
		public Set<CheckImage> getEmptyCheckImages() {
			return emptyCheckImages;
		}
		public Set<CheckImage> getNeededStoreCheckImages() {
			return neededStoreCheckImages;
		}
		public void setEmptyFiles(List<MultipartFile> emptyFiles) {
			this.emptyFiles = emptyFiles;
		}
		public void setEmptyCheckImages(Set<CheckImage> emptyCheckImages) {
			this.emptyCheckImages = emptyCheckImages;
		}
		public void setNeededStoreCheckImages(Set<CheckImage> neededStoreCheckImages) {
			this.neededStoreCheckImages = neededStoreCheckImages;
		}
		public Set<CheckImage> getNeededDeleteCheckImages() {
			return neededDeleteCheckImages;
		}
		public void setNeededDeleteCheckImages(Set<CheckImage> neededDeleteCheckImages) {
			this.neededDeleteCheckImages = neededDeleteCheckImages;
		}
		
		
		
	}
	/*
	 * 这个假设imageFiles.size  和 checkImages.size是相同的和最大image个数相同，这个是有web的时候使用
	 * 1. imageFiles 全部的N个上传文件（有的可能为空）
	 * 2. iterator 	 全部的N个多媒体对象
	 */
	public  static FileAlignmentMediaResult getResult(List<MultipartFile> imageFiles, Set<? extends CheckImage> checkImages)
	{
		int idx = 0;
		for(MultipartFile f:imageFiles){
			idx++;
			if (!f.isEmpty()){//上传了一个图片文件
				if ((!("image/jpeg".equalsIgnoreCase(f.getContentType()))) &&
					(!("image/png".equalsIgnoreCase(f.getContentType())))){
					throw new ImageTypeWrongException(idx-1);
				}
						
			}
		}
		
		//记录上传文件为空的多媒体文件
		List<MultipartFile> 		emptyFiles 						= new ArrayList<MultipartFile>();
		//无上传文件的CheckImage对象
		Set<CheckImage>			emptyCheckImages				= new HashSet<CheckImage>();
		//用户指定删除的CheckImage对象
		Set<CheckImage>			neededDeleteCheckImages 		= new HashSet<CheckImage>();
		//有上传文件的CheckImage（可能是更新或者是新创建）
		Set<CheckImage>			neededStoreCheckImages			= new HashSet<CheckImage>();
		
		Iterator iterator = checkImages.iterator();
		for(MultipartFile  f : imageFiles){
			
			CheckImage q =  (CheckImage) iterator.next();
			if (q.isDel()){
				neededDeleteCheckImages.add(q);
				emptyFiles.add(f);
				continue;
			}
			if (!f.isEmpty()){//上传了一个图片文件
				if (q.getName() == null || q.getName().isEmpty()){//以前没有图片
					q.setName(BuildImageName(q));
				}
				neededStoreCheckImages.add(q);
				q.setOriginalName(f.getOriginalFilename());
			}else{//没上传图片
				emptyFiles.add(f);
				if (q.getName() == null || q.getName().isEmpty()){//以前没图片，没必要保存
					emptyCheckImages.add(q);
				}
			}
		}
		//删除不必要保存的文件
		imageFiles.removeAll(emptyFiles);
		
		if (imageFiles.size() != neededStoreCheckImages.size()){
			throw new RuntimeException("上传文件和需要存储的checkImage个数不匹配"+imageFiles.size() + "|"+neededStoreCheckImages.size()+"|"+emptyCheckImages.size());
		}
		FileAlignmentMediaResult r = new FileAlignmentMediaResult();
		r.setEmptyFiles(emptyFiles);
		r.setEmptyCheckImages(emptyCheckImages);
		r.setNeededStoreCheckImages(neededStoreCheckImages);
		r.setNeededDeleteCheckImages(neededDeleteCheckImages);
		return r;
	}
	public static String BuildImageName(CheckImage i)
	{
		DateTime s = new DateTime();
		return s.toString("yyyy-MM-dd")+"/"+i.getClass().getSimpleName()+"/"+UUID.randomUUID();
	}

}
