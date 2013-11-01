package com.check.v3.security.permission.filter;

import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionFilter;

public class GrantedPermissionFilter implements PermissionFilter{

	@Override
	public boolean filter(User user, Object instance) {
		return true;
	}

	
}
