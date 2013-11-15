package com.check.v3.web.permission;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionPolicy;
import com.check.v3.web.controller.UserController;

@Service("departmentMemberPermissionSet")
public class DepartmentMemberPermissionSet extends DepartmentUserSharePermissionSet {

		
	private UserPermissionPolicy userPermissionFilter = new UserPermissionPolicy();
	public DepartmentMemberPermissionSet()
	{
		super();
		allow(UserController.class.getSimpleName(), ControllerActionConstant.EDIT,  userPermissionFilter);
		allow(UserController.class.getSimpleName(), ControllerActionConstant.UPDATE, userPermissionFilter);
	}
	
	
	public class UserPermissionPolicy implements PermissionPolicy
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
		this.rolepermissionManager.register(Role.DEPARTMENT_MEMEBER, this);
		
	}
}
