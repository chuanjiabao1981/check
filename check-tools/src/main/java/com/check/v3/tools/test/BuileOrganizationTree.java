package com.check.v3.tools.test;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationType;
import com.check.v3.service.OrganizationService;
import com.check.v3.tools.SystemInit;

public class BuileOrganizationTree {
	
//	private static OrganizationService organizationService;
//	private static Organization rootOrganization;
	public static void main(String[] args)
	{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath*:/application-context.xml");
		ctx.refresh();
		/*
		 * 					   O9
		 * 					  / 
		 * 					 O8
		 * 					/ 		
		 * 				  O6
		 * 				 /
		 * 				03
		 * 			  /
		 *          O1
		 *   	  /   \
		 *    ROOT		O4 
		 *   	  \
		 *          O2		O7
		 *            \	  /
		 *              05
		 * 
		 */
		BuileOrganizationTree k = new BuileOrganizationTree();
		
		k.buildOrganizationTree(ctx);
//		k.deleteOrganizationTree(ctx);
	}
	public void buildOrganizationTree(GenericXmlApplicationContext ctx)
	{
		OrganizationService organizationService =  (OrganizationService) ctx.getBean("organizationService");

		Organization rootOrganization		= organizationService.findByName(SystemInit.ROOT_DEPARTMENT_NAME);
		Organization oo1 = new Organization("oo1",OrganizationType.NON_LEAF_NODE);
		Organization oo2 = new Organization("oo2",OrganizationType.NON_LEAF_NODE);
		Organization oo3 = new Organization("oo3",OrganizationType.NON_LEAF_NODE);
		Organization oo4 = new Organization("oo4",OrganizationType.LEAF_NODE);
		Organization oo5 = new Organization("oo5",OrganizationType.NON_LEAF_NODE);
		Organization oo6 = new Organization("oo6",OrganizationType.NON_LEAF_NODE);
		Organization oo7 = new Organization("oo7",OrganizationType.LEAF_NODE);
		Organization oo8 = new Organization("oo8",OrganizationType.NON_LEAF_NODE);
		Organization oo9 = new Organization("oo9",OrganizationType.LEAF_NODE);

		rootOrganization.addSubOrganization(oo1);
		System.err.println(rootOrganization.getSubOrganizations().size());
		System.err.println(rootOrganization.getSubOrganizations().get(0).getName());
		/*
						.addSubOrganization(oo3)
						.addSubOrganization(oo6)
						.addSubOrganization(oo8)
						.addSubOrganization(oo9);
		rootOrganization.addSubOrganization(oo2)
						.addSubOrganization(oo5)
						.addSubOrganization(oo7);
		
		oo1.addSubOrganization(oo4);*/
		
//		organizationService.save(rootOrganization);
		
	}
	public void deleteOrganizationTree(GenericXmlApplicationContext ctx)
	{
		OrganizationService organizationService =  (OrganizationService) ctx.getBean("organizationService");
		Organization o = organizationService.findByName("oo2");
		organizationService.delete(o);
		o = organizationService.findByName("oo1");
		organizationService.delete(o);
	}

}
