package com.check.v3.security.permission.role;

import java.util.List;
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
import com.check.v3.domain.User;
import com.check.v3.security.ControllerActionInstanceLoader;
import com.check.v3.security.Role;
import com.check.v3.security.SecurityConstant;
import com.check.v3.security.util.SecurityTools;
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
			return rolePermissionManager.isAllowed(current_user,Role.GUEST, controller, action, current_instance);
		}
		current_user = (User) subject.getPrincipal();
		
		
		//2. 如果访问的是集合，则没有instance(例如：users的index)
		//   那么就确定当前用户设定的集合是什么角色
		if (current_instance == null){
			Organization o = SecurityTools.getCurrentOrganization();
			if (o != null){
				// 2.1 判断用户在当前organization上的角色
				Role role		= getUserRoleFromOrganization(current_user, o);
				if (role != null){
					return rolePermissionManager.isAllowed(current_user,role,controller,action,current_instance);
				}else{
					return false;
				}
			}else{
				//无法计算当前organization
				logger.warn("Current User does not belong to any organization,please check the database!");
				return false;
			}
		}
		
		
		
		//3. 用户在当前instance上的权限判断
		Affiliation 			affiliation 	= (Affiliation) current_instance;
		List<Organization>		organizations	= null;
		//3.1 当前instance归属的Organization集合
		organizations = affiliation.getBelongsToOrganizations();
		if (organizations == null){
			//理论上不应该出现这种类型
			logger.error("current instace dose not belongs to any organizations");
			return false;
		}
		//3.2 当前用户在instance的角色(ORGANIZATION_SUPERVISOR,ORGANIZATION_MEMBER,ORGANIZATION_ADMIN)
		Role role							= getUserRoleFromOganizations(current_user, organizations);
		if (role == null){
			logger.warn("current user has not to any role on current instace");
			return false;
		}
		return rolePermissionManager.isAllowed(current_user,role,controller,action,current_instance);
	}
	
	public Role getUserRoleFromOganizations(User user,List<Organization> organizations)
	{
		//1. 用户直接所属机构以及它的下属机构和organizations 是否有交集
		//2. 如果有，则返回用户直接所在机构的角色
		
		//TODO::找到交集中权限最大的返回
		for(Organization organization:organizations){
			Role r = getUserRoleFromOrganization(user,organization);
			if (r != null){
				return r;
			}
		}
		return null;
	}
	
	private Role getUserRoleFromOrganization(User user,Organization organization)
	{
		for(OrganizationPost post:user.getOrganizationPosts()){
			if (post.getOrganization().isContainOrganization(organization)){
				switch(post.getType()){
					case SUPERVISOR:
						return Role.ORGANIZATION_SUPERVISOR;
					case MEMEBER:
						return Role.ORGANIZATION_MEMBER;
					case ADMIN:
						return Role.ORGANIZATION_ADMIN;
				}
			}
		}
		return null;
	}

}
