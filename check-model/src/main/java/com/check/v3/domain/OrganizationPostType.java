package com.check.v3.domain;

public enum OrganizationPostType 
{
	MANAGER("管理"),
	MEMEBER("成员"),
	END;
	
	private String text;

	public String getText()
	{
		return text;
	}
	
	OrganizationPostType(String text)
	{
		this.text = text;
	}
	OrganizationPostType()
	{
		this.text = "_LAST";
	}
}
