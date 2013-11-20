package com.check.v3.web.permission;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionPolicy;
import com.check.v3.service.UserService;
import com.check.v3.web.controller.UsersController;

@Service("departmentMemberPermissionSet")
public class DepartmentMemberPermissionSet extends DepartmentUserSharePermissionSet {

		
	public DepartmentMemberPermissionSet()
	{
		super();
	}
	
	
	


	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.DEPARTMENT_MEMEBER, this);
		super.sharePermissionSet();
		
	}
}
