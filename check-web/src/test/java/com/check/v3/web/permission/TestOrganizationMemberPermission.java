package com.check.v3.web.permission;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.ControllerActionConstant;
import com.check.v3.domain.User;
import com.check.v3.web.controller.HomeController;
import com.check.v3.web.controller.UsersController;
import com.check.v3.web.permission.DepartmentMemberPermissionSet;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml","classpath:application-web-role-permission.xml","classpath:application-web-security-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestOrganizationMemberPermission 
{
	
	@Autowired
	DepartmentMemberPermissionSet organizationMemberPermission;

	private User current_user;
	private User other_user;
	@Before
	public void init()
	{
		current_user = new User("account_1");
		other_user 	 = new User("account_2");
	}
	
	@Test
	public void testIsAllowedUser()
	{
		assertTrue(organizationMemberPermission.isAllowed(current_user, UsersController.class.getSimpleName(),  ControllerActionConstant.EDIT, current_user));
		assertFalse(organizationMemberPermission.isAllowed(current_user, UsersController.class.getSimpleName(), ControllerActionConstant.EDIT, other_user));
		assertTrue(organizationMemberPermission.isAllowed(current_user, UsersController.class.getSimpleName(),  ControllerActionConstant.UPDATE,current_user));
		assertFalse(organizationMemberPermission.isAllowed(current_user, UsersController.class.getSimpleName(), ControllerActionConstant.UPDATE,other_user));
	}
	@Test
	public void testIsAllowedHome()
	{
		assertTrue(organizationMemberPermission.isAllowed(current_user, HomeController.class.getSimpleName(), ControllerActionConstant.HOME));
	}
}
