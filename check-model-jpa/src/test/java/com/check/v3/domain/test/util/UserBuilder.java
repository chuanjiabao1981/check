package com.check.v3.domain.test.util;

import com.check.v3.domain.Department;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;

public class UserBuilder {
	
	
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

}
