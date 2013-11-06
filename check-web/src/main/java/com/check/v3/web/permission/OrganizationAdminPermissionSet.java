package com.check.v3.web.permission;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.domain.User;
import com.check.v3.security.Role;


@Service("organizationAdminPermissionSet")
public class OrganizationAdminPermissionSet extends OrganizationUserSharePermissionSet {
	
	public OrganizationAdminPermissionSet()
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
		this.rolepermissionManager.register(Role.ORGANIZATION_ADMIN, this);
	}
}
