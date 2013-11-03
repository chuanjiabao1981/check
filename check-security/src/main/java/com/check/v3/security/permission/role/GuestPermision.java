package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

import com.check.v3.ControllerActionConstant;
import com.check.v3.security.permission.BasePermission;


@Service("guestPermission")
public class GuestPermision extends BasePermission {
	
	public GuestPermision()
	{
		super();
//		allow(ControllerActionConstant.SESSION_REST, ControllerActionConstant.CREATE);
	}

}
