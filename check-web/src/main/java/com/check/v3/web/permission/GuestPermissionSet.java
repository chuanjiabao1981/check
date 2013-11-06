package com.check.v3.web.permission;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.ControllerActionConstant;
import com.check.v3.security.Role;
import com.check.v3.security.permission.PermissionSet;
import com.check.v3.web.controller.LoginController;


@Service("guestPermissionSet")
public class GuestPermissionSet extends PermissionSet {
	
	public GuestPermissionSet()
	{
		super();
		allow(LoginController.class.getSimpleName(),ControllerActionConstant.NEW_LOGIN);
		allow(LoginController.class.getSimpleName(),ControllerActionConstant.FAIL_LOGIN);
	}

	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.GUEST, this);
	}

}
