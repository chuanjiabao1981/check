package com.check.v3.security.permission.role;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.check.v3.domain.Affiliation;
import com.check.v3.domain.OrganizationPost;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.ControllerActionInstanceLoader;
import com.check.v3.security.SecurityConstant;
import com.check.v3.service.InstanceLoaderService;
import com.check.v3.domain.Organization;

@Service("permissionManager")
public class PermissionManager {
	private static final Logger logger = LoggerFactory.getLogger(PermissionManager.class);

	@Autowired
	private ControllerActionInstanceLoader controllerActionInstanceLoader;
	
	@Autowired
	private RolePermissionManager rolePermissionManager;
	

	
	
	public boolean isAllowed(HttpServletRequest httpServletRequest,String controller,String action,Long instanceId)
	{
		Subject 					subject 		= SecurityUtils.getSubject();
		InstanceLoaderService load_instance	 		= controllerActionInstanceLoader.getInstanceLoaderService(controller, action);
		Object				current_instance		= null;
		User				current_user			= null;
		if (load_instance !=null){
			if (instanceId != null){
				logger.trace("instance {} need to be loaded",instanceId);
				current_instance = load_instance.load(instanceId);
				if (current_instance != null){
					logger.trace("instace {} load success",instanceId);
					if (httpServletRequest != null){
						httpServletRequest.setAttribute(SecurityConstant.CurrentInstance, current_instance);
					}else{
						logger.trace("httpServletRequest is null");
					}
				}else{
					logger.trace("instace {} load fail",instanceId);
				}	
			}
		}else{
			logger.trace(" load instance interface is not register");
		}
		//1. Guest权限判断
		if (subject == null || subject.getPrincipal() == null){
			logger.trace("current user is Guest");
			logger.trace(controller);
			logger.trace(action);
			return rolePermissionManager.isAllowed(current_user,Role.GUEST, controller, action, current_instance);
		}
		current_user = (User) subject.getPrincipal();
		//2. 用户默认权限判断(USER\ADMIN)
		if (!rolePermissionManager.isAllowed(current_user,current_user.getDefaultRole(), controller, action, current_instance)){
			logger.trace("default permission is not allowed do the job");
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
				//3.2 当前用户在instance的角色(ORGANIZATION_SUPERVISOR,ORGANIZATION_MEMBER,ORGANIZATION_ADMIN)
				Role role							= getUserRoleFromOganizations(current_user, organizations);
				if (role == null){
					logger.trace("current user has not to any role on current instace");
					return false;
				}
				return rolePermissionManager.isAllowed(current_user,role,controller,action,current_instance);
			}
			
		}else{
			return true;
		}
		return false;
	}
	
	public Role getUserRoleFromOganizations(User user,Set<Organization> organizations)
	{
		//1. 用户直接所属机构以及它的下属机构和organizations 是否有交集
		//2. 如果有，则返回用户直接所在机构的角色
		for(OrganizationPost post:user.getOrganizationPosts()){
			for(Organization organization: organizations){
				if (post.getOrganization().isContainOrganization(organization)){
					switch(post.getType()){
						case SUPERVISOR:
							return Role.ORGANIZATION_SUPERVISOR;
						case MEMEBER:
							return Role.ORGANIZATION_MEMBER;
						case ADMIN:
							return Role.ORGANIZATION_ADMIN;
						default :
							throw new RuntimeException("crazy man!");
					}
				}
			}
		}
		return null;
	}

}
