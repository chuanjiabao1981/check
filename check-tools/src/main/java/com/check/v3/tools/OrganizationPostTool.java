package com.check.v3.tools;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationPost;
import com.check.v3.domain.OrganizationType;
import com.check.v3.domain.User;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.UserService;

public class OrganizationPostTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath*:/application-context.xml");
		ctx.refresh();
		String userAccount									= "organization_user_test";
		String organizationName								= "organization_tools";
		
		UserService                   userService 			= (UserService) 		ctx.getBean("userService");
		OrganizationService	   organizationService 			= (OrganizationService) ctx.getBean("organizationService");
		User			              user					= userService.findByAccount(userAccount);
		Organization	              organization			= organizationService.findByName(organizationName);
		if (user != null)
			userService.delete(user);
		if (organization != null)
			organizationService.delete(organization);
		
		organization										= new Organization(organizationName,OrganizationType.NON_LEAF_NODE);
		user												= new User(userAccount);

		organizationService.save(organization);
		
		for(OrganizationPost p : organization.getOrganizationPosts())
			user.getOrganizationPosts().add(p);
		userService.save(user);
	}

}
