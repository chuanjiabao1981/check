package com.check.v3.service;

import static org.junit.Assert.assertNotNull;

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
import com.check.v3.domain.User;
import com.check.v3.service.exception.UserAccountDuplicateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestUser {
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;
	
	private Organization 	organization;
	private User		 	user;
	private static String   UserAccount = "test_test";
	private static String   UserName	= "kkkkkkk";
	public void init()
	{
		organization = new Organization("testme",OrganizationType.NON_LEAF_NODE);
		user 		 = new User(UserAccount,UserName);
		organizationService.save(organization);
		user.getOrganizationPosts().add(organization.getOrganizationPost(OrganizationPostType.ADMIN));
		userService.save(user);
	}
	@Test(expected=UserAccountDuplicateException.class)
	public void testSameAccount() throws UserAccountDuplicateException 
	{
		String account = "samesame";
		User user1 = new User();
		user1.setAccount(account);
		user1.setName("bigbigbig");
		user1.setPassword_cryp("1");
		User user2 = new User();
		user2.setAccount(account);
		user2.setName("bigbigbig");
		user2.setPassword_cryp("2");
		
		userService.save(user1);
		userService.save(user2);
	}
	
	@Test
	public void testGetOrganizationPost()
	{
		User user = userService.findByAccount(UserAccount);
		assertNotNull(user);
		assertNotNull(user.getOrganizationPosts());
	}
}
