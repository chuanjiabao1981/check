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
import com.check.v3.web.controller.OrganizationsController;


@Service("departmentAdminPermissionSet")
public class DepartmentAdminPermissionSet extends DepartmentUserSharePermissionSet {
	
	@Resource
	OrganizationService organizationService;
	
	public DepartmentAdminPermissionSet()
	{
		super();
	}


	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.DEPARTMENT_ADMIN, this);
		OrganizationPermissionPolicy organizationPermissionPolicy = new OrganizationPermissionPolicy(organizationService);
		
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.INDEX);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.NEW);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.CREATE);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.DESTORY,organizationPermissionPolicy);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.EDIT,organizationPermissionPolicy);
		this.allow(OrganizationsController.class.getSimpleName(), ControllerActionConstant.UPDATE,organizationPermissionPolicy);
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
}
