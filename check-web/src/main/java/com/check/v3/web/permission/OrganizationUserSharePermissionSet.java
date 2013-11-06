package com.check.v3.web.permission;

import com.check.v3.ControllerActionConstant;
import com.check.v3.security.permission.PermissionSet;
import com.check.v3.web.controller.HomeController;

public class OrganizationUserSharePermissionSet extends PermissionSet{
	
	public OrganizationUserSharePermissionSet()
	{
		super();
		allow(HomeController.class.getSimpleName(),ControllerActionConstant.HOME);
	}

}
