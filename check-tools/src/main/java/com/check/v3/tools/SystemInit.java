package com.check.v3.tools;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.Department;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.DepartmentService;
import com.check.v3.service.UserService;

public class SystemInit {
	
	public static final String ROOT_DEPARTMENT_NAME		="root";
	public static final String ROOT_ACCOUNT				="root";
	public static final String ROOT_NAME				= "系统管理员";
	public static final String ROOT_PASSWORD			= "12345";
	private static DepartmentService departmentService;
	private static UserService userService;
	public static void main(String[] args)
	{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath*:/application-context.xml","classpath*:/application-web-security-context.xml");
		ctx.refresh();
		departmentService 	= (DepartmentService) ctx.getBean("departmentService");
		userService			= (UserService) ctx.getBean("userService");
		Department d		= initRootDepartment();
		
		initRootUser(ctx,d);
	}
	
	public static Department initRootDepartment()
	{
		Department root_department = new Department(ROOT_DEPARTMENT_NAME);
		return departmentService.save(root_department);
	}
	public static User initRootUser(GenericXmlApplicationContext ctx,Department d)
	{
		User user  = new User(ROOT_ACCOUNT,ROOT_NAME);
		DefaultWebSecurityManager			securityManager			=  (DefaultWebSecurityManager) ctx.getBean("securityManager");
		/*
		 *  由于AbstractShiroFilter默认不会设置SecurityMananger给当前线程，每次请求来才设置。(staticSecurityManagerEnabled)
		 *  且ShiroFilterFactoryBean也没有接口设置staticSecurityManagerEnabled，所以在非请求模式下，只能手动设置securityManager了。
		 */
		SecurityUtils.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
		user.setPassword_cryp(SecurityTools.getEncryptPassword(ROOT_PASSWORD));
		user.setRole(Role.SYS_ADMIN);
		user.setDepartment(d);
		return userService.save(user);
	}

}
