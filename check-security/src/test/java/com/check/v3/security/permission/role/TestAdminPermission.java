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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml","classpath:application-security-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestAdminPermission {

	@Autowired
	AdminPermission adminPermission;
	
	@Before
	public void init()
	{
	}
	@Test
	public void testAdminPermission()
	{
		assertTrue(adminPermission.isAllowed("anyController", "anyAction", new Object()));
	}
	
}
