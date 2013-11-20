package com.check.v3.security.permission.policy;

import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionPolicy;

public class GrantedPermission implements PermissionPolicy{

	@Override
	public boolean filter(User user, Object object) {
		return true;
	}

	@Override
	public Object getInstance(Long id) {
		return null;
	}
	
}
