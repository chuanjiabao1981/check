package com.check.v3.security.permission.role;


import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Role;
import com.check.v3.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml","classpath:application-security-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestAdminPermission {

	@Autowired
	AdminPermission adminPermission;
	private User user;
	@Before
	public void init()
	{
		user = new User();
		user.setDefaultRole(Role.ADMIN);
	}
	@Test
	public void testAdminPermission()
	{
		assertTrue(adminPermission.isAllowed(user,"anyController", "anyAction", new Object()));
	}
	
}
