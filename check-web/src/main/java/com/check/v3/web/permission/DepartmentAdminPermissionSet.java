package com.check.v3.web.permission;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.domain.Role;


@Service("departmentAdminPermissionSet")
public class DepartmentAdminPermissionSet extends DepartmentUserSharePermissionSet {
	
	public DepartmentAdminPermissionSet()
	{
		super();
	}


	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.DEPARTMENT_ADMIN, this);
	}
}
