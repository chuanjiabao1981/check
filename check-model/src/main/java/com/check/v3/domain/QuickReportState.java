package com.check.v3.domain;

public enum QuickReportState {
	
	OPENED("未解决"),
	CLOSED("已解决");

	private String text;
	private QuickReportState(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}

}
