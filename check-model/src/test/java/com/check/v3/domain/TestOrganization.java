package com.check.v3.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
    private Organization o5 = null;
    private Organization o6 = null;
	@Before
	public void init()
	{
		 o1 = new Organization("o1",OrganizationType.NON_LEAF_NODE);
		 o2 = new Organization("o2",OrganizationType.NON_LEAF_NODE);
		 o3 = new Organization("o3",OrganizationType.NON_LEAF_NODE);
		 o4 = new Organization("o4",OrganizationType.LEAF_NODE);
		 o5 = new Organization("o5",OrganizationType.LEAF_NODE);
		 o6 = new Organization("o6",OrganizationType.LEAF_NODE);
	}
    
	@Test(expected = RuntimeException.class)
	public void testDirectRing()
	{
		o1.addSubOrganization(o1);
	}
	@Test(expected = RuntimeException.class)
	public void testParentRing()
	{
		o1.addSubOrganization(o2).addSubOrganization(o3);
		o3.addSubOrganization(o1);
	}
	@Test
	public void testAddOrganization()
	{
		o1.addSubOrganization(o2);
		assertTrue(o2.getParentOrganization().equals(o1));
	}
	
}
