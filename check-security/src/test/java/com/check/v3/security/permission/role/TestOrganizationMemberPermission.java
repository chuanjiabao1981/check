package com.check.v3.security.permission.role;

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

import com.check.v3.domain.User;
import com.check.v3.security.ControllerActionConstant;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml","classpath:application-security-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestOrganizationMemberPermission 
{
	
	@Autowired
	OrganizationMemberPermission organizationMemberPermission;

	private User current_user;
	private User other_user;
	@Before
	public void init()
	{
		current_user = new User("account_1");
		other_user 	 = new User("account_2");
	}
	
	@Test
	public void testIsAllowed()
	{
		assertTrue(organizationMemberPermission.isAllowed(current_user, ControllerActionConstant.USER,  ControllerActionConstant.EDIT, current_user));
		assertFalse(organizationMemberPermission.isAllowed(current_user, ControllerActionConstant.USER, ControllerActionConstant.EDIT, other_user));
		assertTrue(organizationMemberPermission.isAllowed(current_user, ControllerActionConstant.USER,  ControllerActionConstant.UPDATE,current_user));
		assertFalse(organizationMemberPermission.isAllowed(current_user, ControllerActionConstant.USER, ControllerActionConstant.UPDATE,other_user));
	}
}
