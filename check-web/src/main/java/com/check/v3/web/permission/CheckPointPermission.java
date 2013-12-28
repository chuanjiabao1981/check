package com.check.v3.web.permission;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.check.v3.domain.CheckPoint;
import com.check.v3.domain.User;
import com.check.v3.security.permission.PermissionPolicy;
import com.check.v3.service.CheckPointService;

@Component("checkPointPermission")
public class CheckPointPermission implements PermissionPolicy{

	@Resource
	CheckPointService checkPointService;

	@Override
	public boolean filter(User user, Object instance) {
		CheckPoint checkPoint = (CheckPoint) instance;
		if (checkPoint != null && checkPoint.getDepartment() != null && checkPoint.getDepartment().equals(user.getDepartment())){
			return true;
		}
		return false;
	}

	@Override
	public Object getInstance(Long id) {
		return checkPointService.findById(id);
	}
	
	
}
