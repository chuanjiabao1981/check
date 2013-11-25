package com.check.v3.domain.test.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.v3.domain.Department;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.service.UserService;

@Service("userBuilderServiceTest")
public class UserBuilder {
	
	@Resource
	UserService userService;
	public User build(Department deparment,String namePrefix)
	{
		User user = new User();
		user.setAccount(namePrefix+"____account");
		user.setName(namePrefix+"____name");
		user.setPassword(namePrefix+"xxxxxxxxx");
		user.setRole(Role.DEPARTMENT_ADMIN);
		user.setDepartment(deparment);
		return user;
	}

	public User create(Department department,String namePrefix)
	{
		return userService.save((build(department,namePrefix)));
	}
}
