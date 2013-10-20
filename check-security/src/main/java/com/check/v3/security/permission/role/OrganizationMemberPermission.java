package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

@Service("organizationMemberPermission")
public class OrganizationMemberPermission extends OrganizationUserSharePermission {

	public OrganizationMemberPermission()
	{
		super();
	}
}
