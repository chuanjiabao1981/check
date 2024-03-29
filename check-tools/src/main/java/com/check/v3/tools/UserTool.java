package com.check.v3.tools;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.User;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.UserService;
import com.check.v3.service.exception.UserAccountDuplicateException;

public class UserTool {

	private static final Logger logger = LoggerFactory.getLogger(UserTool.class);

	/**
	 * @param args
	 * @throws UserAccountDuplicateException 
	 */
	public static void main(String[] args) throws UserAccountDuplicateException {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath*:/application-context.xml","classpath*:/application-security-context.xml");

		ctx.refresh();

		UserService 					    userService 			= (UserService) 		ctx.getBean("userService");
		DefaultWebSecurityManager			securityManager			=  (DefaultWebSecurityManager) ctx.getBean("securityManager");
		/*
		 *  由于AbstractShiroFilter默认不会设置SecurityMananger给当前线程，每次请求来才设置。(staticSecurityManagerEnabled)
		 *  且ShiroFilterFactoryBean也没有接口设置staticSecurityManagerEnabled，所以在非请求模式下，只能手动设置securityManager了。
		 */
		SecurityUtils.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
		User		user		= new User();
		user.setAccount("test_test");
		user.setName("test_test");
		user.setPassword_cryp(SecurityTools.getEncryptPassword("12345"));
		System.err.println(user.getAccount());
		logger.info("add user "+user.getAccount());
		userService.save(user);
		logger.info("save user "+user.getAccount());
		ctx.close();
	}

}
