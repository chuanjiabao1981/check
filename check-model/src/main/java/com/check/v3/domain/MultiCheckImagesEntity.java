package com.check.v3.domain;

public interface MultiCheckImagesEntity {
	public CheckImage buildCheckImage();
	public CheckImage removeCheckImage(Long id);
	public CheckImage getImage(Long id);
	public void removeAllEmptyCheckImage();
}
