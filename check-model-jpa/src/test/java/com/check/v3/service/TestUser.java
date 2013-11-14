package com.check.v3.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Department;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.service.exception.UserAccountDuplicateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestUser {
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private DepartmentService departmentService;

	
	private Department   department			 = null;

	@Before
	public void init()
	{
		

	}
	@Test(expected=UserAccountDuplicateException.class)
	public void testSameAccount() throws UserAccountDuplicateException 
	{
		department 				  = new Department("xxxxxxxx");
		department				  = departmentService.save(department);
		if (department == null){
			throw new RuntimeException("no departe if create");
		}
		System.err.println("1.---------------------------------------------");
		String account = "samesame";
		User user1 = new User();
		user1.setAccount(account);
		user1.setName("bigbigbig");
		user1.setPassword_cryp("1");
		user1.setDepartment(department);
		user1.setRole(Role.DEPARTMENT_ADMIN);
		User user2 = new User();
		user2.setAccount(account);
		user2.setName("bigbigbig");
		user2.setPassword_cryp("2");
		user2.setDepartment(department);
		user2.setRole(Role.DEPARTMENT_ADMIN);

		System.err.println("2.---------------------------------------------");
		System.err.println(user1.getDepartment().getId());
		userService.save(user1);
		userService.save(user2);
	}
	
}
