package com.check.v3.web.permission;



import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionPolicy;
import com.check.v3.security.permission.PermissionSet;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.QuickReportResolveService;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.UserService;
import com.check.v3.web.controller.HomeController;
import com.check.v3.web.controller.UsersController;
import com.check.v3.web.controller.checktemplate.CheckTemplatesCreateController;
import com.check.v3.web.controller.checktemplate.CheckTemplatesIndexController;
import com.check.v3.web.controller.checktemplate.CheckTemplatesShowController;
import com.check.v3.web.controller.quickreport.QuickReportsCreateController;
import com.check.v3.web.controller.quickreport.QuickReportsEditController;
import com.check.v3.web.controller.quickreport.QuickReportsIndexController;
import com.check.v3.web.controller.quickreport.QuickReportsShowController;
import com.check.v3.web.controller.quickreport.resolve.QuickReportResolvesCreateController;
import com.check.v3.web.controller.quickreport.resolve.QuickReportResolvesEditController;
@Component
public abstract class DepartmentUserSharePermissionSet extends PermissionSet{
	
	@Resource
	UserService userService;
	@Resource
	OrganizationService organizationService;
	@Resource
	QuickReportService quickReportService;
	@Resource
	QuickReportResolveService quickReportResolveService;
	public DepartmentUserSharePermissionSet()
	{
		super();
	}
	
	public void sharePermissionSet()
	{
		UserPermissionPolicy 						userpermissionPolicy 				= new UserPermissionPolicy(userService);
		QuickReportPermissionPolicy					quickReportPermissionPolicy			= new QuickReportPermissionPolicy();
		OwnQuickReportPermissionPolicy  			ownQuickReportPermissionPolicy		= new OwnQuickReportPermissionPolicy();
		QuickReportPermissionPolicy2				quickReportPermissionPolicy2		= new QuickReportPermissionPolicy2();
		QuickReportResolveNewPermissionPolicy		quickReportResolveNewPermission		= new QuickReportResolveNewPermissionPolicy();
		QuickReportResolveEditPermissionPolicy		quickReportResolveEditPermission	= new QuickReportResolveEditPermissionPolicy();
		allow(HomeController.class.getSimpleName(),ControllerActionConstant.HOME);
		allow(UsersController.class.getSimpleName(),ControllerActionConstant.INDEX);
		allow(UsersController.class.getSimpleName(),ControllerActionConstant.EDIT,userpermissionPolicy);
		allow(UsersController.class.getSimpleName(), ControllerActionConstant.SHOW,userpermissionPolicy);
		allow(QuickReportsIndexController.class.getSimpleName(),ControllerActionConstant.INDEX,quickReportPermissionPolicy);
		allow(QuickReportsCreateController.class.getSimpleName(),ControllerActionConstant.NEW,quickReportPermissionPolicy);
		allow(QuickReportsCreateController.class.getSimpleName(),ControllerActionConstant.CREATE,quickReportPermissionPolicy);
		allow(QuickReportsEditController.class.getSimpleName(),ControllerActionConstant.EDIT,ownQuickReportPermissionPolicy);
		allow(QuickReportsEditController.class.getSimpleName(),ControllerActionConstant.UPDATE,ownQuickReportPermissionPolicy);
		allow(QuickReportsShowController.class.getSimpleName(),ControllerActionConstant.SHOW,quickReportPermissionPolicy2);
		allow(QuickReportResolvesCreateController.class.getSimpleName(),ControllerActionConstant.NEW,quickReportResolveNewPermission);
		allow(QuickReportResolvesCreateController.class.getSimpleName(),ControllerActionConstant.CREATE,quickReportResolveNewPermission);
		allow(QuickReportResolvesEditController.class.getSimpleName(),ControllerActionConstant.EDIT,quickReportResolveEditPermission);
		allow(QuickReportResolvesEditController.class.getSimpleName(),ControllerActionConstant.UPDATE,quickReportResolveEditPermission);
		allow(QuickReportResolvesEditController.class.getSimpleName(),ControllerActionConstant.DESTORY,quickReportResolveEditPermission);
		allow(CheckTemplatesIndexController.class.getSimpleName(),ControllerActionConstant.INDEX);
				//简单处理
		allow(CheckTemplatesShowController.class.getSimpleName(),ControllerActionConstant.SHOW);


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
	public class QuickReportPermissionPolicy2 implements PermissionPolicy
	{

		@Override
		public boolean filter(User user, Object instance) {
			QuickReport q = (QuickReport) instance;
			if (q!=null && q.getDepartment().equals(user.getDepartment())){
				return true;
			}
			return false;
		}

		@Override
		public Object getInstance(Long id) {
			return quickReportService.findById(id);
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
			return quickReportService.findById(id);
		}
		
	}
	public class QuickReportResolveEditPermissionPolicy implements PermissionPolicy
	{

		@Override
		public boolean filter(User user, Object instance) {
			QuickReportResolve quickReportResolve = (QuickReportResolve) instance;
			if (quickReportResolve != null && 
				user !=null && 
				quickReportResolve.getSubmitter() != null &&
				quickReportResolve.getSubmitter().equals(user)){
				return true;
			}
				
			return false;
		}

		@Override
		public Object getInstance(Long id) {
			return quickReportResolveService.findById(id);
		}
		
	}
	public class QuickReportResolveNewPermissionPolicy implements PermissionPolicy
	{

		@Override
		public boolean filter(User user, Object instance) {
			QuickReport q = (QuickReport) instance;
			if(q!=null && q.getDepartment() != null && q.getDepartment().equals(user.getDepartment())){
				return true;
			}
			return false;
		}

		@Override
		public Object getInstance(Long id) {
			return quickReportService.findById(id);
		}
		
	}

}
