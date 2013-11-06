package com.check.v3.security.permission.role;

import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.check.v3.domain.User;
import com.check.v3.security.Role;
import com.check.v3.security.permission.PermissionSet;

@Service("rolePermissionManager")
public class RolePermissionManager {
	private static final Logger logger = LoggerFactory.getLogger(RolePermissionManager.class);

	private Map<Role,PermissionSet> rolePermissionSet = new HashMap<Role,PermissionSet>();
	
	public void register(Role role,PermissionSet permissionSet)
	{
		rolePermissionSet.put(role,permissionSet);
	}
	
	public  boolean isAllowed(User user,Role role,String controller,String action,Object instance )
	{
		PermissionSet pSet = rolePermissionSet.get(role);
		if (pSet == null){
			logger.warn("no ["+role+"] permission is found");
			return false;
		}
		return pSet.isAllowed(user, controller, action, instance);
	}

}
