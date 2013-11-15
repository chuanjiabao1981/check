package com.check.v3.domain.test.util;

import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationType;

public class OrganizationTreeBuilder {
	
	
	
	/*
	 * 
	 * 			 o3
	 * 			/
	 * 		  o2
	 * 		/	\
	 *   o1		  o4
	 * 		\
	 * 		  o5
	 * 				
	 */

	
	private Organization o1;
	private Organization o2;
	private Organization o3;
	private Organization o4;
	private Organization o5;
	
	public Organization buildTree(Department deparmtent,String namePrefix)
	{
		o1 = new Organization(deparmtent,namePrefix+"o1",OrganizationType.NON_LEAF_NODE);
		o2 = new Organization(deparmtent,namePrefix+"o2",OrganizationType.NON_LEAF_NODE);
		o3 = new Organization(deparmtent,namePrefix+"o3",OrganizationType.NON_LEAF_NODE);
		o4 = new Organization(deparmtent,namePrefix+"o4",OrganizationType.NON_LEAF_NODE);
		o5 = new Organization(deparmtent,namePrefix+"o5",OrganizationType.NON_LEAF_NODE);
		o1.addSubOrganization(o2).addSubOrganization(o3);
		o2.addSubOrganization(o4);
		o1.addSubOrganization(o5);
		return o1;
	}
	public Organization buildTree(Department department)
	{
		return buildTree(department,"");
	}
	
}
