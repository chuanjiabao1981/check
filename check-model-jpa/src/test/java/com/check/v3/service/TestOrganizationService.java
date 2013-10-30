package com.check.v3.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestOrganizationService {
	
	@Autowired
	private OrganizationService organizationService;
	private final String TEST_ORG_NAME = "AA";
	
	@Before
	public void init()
	{
		
		Organization organization = new Organization(TEST_ORG_NAME,OrganizationType.LEAF_NODE);
		organizationService.save(organization);
	}
	
	@Test
	public void testSSS()
	{
		assertNotNull(organizationService.findByName(TEST_ORG_NAME));
	}
}
