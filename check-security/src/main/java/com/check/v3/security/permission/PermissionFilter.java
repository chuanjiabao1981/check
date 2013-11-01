package com.check.v3.security.permission;

import com.check.v3.domain.User;

public interface PermissionFilter {
	boolean filter(User user,Object instance);
}
