package com.check.v3.domain;

public enum CheckImageType
{
	little_thumbnail(32,32,"little-thumbnail"),
	small_thumbnail(64,64,"small-thumbnail"),
	thumbnail(128,128,"thumbnail"),
	normal(256,256,"normal");
	int width,height;
	String text;
	private CheckImageType(int width,int height,String text)
	{
		this.width 		= width;
		this.height		= height;
		this.text		= text;
	}
	
	public int getWidth()
	{
		return width;
	}
	public int getHeigth()
	{
		return height;
	}
	public String getText()
	{
		return text;
	}
};