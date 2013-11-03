package com.check.v3.security.permission.role;

import org.springframework.stereotype.Service;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionFilter;

@Service("organizationMemberPermission")
public class OrganizationMemberPermission extends OrganizationUserSharePermission {

		
	private OrganizationMemberPermissionFilter organizationMemberPermissionFilter = new OrganizationMemberPermissionFilter();
	public OrganizationMemberPermission()
	{
		super();
		allow(ControllerActionConstant.USER, ControllerActionConstant.EDIT,  organizationMemberPermissionFilter);
		allow(ControllerActionConstant.USER, ControllerActionConstant.UPDATE, organizationMemberPermissionFilter);
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
}
