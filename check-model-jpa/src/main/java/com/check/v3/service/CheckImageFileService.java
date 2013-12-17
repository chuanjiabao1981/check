package com.check.v3.service;

import java.util.List;
import java.util.SortedSet;

import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.CheckImage;

public interface CheckImageFileService {
	
	public void save(List<MultipartFile> imageFiles, SortedSet<CheckImage> checkImages);
	public void delete(SortedSet<? extends CheckImage> checkImages);
}
