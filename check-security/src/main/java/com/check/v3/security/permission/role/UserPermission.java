package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

import com.check.v3.security.permission.PermissionSet;

@Service("UserPermission")
public class UserPermission extends PermissionSet {

	public UserPermission()
	{
		super();
	}
}
