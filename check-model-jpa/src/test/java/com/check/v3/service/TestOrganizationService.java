package com.check.v3.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import com.check.v3.domain.exception.OrganizationRingException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestOrganizationService {
	
	@Autowired
	private OrganizationService organizationService;
	private final String TEST_ORG_NAME		 = "AA";
	private final String TEST_ORG_NOT_EXSIT  = "NOT_EXIST"; 
	private Organization o1;
	private Organization o2;
	private Organization o3;
	private Organization o4;
	
	@Before
	public void init()
	{
		
		Organization organization = new Organization(TEST_ORG_NAME,OrganizationType.LEAF_NODE);
		o1 						  = new Organization("o1",OrganizationType.NON_LEAF_NODE);
		o2 						  = new Organization("o2",OrganizationType.NON_LEAF_NODE);
		o3 						  = new Organization("o3",OrganizationType.LEAF_NODE);
		o4 						  = new Organization("o4",OrganizationType.NON_LEAF_NODE);
		o1.addSubOrganization(o2).addSubOrganization(o3);
		
		organizationService.save(organization);
	}
	
	@Test
	public void testSave1()
	{
		assertNotNull(organizationService.findByName(TEST_ORG_NAME));
	}
	@Test
	public void testSave2()
	{
		organizationService.save(o1);
		assertNotNull(organizationService.findByName("o2"));
		
	}
	@Test
	public void testFindByName()
	{
		assertNull(organizationService.findByName(TEST_ORG_NOT_EXSIT));
	}
	
	@Test(expected=OrganizationRingException.class)
	public void testAddSubOrganization()
	{
		organizationService.save(o1);
		organizationService.save(o4);

		/*
		 *   o1->o2->o3 
		 *   不能添加父节点为子节点
		 */
		Organization o3 = organizationService.findByName("o3");
		Organization o2 = organizationService.findByName("o2");
		assertTrue(o1.isContainOrganization(o3));
		assertFalse(o1.isContainOrganization(o4));
		assertNotNull(o3);
		assertNotNull(o2);
		assertNotNull(o3.getId());
		assertNotNull(o2.getId());
		o3.addSubOrganization(o1);
	}
	
}
