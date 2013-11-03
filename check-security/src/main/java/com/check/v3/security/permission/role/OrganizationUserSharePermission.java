package com.check.v3.security.permission.role;

import com.check.v3.ControllerActionConstant;
import com.check.v3.security.permission.BasePermission;

public class OrganizationUserSharePermission extends BasePermission{
	
	public OrganizationUserSharePermission()
	{
		super();
		this.allow(ControllerActionConstant.SESSION_REST, ControllerActionConstant.CREATE);
	}

}
