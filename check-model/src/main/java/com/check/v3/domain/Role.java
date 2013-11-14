package com.check.v3.domain;

public enum Role {
	
	SYS_ADMIN("系统管理员"),
	DEPARTMENT_ADMIN("部门系统管理员"),
	DEPARTMENT_SUPERVISOR("部门领导"),
	DEPARTMENT_MEMEBER("部门成员");

	private String text;
	
	private Role(String t)
	{
		this.text = t;
	}
	public String getText()
	{
		return text;
	}
}
