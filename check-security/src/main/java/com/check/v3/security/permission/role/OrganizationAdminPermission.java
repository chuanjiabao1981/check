package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;


@Service("organizationAdminPermission")
public class OrganizationAdminPermission extends OrganizationUserSharePermission {
	
	public OrganizationAdminPermission()
	{
		super();
	}

}
