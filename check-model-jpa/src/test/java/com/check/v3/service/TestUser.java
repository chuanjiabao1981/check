package com.check.v3.service;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationType;
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
		
		department 				  = new Department("xxxxxxxx");
		department				  = departmentService.save(department);
		if (department == null){
			throw new RuntimeException("no departe if create");
		}
	}
	@Test(expected=UserAccountDuplicateException.class)
	public void testSameAccount() throws UserAccountDuplicateException 
	{
		
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

		System.err.println(user1.getDepartment().getId());
		userService.save(user1);
		userService.save(user2);
	}
	@Test
	public void testUserAddOrganization()
	{
		String user_account 		= "aaaaaaaaaaa";
		String organization_name	= "ddddddd";
		userService.save(buildUser(user_account,department));
		organizationService.save(buildOrganization(organization_name,department));
		
		User 			user = userService.findByAccount(user_account);
		Organization	org  = organizationService.findByName(organization_name);
		
		user.addOrganization(org);
		userService.save(user);
		User 			user2 = userService.findByAccount(user_account);

		assertEquals(user2.getOrganizations().size(),1);
	}
	@Test
	public void testUserRemoveOrganization()
	{
		String user_account 		= "aaaaaaaaaaa";
		String organization_name	= "ddddddd";
		userService.save(buildUser(user_account,department));
		organizationService.save(buildOrganization(organization_name,department));
		
		User 			user = userService.findByAccount(user_account);
		Organization	org  = organizationService.findByName(organization_name);
		
		user.addOrganization(org);
	
		assertEquals(user.getOrganizations().size(),1);
		assertEquals(userService.findByAccount(user_account).getOrganizations().size(),1);
		user.removeOrganization( organizationService.findByName(organization_name));
		User l = userService.save(user);
		assertEquals(l.getOrganizations().size(),0);
		assertEquals(userService.findByAccount(user_account).getOrganizations().size(),0);
	}
	
	
	private User buildUser(String user_account,Department department)
	{
		String user_name		= user_account;
		String user_password	= "1";
		User user = new User();
		user.setName(user_name);
		user.setAccount(user_account);
		user.setPassword(user_password);
		user.setDepartment(department);
		user.setRole(Role.DEPARTMENT_ADMIN);
		return user;
	}
	
	private Organization buildOrganization(String name,Department department)
	{
		Organization organization 	= new Organization(name,OrganizationType.NON_LEAF_NODE);
		organization.setDepartment(department);
		return organization;
	}
}
