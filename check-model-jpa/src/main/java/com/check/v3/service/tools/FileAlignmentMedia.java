package com.check.v3.service.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
		List<CheckImage>			emptyCheckImages;
		List<CheckImage>			neededStoreCheckImages;
		List<CheckImage>			neededDeleteCheckImages;
		
		
		public List<MultipartFile> getEmptyFiles() {
			return emptyFiles;
		}
		public List<CheckImage> getEmptyCheckImages() {
			return emptyCheckImages;
		}
		public List<CheckImage> getNeededStoreCheckImages() {
			return neededStoreCheckImages;
		}
		public void setEmptyFiles(List<MultipartFile> emptyFiles) {
			this.emptyFiles = emptyFiles;
		}
		public void setEmptyCheckImages(List<CheckImage> emptyCheckImages) {
			this.emptyCheckImages = emptyCheckImages;
		}
		public void setNeededStoreCheckImages(List<CheckImage> neededStoreCheckImages) {
			this.neededStoreCheckImages = neededStoreCheckImages;
		}
		public List<CheckImage> getNeededDeleteCheckImages() {
			return neededDeleteCheckImages;
		}
		public void setNeededDeleteCheckImages(List<CheckImage> neededDeleteCheckImages) {
			this.neededDeleteCheckImages = neededDeleteCheckImages;
		}
		
		
		
	}
	/*
	 * 1. imageFiles 全部的上传文件（有的可能为空）
	 * 2. iterator 	 全部的多媒体对象
	 */
	public  static FileAlignmentMediaResult getResult(List<MultipartFile> imageFiles, List<? extends CheckImage> checkImages)
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
		List<CheckImage>			emptyCheckImages				= new ArrayList<CheckImage>();
		//用户指定删除的CheckImage对象
		List<CheckImage>			neededDeleteCheckImages 		= new ArrayList<CheckImage>();
		//有上传文件的CheckImage（可能是更新或者是新创建）
		List<CheckImage>			neededStoreCheckImages			= new ArrayList<CheckImage>();
		
		Iterator iterator = checkImages.iterator();
		for(MultipartFile  f : imageFiles){
			CheckImage q =  (CheckImage) iterator.next();
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
		
		iterator = checkImages.iterator();
		while(iterator.hasNext()){
			CheckImage q = (CheckImage) iterator.next();
			//被用户删除
			if (q.isDel()){
				neededDeleteCheckImages.add(q);
				System.err.println("delete ..........."+q.getId() );
			}
		}
		
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
	protected static String BuildImageName(CheckImage i)
	{
		DateTime s = new DateTime();
		return s.toString("yyyy-MM-dd")+"/"+i.getClass().getSimpleName()+"/"+UUID.randomUUID();
	}

}
