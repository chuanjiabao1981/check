package com.check.v3.web.permission;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.domain.Role;


@Service("departmentSupervisorPermissionSet")
public class DepartmentSupervisorPermissionSet extends DepartmentUserSharePermissionSet {
	

	public DepartmentSupervisorPermissionSet()
	{
		super();
	}

	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.DEPARTMENT_SUPERVISOR, this);
		super.sharePermissionSet();
	}
}
