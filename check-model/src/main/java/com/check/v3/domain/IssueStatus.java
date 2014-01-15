package com.check.v3.domain;

public enum IssueStatus {
	OPENED("未解决"),
	CLOSED("已解决");

	private String text;
	private IssueStatus(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
}
