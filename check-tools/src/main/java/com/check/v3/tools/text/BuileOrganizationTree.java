package com.check.v3.tools.text;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationType;
import com.check.v3.service.OrganizationService;
import com.check.v3.tools.SystemInit;

public class BuileOrganizationTree {
	
	private static OrganizationService organizationService;
	private static Organization rootOrganization;
	public static void main(String[] args)
	{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath*:/application-context.xml");
		ctx.refresh();
		organizationService 	= (OrganizationService) ctx.getBean("organizationService");
//		rootOrganization		= organizationService.findByName(SystemInit.ROOT_ORGANIZATION_NAME);
//		if (rootOrganization == null){
//			throw new RuntimeException("root organization is null"); 
//		}
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
		buildOrganizationTree();
	}
	public static void buildOrganizationTree()
	{
		Organization t = organizationService.findByName("o1");
		if (t != null){
			System.err.println(t.getId());
			organizationService.delete(t);
		}
//		t = organizationService.findByName("o2");
//		if(t!=null){
//			System.err.println(t.getId());
//			organizationService.delete(t);
//		}
		
		Organization o1 = new Organization("o1",OrganizationType.NON_LEAF_NODE);
//		Organization o2 = new Organization("o2",OrganizationType.NON_LEAF_NODE);
		Organization o3 = new Organization("o3",OrganizationType.NON_LEAF_NODE);
/*		Organization o4 = new Organization("o4",OrganizationType.LEAF_NODE);
		Organization o5 = new Organization("o5",OrganizationType.NON_LEAF_NODE);
		Organization o6 = new Organization("o6",OrganizationType.NON_LEAF_NODE);
		Organization o7 = new Organization("o7",OrganizationType.LEAF_NODE);
		Organization o8 = new Organization("o8",OrganizationType.NON_LEAF_NODE);
		Organization o9 = new Organization("o9",OrganizationType.LEAF_NODE);*/
		//rootOrganization.addSubOrganization(o1).addSubOrganization(o3);
		/*
						.addSubOrganization(o6)
						.addSubOrganization(o8)
						.addSubOrganization(o9);
		o1.addSubOrganization(o4);
		rootOrganization.addSubOrganization(o2)
						.addSubOrganization(o5)
						.addSubOrganization(o7);*/
		/* 
		 * 
		 * 注意：这里不能通过save rootOrganization 达到存储o1和o2的效果。
		 * 应该是由于，organization的suborganization都是从数据库中读取的。
		 * 如果直接存储，会有org.hibernate.TransientObjectException异常。
		 * http://stackoverflow.com/questions/10278096/object-references-an-unsaved-transient-instance-save-the-transient-instance-be
		 * 
		 * 		organizationService.save(rootOrganization);
		 */
//		organizationService.save(o1);
//		organizationService.save(o2);
		
	}

}
