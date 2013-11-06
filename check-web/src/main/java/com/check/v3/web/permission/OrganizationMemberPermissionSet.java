package com.check.v3.web.permission;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.User;
import com.check.v3.security.Role;
import com.check.v3.security.permission.PermissionFilter;
import com.check.v3.web.controller.UserController;

@Service("organizationMemberPermissionSet")
public class OrganizationMemberPermissionSet extends OrganizationUserSharePermissionSet {

		
	private OrganizationMemberPermissionFilter organizationMemberPermissionFilter = new OrganizationMemberPermissionFilter();
	public OrganizationMemberPermissionSet()
	{
		super();
		allow(UserController.class.getSimpleName(), ControllerActionConstant.EDIT,  organizationMemberPermissionFilter);
		allow(UserController.class.getSimpleName(), ControllerActionConstant.UPDATE, organizationMemberPermissionFilter);
	}
	
	
	public class OrganizationMemberPermissionFilter implements PermissionFilter
	{

		@Override
		public boolean filter(User current_user,Object current_instance) {
			if (current_user.equals(current_instance)){
				return true;
			}
			return false;
		}
		
	}


	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.ORGANIZATION_MEMBER, this);
		
	}
}
