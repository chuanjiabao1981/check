package com.check.v3.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationType;
import com.check.v3.service.OrganizationService;

public class OrganizationTool {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationTool.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath*:/application-context.xml");
		ctx.refresh();
		OrganizationService organizationService = (OrganizationService) ctx.getBean("organizationService");
		
		organizationAndPosition(organizationService);
		createOrganizationHierachy(organizationService);
		getOrganizationHierachy(organizationService,organizationService.findByName("a"));
		deleteOrganizationHierachy(organizationService,organizationService.findByName("a"));


	}
	private static void deleteOrganizationHierachy(OrganizationService organizationService,Organization o)
	{
		organizationService.delete(o);
	}
	private static void getOrganizationHierachy(OrganizationService organizationService,Organization a)
	{
		logger.info("get Organization {} tpye is {}",a.getName(),a.getType());
		for(Organization o:a.getSubOrganizations()){
			getOrganizationHierachy(organizationService,o);
		}
		
	}
	private static void createOrganizationHierachy(OrganizationService organizationService)
	{
		/* add sub organization 
		 * 
		 * 		b
		 *    /
		 * a		  d
		 *    \     /
		 *    	 c 
		 */
		Organization a = new Organization("a",OrganizationType.NON_LEAF_NODE);
		Organization b = new Organization("b",OrganizationType.LEAF_NODE);
		Organization c = new Organization("c",OrganizationType.NON_LEAF_NODE);
		Organization d = new Organization("d",OrganizationType.LEAF_NODE);
		a.addSubOrganization(b);
		a.addSubOrganization(c);
		c.addSubOrganization(d);
		
		organizationService.save(a);
	}
	private  static void organizationAndPosition(OrganizationService organizationService)
	{
		String orgName = "测试";

		/**find the organization **/
		Organization f = organizationService.findByName(orgName);
		if (f != null){
			organizationService.delete(f);
		}
		/**add a new organization **/
		Organization o = new Organization(orgName,OrganizationType.NON_LEAF_NODE);
		/** add organizationPost **/
		//构造函数完成此操作
		/** save organization **/
		
		organizationService.save(o);
	}

}
