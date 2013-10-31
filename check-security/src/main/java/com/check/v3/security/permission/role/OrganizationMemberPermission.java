package com.check.v3.security.permission.role;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionFilter;
import com.check.v3.service.LoadInstanceService;
import com.check.v3.service.UserService;

@Service("organizationMemberPermission")
public class OrganizationMemberPermission extends OrganizationUserSharePermission {

	@Autowired
	UserService userService;
	
	public OrganizationMemberPermission()
	{
		super();
		allow("UserController", "edit",  (LoadInstanceService)userService,  new PermissionFilter() {
				public boolean filter(Object o){
					User user 			= (User) o;
					User current_user 	= (User) SecurityUtils.getSubject();
					if (current_user.equals(user)){
						return true;
					}
					return false;
				}
		});
	}
}
