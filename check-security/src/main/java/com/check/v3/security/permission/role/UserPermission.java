package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

import com.check.v3.security.permission.WebBasePermission;

@Service("UserPermission")
public class UserPermission extends WebBasePermission {

	public UserPermission()
	{
		super();
	}
}
