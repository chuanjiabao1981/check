package com.check.v3.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.check.v3.domain.exception.OrganizationRingException;

public class TestOrganization {
	/*          o4  
	 * 		  / 
	 *      o2
	 *    /
	 * o1       o5
	 *    \    /
	 *      o3
	 *         \
				o6
	 * 
	 * 
	 */
	
	private Organization o1 = null;
	private Organization o2 = null;
	private Organization o3 = null;
    private Organization o4 = null;
	@Before
	public void init()
	{
		 o1 = new Organization("o1",OrganizationType.NON_LEAF_NODE);
		 o2 = new Organization("o2",OrganizationType.NON_LEAF_NODE);
		 o3 = new Organization("o3",OrganizationType.NON_LEAF_NODE);
		 o4 = new Organization("o4",OrganizationType.LEAF_NODE);
	}
    
	@Test(expected = RuntimeException.class)
	public void testDirectRing()
	{
		o1.addSubOrganization(o1);
	}
	@Test(expected = OrganizationRingException.class)
	public void testParentRing()
	{
		o1.addSubOrganization(o2).addSubOrganization(o3);
		o3.addSubOrganization(o1);
	}
	@Test
	public void testAddOrganization()
	{
		o1.addSubOrganization(o2);
		o2.addSubOrganization(o3);
		assertTrue(o2.getParentOrganization().equals(o1));
		assertTrue(o1.isContainOrganization(o2));
		assertTrue(o1.isContainOrganization(o3));
		assertTrue(o1.getSubOrganizations().contains(o2));
		assertTrue(o2.getSubOrganizations().contains(o3));
		//测试反例
		assertFalse(o1.isContainOrganization(o4));
		//测试正例
		assertFalse(o1.getSubOrganizations().contains(o3));
	}
	@Test
	public void testEqual()
	{
		Organization o = new Organization("xx",OrganizationType.NON_LEAF_NODE);
		Organization p = new Organization("xx",OrganizationType.NON_LEAF_NODE); 
		assertTrue(o.equals(o));
		assertFalse(o.equals(null));
		assertFalse(o.equals(p));
		o.setId(10L);
		p.setId(10L);
		assertTrue(o.equals(p));
		p.setId(20L);
		assertFalse(o.equals(p));
	}
	@Test
	public void TestAddSubOrganization()
	{
		o1.addSubOrganization(o2);
		assertTrue(o2.getParentOrganization().equals(o1));
		assertTrue(o1.getSubOrganizations().contains(o2));
	}
	@Test
	public void TestSetParentOrganization()
	{
		o2.setParentOrganization(o1);
		assertTrue(o2.getParentOrganization().equals(o1));
		assertTrue(o1.getSubOrganizations().contains(o2));
	}
	@Test
	public void TestRemoveSubOrganization()
	{
		o1.addSubOrganization(o2);
		assertTrue(o1.getSubOrganizations().contains(o2));
		o1.removeSubOrganization(o2);
		assertFalse(o1.getSubOrganizations().contains(o2));
		assertNull(o2.getParentOrganization());
	}
	@Test
	public void TestSetDepartment()
	{
		Organization t = new Organization();
		Department   d = new Department();
		assertEquals(d.getOrganizations().size(),0);
		t.setDepartment(d);
		assertEquals(d.getOrganizations().size(),1);
		assertEquals(t.getDepartment(),d);
		assertTrue(d.getOrganizations().contains(t));
		

	}
	
}
