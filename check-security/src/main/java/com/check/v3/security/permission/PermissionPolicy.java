package com.check.v3.security.permission;

import com.check.v3.domain.User;

public interface PermissionPolicy {
	boolean filter(User user,Object instance);
	Object getInstance(Long id);
}
