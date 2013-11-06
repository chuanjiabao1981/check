package com.check.v3.security;

public enum Role {
	GUEST,
	ORGANIZATION_MEMBER, 		//属于机构的职员都拥有此角色
	ORGANIZATION_SUPERVISOR,	//属于机构的管理员拥有此角色
	ORGANIZATION_ADMIN			//属于机构的系统管理员拥有此角色（可以创建、修改删除机构，添加人员等）
}
