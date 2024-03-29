package com.check.v3.security.permission.role;

import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionSet;

@Service("rolePermissionManager")
public class RolePermissionManager {
	private static final Logger logger = LoggerFactory.getLogger(RolePermissionManager.class);

	private Map<Role,PermissionSet> rolePermissionSet = new HashMap<Role,PermissionSet>();
	
	public void register(Role role,PermissionSet permissionSet)
	{
		rolePermissionSet.put(role,permissionSet);
	}
	
	public  PermissionSet getPermissionSet(User user)
	{
		PermissionSet pSet = null;
		if (user == null){
			pSet = rolePermissionSet.get(Role.GUEST);
		}else{
			pSet = rolePermissionSet.get(user.getRole());
		}
		logger.trace("Current's Role is ["+ ((user == null)? Role.GUEST:user.getRole())+"]");
		if (pSet == null){
			logger.warn("no ["+user.getRole()+"] permission is found");
			return null;
		}
		return pSet;
	}

}
