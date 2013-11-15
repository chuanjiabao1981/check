package com.check.v3.security.permission.role;


import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.check.v3.domain.User;
import com.check.v3.security.ControllerActionInstanceLoader;
import com.check.v3.security.SecurityConstant;
import com.check.v3.service.InstanceLoaderService;

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
		Object				current_instance		= getCurrentInstance(controller,action,instanceId);
		User				current_user			= null;
		if (httpServletRequest != null){
			httpServletRequest.setAttribute(SecurityConstant.CurrentInstance, current_instance);
		}else{
			logger.trace("httpServletRequest is null");
		}
		//1. Guest权限判断
		if (subject == null || subject.getPrincipal() == null){
			logger.trace("current user is Guest");
			return rolePermissionManager.isAllowed(null,controller, action, current_instance);
		}
		current_user = (User) subject.getPrincipal();
		return rolePermissionManager.isAllowed(current_user, controller, action, current_instance);
	}
	
	public Object getCurrentInstance(String controller,String action,Long instanceId)
	{
		InstanceLoaderService load_instance	 		= controllerActionInstanceLoader.getInstanceLoaderService(controller, action);
		Object				  current_instance		= null;
		if (load_instance !=null){
			if (instanceId != null){
				logger.trace("instance {} need to be loaded",instanceId);
				current_instance = load_instance.load(instanceId);
				if (current_instance != null){
					logger.trace("instace {} load success",instanceId);
					return current_instance;
				}else{
					logger.trace("instace {} load fail",instanceId);
				}	
			}
		}else{
			logger.trace(" load instance interface is not register");
		}
		return current_instance;

	}

}
