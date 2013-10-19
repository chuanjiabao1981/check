package com.check.v3.security.permission;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.check.v3.domain.Affiliation;
import com.check.v3.domain.Organization;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.SecurityConstant;
import com.check.v3.security.permission.role.RolePermissionManager;
import com.check.v3.service.LoadInstanceService;

public class PermissionManager {
	private static final Logger logger = LoggerFactory.getLogger(PermissionManager.class);

	@Autowired
	private RolePermissionManager rolePermissionManager;
	
	public boolean isAllowed(HttpServletRequest httpServletRequest,String controller,String action,Long instanceId)
	{
		Subject 					subject 		= SecurityUtils.getSubject();
		LoadInstanceService load_instance	 		= BasePermission.getLoadInstanceService(controller, action);
		Object				current_instance		= null;
		User				current_user			= null;
		if (load_instance !=null){
			current_instance = load_instance.load(instanceId);
			httpServletRequest.setAttribute(SecurityConstant.CurrentInstance, current_instance);
		}
		//1. Guest权限判断
		if (subject == null || subject.getPrincipal() == null){
			return rolePermissionManager.isAllowed(Role.GUEST, controller, action, current_instance);
		}
		current_user = (User) subject.getPrincipal();
		//2. 用户默认权限判断(USER\ADMIN)
		if (!rolePermissionManager.isAllowed(current_user.getDefaultRole(), controller, action, current_instance)){
			//3. 用户在当前instance上的权限判断
			Affiliation 			affiliation 	= (Affiliation) current_instance;
			Set<Organization>		organizations	= null;
			if (affiliation != null){
				//3.1 当前instance归属的Organization集合
				organizations = affiliation.getBelongsToOrganizations();
				if (organizations == null){
					logger.trace("current instace dose not belongs to any organizations");
					return false;
				}
				//3.2 当前用户在instance的角色(ORGANIZATION_SUPERVISOR,ORGANIZATION_MEMBER)
				Role role							= getUserRoleFromOganizations(current_user, organizations);
				if (role == null){
					logger.trace("current user has not to any role on current instace");
					return false;
				}
				return rolePermissionManager.isAllowed(role,controller,action,current_instance);
			}
			
		}else{
			return true;
		}
		return false;
	}
	
	private Role getUserRoleFromOganizations(User user,Set<Organization> organizations)
	{
		//TODO::
		return Role.ORGANIZATION_SUPERVISOR;
	}

}