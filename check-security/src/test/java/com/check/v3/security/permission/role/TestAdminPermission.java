package com.check.v3.security.permission.role;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
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
	}
	
}