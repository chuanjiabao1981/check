package com.check.v3.domain;

public enum OrganizationPostType 
{
	SUPERVISOR("管理"),
	MEMEBER("成员"),
	ADMIN("维护");
	
	private String text;

	public String getText()
	{
		return text;
	}
	
	OrganizationPostType(String text)
	{
		this.text = text;
	}
}
