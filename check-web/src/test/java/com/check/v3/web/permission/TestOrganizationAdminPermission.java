package com.check.v3.web.permission;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml","classpath:application-web-role-permission.xml","classpath:application-web-security-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestOrganizationAdminPermission {
	@Autowired
	DepartmentAdminPermissionSet organizationAdminPermissionSet;
	User user ;
	@Before
	public void init()
	{
		user = new User();
	}
	@Test
	public void testAdminPermision()
	{
		assertTrue(organizationAdminPermissionSet.isAllowed(user, "any controller", "any action","any instance"));
		assertTrue(organizationAdminPermissionSet.isAllowed(user, "any controller", "any action"));
	}
}
