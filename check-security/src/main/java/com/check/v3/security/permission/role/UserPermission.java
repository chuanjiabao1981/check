package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

import com.check.v3.security.permission.BasePermission;

@Service("UserPermission")
public class UserPermission extends BasePermission {

	public UserPermission()
	{
		super();
	}
}
