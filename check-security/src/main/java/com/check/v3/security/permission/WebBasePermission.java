package com.check.v3.security.permission;

import org.springframework.beans.factory.annotation.Autowired;

import com.check.v3.security.ControllerActionConstant;
import com.check.v3.service.UserService;

public class WebBasePermission extends BasePermission{

	@Autowired
	UserService userService;

	
	public WebBasePermission()
	{
		super();
		registerAllLoadInstance();
	}
	
	private void registerAllLoadInstance()
	{
		this.addLoadInstance(ControllerActionConstant.USER, ControllerActionConstant.EDIT, userService);
		this.addLoadInstance(ControllerActionConstant.USER, ControllerActionConstant.UPDATE, userService);
	}
}
