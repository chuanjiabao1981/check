package com.check.v3.web.permission;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.Organization;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionPolicy;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.UserService;
import com.check.v3.web.controller.OrganizationsController;
import com.check.v3.web.controller.UsersController;


@Service("departmentAdminPermissionSet")
public class DepartmentAdminPermissionSet extends DepartmentUserSharePermissionSet {
	
	@Resource
	OrganizationService organizationService;
	@Resource
	UserService			userService;
	
	public DepartmentAdminPermissionSet()
	{
		super();
	}


	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.DEPARTMENT_ADMIN, this);
		
		super.sharePermissionSet();
		
		OrganizationPermissionPolicy organizationPermissionPolicy = new OrganizationPermissionPolicy(organizationService);
		UserPermissionPolicy         userPermissionPolicy		  = new UserPermissionPolicy();
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.INDEX);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.NEW);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.CREATE);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.DESTORY,organizationPermissionPolicy);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.EDIT,organizationPermissionPolicy);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.UPDATE,organizationPermissionPolicy);
		this.allow(UsersController.class.getSimpleName(), ControllerActionConstant.EDIT,userPermissionPolicy);
		this.allow(UsersController.class.getSimpleName(), ControllerActionConstant.UPDATE,userPermissionPolicy);
	}
	
	public class OrganizationPermissionPolicy implements  PermissionPolicy
	{
		private OrganizationService organizationService;
		public OrganizationPermissionPolicy(OrganizationService organizationService)
		{
			this.organizationService = organizationService;
		}
		@Override
		public boolean filter(User user, Object instance) {
			if (instance != null){
				Organization o = (Organization) instance;
				if (o.getDepartment().equals(user.getDepartment())){
					return true;
				}
			}
			return false;
		}

		@Override
		public Object getInstance(Long id) {
			return organizationService.findById(id);
		}
		
	}
	public class UserPermissionPolicy implements PermissionPolicy
	{
;
		@Override
		public boolean filter(User user, Object instance) {
			if(instance != null){
				User u = (User) instance;
				if (u.getDepartment().equals(user.getDepartment())){
					return true;
				}
			}
			return false;
		}

		@Override
		public Object getInstance(Long id) {
			return userService.findById(id);
		}
		
	}
}
