package com.check.v3.security.permission.role;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionSet;

@Service("rolePermissionManager")
public class RolePermissionManager {
	private static final Logger logger = LoggerFactory.getLogger(RolePermissionManager.class);

	
	@Resource(name="guestPermissionSet")
	private PermissionSet guestPermissionSet;
	
	@Resource(name="organizationSupervisorPermissionSet")
	private PermissionSet organizationSupervisorPermissionSet;
	
	@Resource(name="organizationMemberPermissionSet")
	private PermissionSet organizationMemberPermissionSet;
	
	@Resource(name="organizationAdminPermissionSet")
	private PermissionSet organizationAdminPermissionSet;
	
	@Autowired
	private AdminPermission adminPermission;
	
	@Autowired
	private UserPermission userPermission;
	
	public  boolean isAllowed(User user,Role role,String controller,String action,Object instance )
	{
		switch (role)
		{
			case GUEST:
				return guestPermissionSet.isAllowed(user,controller, action, instance);
			case ADMIN:
				return adminPermission.isAllowed(user,controller, action, instance);
			case ORGANIZATION_SUPERVISOR:
				return organizationSupervisorPermissionSet.isAllowed(user,controller, action, instance);
			case ORGANIZATION_MEMBER:
				return organizationMemberPermissionSet.isAllowed(user,controller, action, instance);
			case ORGANIZATION_ADMIN:
				return organizationAdminPermissionSet.isAllowed(user,controller, action, instance);
			case USER:
				return userPermission.isAllowed(user,controller, action, instance);
			default:
				logger.trace("no role is match");
				return false;
				
		}
	}

}
