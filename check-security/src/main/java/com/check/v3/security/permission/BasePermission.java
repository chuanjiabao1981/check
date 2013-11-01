package com.check.v3.security.permission;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.check.v3.domain.User;
import com.check.v3.security.permission.filter.GrantedPermissionFilter;
import com.check.v3.service.LoadInstanceService;

public class  BasePermission {
	private static final Logger logger = LoggerFactory.getLogger(BasePermission.class);

	
	private static final PermissionFilter 	 DEFAULT_PERMISSION_FILTER 				= new GrantedPermissionFilter();
	private static Map<String,LoadInstanceService> ControllerActionLoadInstance 	= new HashMap<String,LoadInstanceService>();
	
	private Map<String,PermissionFilter> controllerActionPermissionFilter 			= new HashMap<String,PermissionFilter>();
	
	public static LoadInstanceService getLoadInstanceService(String controller,String action)
	{
		LoadInstanceService loadInstanceService = ControllerActionLoadInstance.get(getKey(controller,action));
		if (loadInstanceService == null)
			logger.trace("can't find controller {} action {} loadInstanceService",controller,action);
		return loadInstanceService;
	}
	public PermissionFilter getPermissionFilter(String controller,String action)
	{
		PermissionFilter permissionFilter = controllerActionPermissionFilter.get(getKey(controller,action));
		if (permissionFilter == null)
			logger.trace("can't find controller {} action {} permissionFilter",controller,action);
		return permissionFilter;
	}
	public boolean isAllowed(User user,String controller,String action,Object instance)
	{
		PermissionFilter permissionFilter = getPermissionFilter(controller,action);
		if (permissionFilter == null){
			return false;
		}
		return permissionFilter.filter(user,instance);
	}
	public boolean isAllowed(User user,String controller,String action)
	{
		PermissionFilter permissionFilter = getPermissionFilter(controller,action);
		if (permissionFilter == null){
			return false;
		}
		return permissionFilter.filter(user,null);
	}
	protected void addLoadInstance(String controller,String action,LoadInstanceService loadInstance)
	{
		logger.trace("add LoadInstance{} for controller{} action{} ",new Object[]{loadInstance,controller,action});
		ControllerActionLoadInstance.put(getKey(controller,action),loadInstance);
	}
	protected void allow(String controller,String action,PermissionFilter permissionFilter)
	{
		addPermissionFilter(controller,action,permissionFilter);
	}
	protected void allow(String controller,String action)
	{
		allow(controller,action,DEFAULT_PERMISSION_FILTER);
	}
		
	private void addPermissionFilter(String controller,String action,PermissionFilter permissionFilter)
	{
		logger.trace("add PermissionFilter{} for controller{} action{} ",new Object[]{permissionFilter,controller,action});
		controllerActionPermissionFilter.put(getKey(controller,action), permissionFilter);
	}
	
	private static String getKey(String controller,String action)
	{
		return controller+":"+action;
	}
	
}
