package com.check.v3.security.permission.role;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionSet;

@Service("sysAdminPermissionSet")
public class SysAdminPermissionSet extends PermissionSet {
	
	
	public boolean isAllowed(User user,String controller,String action)
	{
		return true;
	}
	public boolean isAllowed(User user,String controller,String action,Object instance)
	{
		return true;
	}


	@Override
	@PostConstruct
	public void register() {
		this.rolepermissionManager.register(Role.SYS_ADMIN, this);
	}

}
