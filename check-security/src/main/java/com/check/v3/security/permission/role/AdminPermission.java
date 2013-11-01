package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

import com.check.v3.domain.User;
import com.check.v3.security.permission.WebBasePermission;

@Service("adminPermssion")
public class AdminPermission extends WebBasePermission {

	public AdminPermission()
	{
		super();
	}
	@Override
	public boolean isAllowed(User user,String controller, String action, Object instance) {
		return true;
	}

}
