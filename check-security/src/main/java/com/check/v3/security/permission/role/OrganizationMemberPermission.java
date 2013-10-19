package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

import com.check.v3.security.permission.BasePermission;

@Service("organizationMemberPermission")
public class OrganizationMemberPermission extends BasePermission {

	public OrganizationMemberPermission()
	{
		super();
	}
}
