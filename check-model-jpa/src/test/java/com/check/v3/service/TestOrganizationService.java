package com.check.v3.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestOrganizationService {
	
//	@Autowired
//	private OrganizationService organizationService;
	
	@Before
	public void init()
	{
		
	}
	
	@Test
	public void testSSS()
	{
		
	}
}
