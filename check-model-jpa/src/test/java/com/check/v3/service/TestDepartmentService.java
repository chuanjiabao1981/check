package com.check.v3.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.domain.test.util.OrganizationTreeBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:application-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestDepartmentService {
	
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	
	private String name  = "kkkkk" ;
	
	

	@Test
	public void testSave()
	{
		Department a = new Department(name);
		assertNull(departmentService.findByName(name));
		departmentService.save(a);
		assertNotNull(departmentService.findByName(name));
	}
	@Test 
	public void testDelete()
	{
		/*构建department*/
		Department d = new Department(name);
//		System.err.println("++++++++++++++++++++++++");
//		System.err.println(d);
		d=departmentService.save(d);
		System.err.println(d);
		/*构建 organization*/
		OrganizationTreeBuilder b = new OrganizationTreeBuilder();
		assertEquals(d.getOrganizations().size(),0);
		Organization o = b.buildTree(d,"1234");
//		System.err.println("--------------------------------------");
//		System.err.println(o);
//		System.err.println(d.getOrganizations().get(0));
//		System.err.println(d.getOrganizations().get(0).getId());
//		System.err.println(o.getId());
//		System.err.println(organizationService.findByName(o.getName()).getName());
//		System.err.println(d.getOrganizations().get(0));
//		System.err.println(d.getOrganizations().get(0).getId());
		organizationService.save(o);
		assertNotNull(organizationService.findByName(o.getName()));
		
		
		
		/*删除 department*/
		departmentService.delete(d);
		/* 1. organization被删除完 */
		assertNull(organizationService.findByName(o.getName()));
	}

}
