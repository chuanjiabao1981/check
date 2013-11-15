package com.check.v3.web.permission;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.domain.Role;
import com.check.v3.domain.User;


@Service("departmentAdminPermissionSet")
public class DepartmentAdminPermissionSet extends DepartmentUserSharePermissionSet {
	
	public DepartmentAdminPermissionSet()
	{
		super();
	}

	public boolean isAllowed(User user,String controller,String action)
	{
		return true;
	}
	public boolean isAllowed(User user,String controller,String action,Object instance)
	{
		return true;
	}
	

	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.DEPARTMENT_ADMIN, this);
	}
}
