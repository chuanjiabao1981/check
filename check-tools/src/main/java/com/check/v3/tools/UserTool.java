package com.check.v3.tools;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.User;
import com.check.v3.service.UserService;

public class UserTool {

	private static final Logger logger = LoggerFactory.getLogger(UserTool.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.err.println("1.test some.......");
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		System.err.println("2.test some.......");
		ctx.load("classpath*:application-context.xml");
		System.err.println("3.test some.......");
		ctx.refresh();
		System.err.println("4.test some.......");

		
		UserService 					    userService 			= (UserService) 		ctx.getBean("userService");
		DefaultWebSecurityManager			securityManager			=  (DefaultWebSecurityManager) ctx.getBean("securityManager");
		/*
		 *  由于AbstractShiroFilter默认不会设置SecurityMananger给当前线程，每次请求来才设置。(staticSecurityManagerEnabled)
		 *  且ShiroFilterFactoryBean也没有接口设置staticSecurityManagerEnabled，所以在非请求模式下，只能手动设置securityManager了。
		 */
		SecurityUtils.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
		User		user		= new User();
		logger.info("new a user");
		user.setAccount("test222");
		user.setPassword("12345");
		logger.info("begin save user");
		userService.save(user);
		logger.info("after save user");

		ctx.close();
		logger.info("end");

	}

}
