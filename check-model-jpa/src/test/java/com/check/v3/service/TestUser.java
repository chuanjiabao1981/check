package com.check.v3.service;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.User;
import com.check.v3.service.exception.UserAccountDuplicateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestUser {
	
	@Autowired
	private UserService userService;

	public void init()
	{
		
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
}
