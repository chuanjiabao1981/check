package com.check.v3.web.permission;

import com.check.v3.ControllerActionConstant;
import com.check.v3.security.permission.PermissionSet;
import com.check.v3.web.controller.HomeController;

public abstract class DepartmentUserSharePermissionSet extends PermissionSet{
	
	public DepartmentUserSharePermissionSet()
	{
		super();
		allow(HomeController.class.getSimpleName(),ControllerActionConstant.HOME);
	}

}
