package com.check.v3.security.permission.role;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationPostType;
import com.check.v3.domain.OrganizationType;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.UserService;
import com.google.common.collect.Sets;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml","classpath:application-security-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestRolePermissionManager {
	
	
	
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	
	private PermissionManager permissionManager;
	
	private User user_o3;
	private User user_o2;
	private User user_o5;
	
	private Organization o1;
	private Organization o2;
	private Organization o3;
	private Organization o4;
	private Organization o5;
	private Organization o6;
	
	
	/*		   o4
	 * 		  /
	 * 		 o2
	 *  	/
	 *    o1	o5
	 *    	\  /
	 *    	 o3
	 *    	   \
	 *    		o6
	 *    
	 *    user_o3 在o3 上
	 *    user_o2 在o2 上
	 *    user_o5 在o5 上
	 * 
	 */
	@Before
	public void init()
	{
		o1 = new Organization("o1",OrganizationType.NON_LEAF_NODE);
		o2 = new Organization("o2",OrganizationType.NON_LEAF_NODE);
		o3 = new Organization("o3",OrganizationType.NON_LEAF_NODE);
		o4 = new Organization("o4",OrganizationType.LEAF_NODE);
		o5 = new Organization("o5",OrganizationType.LEAF_NODE);
		o6 = new Organization("o6",OrganizationType.LEAF_NODE);
		
		o1.addSubOrganization(o2).addSubOrganization(o4);
		o1.addSubOrganization(o3).addSubOrganization(o5);
		o3.addSubOrganization(o6);
		
		organizationService.save(o1);
		
		user_o3		= new User("user_o3","user_o3");
		user_o2		= new User("user_o2","user_o3");
		user_o5 	= new User("user_o5","user_o3");
		
		user_o3.getOrganizationPosts().add(o3.getOrganizationPost(OrganizationPostType.MEMEBER));
		user_o2.getOrganizationPosts().add(o2.getOrganizationPost(OrganizationPostType.ADMIN));
		user_o5.getOrganizationPosts().add(o5.getOrganizationPost(OrganizationPostType.SUPERVISOR));
		
		userService.save(user_o3);
		userService.save(user_o2);
		userService.save(user_o5);
		
		permissionManager = new PermissionManager();
	}

	@Test
	public void testGetUserRoleFromOganizations()
	{
		assertEquals(Role.ORGANIZATION_MEMBER,permissionManager.getUserRoleFromOganizations(user_o3, Sets.newHashSet(o5)));
		assertEquals(null,permissionManager.getUserRoleFromOganizations(user_o3, Sets.newHashSet(o1)));
		assertEquals(null,permissionManager.getUserRoleFromOganizations(user_o3, Sets.newHashSet(o2)));
		assertEquals(Role.ORGANIZATION_MEMBER,permissionManager.getUserRoleFromOganizations(user_o3, Sets.newHashSet(o3)));

	}
}