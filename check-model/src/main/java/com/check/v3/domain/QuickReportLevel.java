package com.check.v3.domain;

public enum QuickReportLevel {
	
	HIGH("高"),
	MID("中"),
	LOW("低");
	
	private String text;
	private QuickReportLevel(String text)
	{
		this.text = text;
	}
	public String getText()
	{
		return text;
	}
}
