package com.check.v3.security.permission.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.check.v3.domain.Role;
import com.check.v3.domain.User;

public class RolePermissionManager {
	private static final Logger logger = LoggerFactory.getLogger(RolePermissionManager.class);

	
	@Autowired
	private GuestPermision guestPermission;
	@Autowired
	private AdminPermission adminPermission;
	@Autowired
	private OrganizationSupervisorPermission organizationSupervisorPermission;
	@Autowired
	private OrganizationMemberPermission organizationMemberPermission;
	@Autowired
	private UserPermission userPermission;
	@Autowired
	private OrganizationAdminPermission organizationAdminPermission;
	
	public  boolean isAllowed(User user,Role role,String controller,String action,Object instance )
	{
		switch (role)
		{
			case GUEST:
				return guestPermission.isAllowed(user,controller, action, instance);
			case ADMIN:
				return adminPermission.isAllowed(user,controller, action, instance);
			case ORGANIZATION_SUPERVISOR:
				return organizationSupervisorPermission.isAllowed(user,controller, action, instance);
			case ORGANIZATION_MEMBER:
				return organizationMemberPermission.isAllowed(user,controller, action, instance);
			case ORGANIZATION_ADMIN:
				return organizationAdminPermission.isAllowed(user,controller, action, instance);
			case USER:
				return userPermission.isAllowed(user,controller, action, instance);
			default:
				logger.trace("no role is match");
				return false;
				
		}
	}

}
