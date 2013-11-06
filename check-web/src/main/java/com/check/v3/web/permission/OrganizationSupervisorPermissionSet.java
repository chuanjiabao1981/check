package com.check.v3.web.permission;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.security.Role;

@Service("organizationSupervisorPermissionSet")
public class OrganizationSupervisorPermissionSet extends OrganizationUserSharePermissionSet {
	

	public OrganizationSupervisorPermissionSet()
	{
		super();
	}

	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.ORGANIZATION_SUPERVISOR, this);
	}
}
