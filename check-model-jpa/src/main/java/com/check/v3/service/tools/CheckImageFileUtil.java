package com.check.v3.service.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.CheckImage;
import com.check.v3.domain.MultiCheckImagesEntity;

public class CheckImageFileUtil {

	public static class NeededHandleFile
	{
		
		private List<MultipartFile> neededStoreCheckImageFile	= new ArrayList<MultipartFile>();
		private List<CheckImage> neededStoreCheckImage			= new ArrayList<CheckImage>();
		private List<CheckImage> neededDeleteCheckImage 		= new ArrayList<CheckImage>();
		
		public void addNeededStoreCheckImage(CheckImage checkImage,MultipartFile multipartFile)
		{
			this.neededStoreCheckImage.add(checkImage);
			this.neededStoreCheckImageFile.add(multipartFile);
		}
		public void addNeededDeletedCheckImage(CheckImage checkImage)
		{
			this.neededDeleteCheckImage.add(checkImage);
		}
		public List<MultipartFile> getNeededStoreCheckImageFile() {
			return neededStoreCheckImageFile;
		}
		public List<CheckImage> getNeededStoreCheckImage() {
			return neededStoreCheckImage;
		}
		public List<CheckImage> getNeededDeleteCheckImage() {
			return neededDeleteCheckImage;
		}
	}
	
	public static NeededHandleFile getNeededHandleFile(
			MultiCheckImagesEntity multiCheckImagesEntity,
			List<MultipartFile> newImageFiles,
		    Map<Long,MultipartFile> editImageFiles,
			List<Long> needDeletedCheckImageIds)
	{
		NeededHandleFile neededHandleFile = new NeededHandleFile();
		//需要删除的image
		if (needDeletedCheckImageIds != null){
			for(Long id : needDeletedCheckImageIds){
				CheckImage checkImage = multiCheckImagesEntity.removeCheckImage(id);
				if (checkImage != null && checkImage.getName() != null){
					neededHandleFile.addNeededDeletedCheckImage(checkImage);
				}
			}
		}
		//需要编辑的image
		if(editImageFiles != null && editImageFiles.size() != 0){
			for(Long id : editImageFiles.keySet()){
				if (editImageFiles.get(id) != null && !editImageFiles.get(id).isEmpty()){
					CheckImage checkImage = multiCheckImagesEntity.getImage(id);
					if (checkImage != null){
						neededHandleFile.addNeededStoreCheckImage(checkImage, editImageFiles.get(id));
					}
				}
			}
		}
		//新增加的image
		if (newImageFiles != null && newImageFiles.size() !=0){
			for(MultipartFile f  : newImageFiles){
				if (!f.isEmpty()){
					CheckImage checkImage = multiCheckImagesEntity.buildCheckImage();
					neededHandleFile.addNeededStoreCheckImage(checkImage,f);
				}
			}
		}
		return neededHandleFile;
	}
}
