package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

import com.check.v3.security.permission.BasePermission;

@Service("adminPermssion")
public class AdminPermission extends BasePermission {

	public AdminPermission()
	{
		super();
	}
	@Override
	public boolean isAllowed(String controller, String action, Object instance) {
		return true;
	}

}
