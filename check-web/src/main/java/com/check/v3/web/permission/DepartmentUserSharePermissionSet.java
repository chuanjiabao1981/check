package com.check.v3.web.permission;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionPolicy;
import com.check.v3.security.permission.PermissionSet;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.UserService;
import com.check.v3.web.controller.HomeController;
import com.check.v3.web.controller.UsersController;
import com.check.v3.web.controller.quickreport.QuickReportsCreateController;
import com.check.v3.web.controller.quickreport.QuickReportsEditController;
import com.check.v3.web.controller.quickreport.QuickReportsIndexController;
@Component
public abstract class DepartmentUserSharePermissionSet extends PermissionSet{
	
	@Resource
	UserService userService;
	@Resource
	OrganizationService organizationService;
	@Resource
	QuickReportService quickReportService;
	public DepartmentUserSharePermissionSet()
	{
		super();
	}
	
	public void sharePermissionSet()
	{
		UserPermissionPolicy 			userpermissionPolicy 			= new UserPermissionPolicy(userService);
		QuickReportPermissionPolicy		quickReportPermissionPolicy		= new QuickReportPermissionPolicy();
		OwnQuickReportPermissionPolicy  ownQuickReportPermissionPolicy	= new OwnQuickReportPermissionPolicy();
		allow(HomeController.class.getSimpleName(),ControllerActionConstant.HOME);
		allow(UsersController.class.getSimpleName(),ControllerActionConstant.INDEX);
		allow(UsersController.class.getSimpleName(),ControllerActionConstant.EDIT,userpermissionPolicy);
		allow(UsersController.class.getSimpleName(), ControllerActionConstant.SHOW,userpermissionPolicy);
		allow(QuickReportsIndexController.class.getSimpleName(),ControllerActionConstant.INDEX,quickReportPermissionPolicy);
		allow(QuickReportsCreateController.class.getSimpleName(),ControllerActionConstant.NEW,quickReportPermissionPolicy);
		allow(QuickReportsCreateController.class.getSimpleName(),ControllerActionConstant.CREATE,quickReportPermissionPolicy);
		allow(QuickReportsEditController.class.getSimpleName(),ControllerActionConstant.EDIT,ownQuickReportPermissionPolicy);
		allow(QuickReportsEditController.class.getSimpleName(),ControllerActionConstant.UPDATE,ownQuickReportPermissionPolicy);


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
	public class OwnQuickReportPermissionPolicy implements PermissionPolicy
	{

		@Override
		public boolean filter(User user, Object instance) {
			QuickReport q = (QuickReport) instance;
			if(q != null && q.getSubmitter().equals(user)){
				return true;
			}else{
				return false;
			}
		}

		@Override
		public Object getInstance(Long id) {
			System.err.println(id);
			return quickReportService.findById(id);
		}
		
	}

}
