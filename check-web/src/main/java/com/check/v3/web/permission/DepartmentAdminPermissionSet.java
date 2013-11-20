package com.check.v3.web.permission;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.Role;
import com.check.v3.web.controller.OrganizationsController;


@Service("departmentAdminPermissionSet")
public class DepartmentAdminPermissionSet extends DepartmentUserSharePermissionSet {
	
	public DepartmentAdminPermissionSet()
	{
		super();
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.INDEX);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.NEW);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.CREATE);
	}


	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.DEPARTMENT_ADMIN, this);
	}
}
