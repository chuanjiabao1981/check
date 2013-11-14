package com.check.v3.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestDepartment {

	
	
	@Test
	public void TestAddOrganization()
	{
		Department 	 department = new Department();
		Organization organization = new Organization();
		assertEquals(department.getOrganizations().size(),0);
		department.addOrganization(organization);
		assertEquals(department.getOrganizations().size(),1);
		assertTrue(department.getOrganizations().contains(organization));
		assertEquals(department,organization.getDepartment());
	}
	
	@Test
	public void TestRemoveSingleOrganization()
	{
		Department department = new Department();
		Organization organization = new Organization();
		department.addOrganization(organization);
		assertEquals(department.getOrganizations().size(),1);
		department.removeOrganization(organization);
		assertEquals(department.getOrganizations().size(),0);
	}
	
	@Test
	public void TestRemoveOrganizationTree()
	{
		Department department = new Department();

		Organization o1 = new Organization(department);
		Organization o2 = new Organization(department);
		Organization o3 = new Organization(department);
		Organization o4 = new Organization(department);
		
		/*
		 * o1->o2->o3
		 * 		\
		 * 		 o4
		 */
		o1.addSubOrganization(o2).addSubOrganization(o3);
		o2.addSubOrganization(o4);
		assertEquals(department.getOrganizations().size(),4);
		department.removeOrganization(o2);
		assertEquals(department.getOrganizations().size(),1);

	}
	
	@Test
	public void testEqual()
	{
		Department o = new Department();
		Department p = new Department(); 
		assertTrue(o.equals(o));
		assertFalse(o.equals(null));
		assertFalse(o.equals(p));
		o.setId(10L);
		p.setId(10L);
		assertTrue(o.equals(p));
		p.setId(20L);
		assertFalse(o.equals(p));
	}
	
	


}
