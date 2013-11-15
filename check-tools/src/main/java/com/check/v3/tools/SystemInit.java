package com.check.v3.tools;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationType;
import com.check.v3.domain.User;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.UserService;

public class SystemInit {
	
	public static final String ROOT_ORGANIZATION_NAME	="root";
	public static final String ROOT_ACCOUNT				="root";
	public static final String ROOT_NAME				= "系统管理员";
	public static final String ROOT_PASSWORD			= "12345";
	private static OrganizationService organizationService;
	private static UserService userService;
	public static void main(String[] args)
	{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath*:/application-context.xml","classpath*:/application-web-security-context.xml");
		ctx.refresh();
		organizationService = (OrganizationService) ctx.getBean("organizationService");
		userService			= (UserService) ctx.getBean("userService");
		Organization r	= initRootOrganization();
		
		initRootUser(ctx,r);
	}
	
	public static Organization initRootOrganization()
	{
		Organization root_organization = new Organization(ROOT_ORGANIZATION_NAME,OrganizationType.NON_LEAF_NODE);
		return organizationService.save(root_organization);
	}
	public static User initRootUser(GenericXmlApplicationContext ctx,Organization o)
	{
		User user  = new User(ROOT_ACCOUNT,ROOT_NAME);
		DefaultWebSecurityManager			securityManager			=  (DefaultWebSecurityManager) ctx.getBean("securityManager");
		/*
		 *  由于AbstractShiroFilter默认不会设置SecurityMananger给当前线程，每次请求来才设置。(staticSecurityManagerEnabled)
		 *  且ShiroFilterFactoryBean也没有接口设置staticSecurityManagerEnabled，所以在非请求模式下，只能手动设置securityManager了。
		 */
		SecurityUtils.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
		user.setPassword_cryp(SecurityTools.getEncryptPassword(ROOT_PASSWORD));
		return userService.save(user);
	}

}
