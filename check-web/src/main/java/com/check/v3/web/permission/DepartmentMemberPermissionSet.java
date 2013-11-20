package com.check.v3.web.permission;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionPolicy;
import com.check.v3.service.UserService;
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
		@Resource
		UserService userService;
		@Override
		public boolean filter(User current_user,Object current_instance) {
			if (current_user.equals(current_instance)){
				return true;
			}
			return false;
		}

		@Override
		public Object getInstance(Long id) {
			return userService.findById(id);
		}
		
	}


	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.DEPARTMENT_MEMEBER, this);
		
	}
}
