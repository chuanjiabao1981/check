package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

import com.check.v3.security.permission.WebBasePermission;


@Service("guestPermission")
public class GuestPermision extends WebBasePermission {
	
	public GuestPermision()
	{
		super();
	}

}
