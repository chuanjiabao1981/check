package com.check.v3.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.CheckImage;

public interface CheckImageFileService {
	
	public void save(List<MultipartFile> imageFiles, List<CheckImage> checkImages);
	public void delete(Iterator checkImages);
}
