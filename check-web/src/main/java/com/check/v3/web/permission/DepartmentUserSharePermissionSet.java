package com.check.v3.web.permission;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.Organization;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionPolicy;
import com.check.v3.security.permission.PermissionSet;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.UserService;
import com.check.v3.web.controller.HomeController;
import com.check.v3.web.controller.QuickReportsController;
import com.check.v3.web.controller.UsersController;
@Component
public abstract class DepartmentUserSharePermissionSet extends PermissionSet{
	
	@Resource
	UserService userService;
	@Resource
	OrganizationService organizationService;
	public DepartmentUserSharePermissionSet()
	{
		super();
	}
	
	public void sharePermissionSet()
	{
		UserPermissionPolicy 			userpermissionPolicy 			= new UserPermissionPolicy(userService);
		QuickReportPermissionPolicy		quickReportPermissionPolicy		= new QuickReportPermissionPolicy();
		allow(HomeController.class.getSimpleName(),ControllerActionConstant.HOME);
		allow(UsersController.class.getSimpleName(),ControllerActionConstant.INDEX);
		allow(UsersController.class.getSimpleName(),ControllerActionConstant.EDIT,userpermissionPolicy);
		allow(UsersController.class.getSimpleName(), ControllerActionConstant.SHOW,userpermissionPolicy);
		allow(QuickReportsController.class.getSimpleName(),ControllerActionConstant.INDEX,quickReportPermissionPolicy);

	}
	
	public class UserPermissionPolicy implements PermissionPolicy
	{
		UserService userService;
		
		public UserPermissionPolicy(UserService userService)
		{
			this.userService = userService;
		}
		@Override
		public boolean filter(User current_user,Object current_instance) {
			User user = (User) current_instance;
			if (current_user != null && current_user.getDepartment().equals(user.getDepartment())){
				return true;
			}
			return false;
		}

		@Override
		public Object getInstance(Long id) {
			return userService.findById(id);
		}
		
	}
	public class QuickReportPermissionPolicy implements PermissionPolicy
	{

		@Override
		public boolean filter(User user, Object instance) {
			Organization o = (Organization) instance;
			if (o != null && o.getDepartment().equals(user.getDepartment())){
				return true;
			}
			return false;
		}

		@Override
		public Object getInstance(Long id) {
			return organizationService.findById(id);
		}
		
	}

}
