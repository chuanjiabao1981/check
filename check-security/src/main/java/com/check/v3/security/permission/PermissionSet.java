package com.check.v3.security.permission;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.check.v3.domain.User;
import com.check.v3.security.permission.policy.GrantedPermission;
import com.check.v3.security.permission.role.RolePermissionManager;
import com.check.v3.util.KeyUtils;

public abstract class   PermissionSet {
	
	
	private static final Logger logger = LoggerFactory.getLogger(PermissionSet.class);

	private static final PermissionPolicy 	 DEFAULT_PERMISSION_FILTER 				= new GrantedPermission();
	
	private Map<String,PermissionPolicy> controllerActionPermissionFilter 			= new HashMap<String,PermissionPolicy>();
	
	@Autowired
	protected RolePermissionManager rolepermissionManager;
	@PostConstruct
	public abstract void register();
	
	
	public PermissionPolicy getPermissionFilter(String controller,String action)
	{
		PermissionPolicy permissionFilter = controllerActionPermissionFilter.get(KeyUtils.buildKey(controller,action));
		if (permissionFilter == null)
			logger.trace("can't find controller [{}] action [{}] permissionFilter",controller,action);
		return permissionFilter;
	}
	public boolean isAllowed(User user,String controller,String action,Object instance)
	{
		PermissionPolicy permissionFilter = getPermissionFilter(controller,action);
		if (permissionFilter == null){
			return false;
		}
		return permissionFilter.filter(user,instance);
	}
	public boolean isAllowed(User user,String controller,String action)
	{
		PermissionPolicy permissionFilter = getPermissionFilter(controller,action);
		if (permissionFilter == null){
			return false;
		}
		return permissionFilter.filter(user,null);
	}
	
	protected void allow(String controller,String action,PermissionPolicy permissionFilter)
	{
		addPermissionFilter(controller,action,permissionFilter);
	}
	protected void allow(String controller,String action)
	{
		logger.trace("allow controller["+controller+"] action["+action+"]");
		allow(controller,action,DEFAULT_PERMISSION_FILTER);
	}
		
	private void addPermissionFilter(String controller,String action,PermissionPolicy permissionFilter)
	{
		logger.trace("add PermissionFilter{} for controller[{}] action[{}] ",new Object[]{permissionFilter,controller,action});
		controllerActionPermissionFilter.put(KeyUtils.buildKey(controller,action), permissionFilter);
	}
	
		
}
