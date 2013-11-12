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
import com.check.v3.domain.OrganizationPost;
import com.check.v3.domain.OrganizationPostType;
import com.check.v3.domain.OrganizationType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestOrganizationPostService {
	
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private OrganizationPostService organizationPostService;
	private Long needTobeDeleteId = -1L;
	@Before
	public void init()
	{
		Organization o1 = new Organization("testdelte",OrganizationType.NON_LEAF_NODE);
		organizationService.save(o1);
		needTobeDeleteId = o1.getOrganizationPost(OrganizationPostType.ADMIN).getId();
	}
	@Test
	public void testDelete()
	{
		OrganizationPost op = organizationPostService.findById(needTobeDeleteId);
		assertNotNull(op);
	}
}
