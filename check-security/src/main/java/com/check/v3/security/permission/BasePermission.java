package com.check.v3.security.permission;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.check.v3.domain.User;
import com.check.v3.security.permission.filter.GrantedPermissionFilter;
import com.check.v3.util.KeyUtils;

public class  BasePermission {
	private static final Logger logger = LoggerFactory.getLogger(BasePermission.class);

	
	private static final PermissionFilter 	 DEFAULT_PERMISSION_FILTER 				= new GrantedPermissionFilter();
	
	private Map<String,PermissionFilter> controllerActionPermissionFilter 			= new HashMap<String,PermissionFilter>();
	
	public PermissionFilter getPermissionFilter(String controller,String action)
	{
		PermissionFilter permissionFilter = controllerActionPermissionFilter.get(KeyUtils.buildKey(controller,action));
		if (permissionFilter == null)
			logger.trace("can't find controller [{}] action [{}] permissionFilter",controller,action);
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
		controllerActionPermissionFilter.put(KeyUtils.buildKey(controller,action), permissionFilter);
	}
	
		
}
