package com.check.v3.tools;

import com.check.v3.domain.OrganizationType;
import com.check.v3.domain.Organization;

public class Security {

	
	
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
	 * 
	 *    user1<----o1:SUPERVISOR
	 *    user2<----o2:ADMIN
	 *    user3<----o3:MEMBER
	 *    user4<----o6:MEMBER
	 */
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
	
	public static Organization getOrganization()
	{
		Organization o1 = new Organization("o1",OrganizationType.NON_LEAF_NODE);
		Organization o2 = new Organization("o2",OrganizationType.NON_LEAF_NODE);
		Organization o3 = new Organization("o3",OrganizationType.NON_LEAF_NODE);
		Organization o4 = new Organization("o4",OrganizationType.LEAF_NODE);
		Organization o5 = new Organization("o5",OrganizationType.LEAF_NODE);
		Organization o6 = new Organization("o6",OrganizationType.LEAF_NODE);
		o1.addSubOrganization(o2).addSubOrganization(o4);
		o1.addSubOrganization(o3).addSubOrganization(o3).addSubOrganization(o5);
		o3.addSubOrganization(o6);
		
		return null;
	}

}
