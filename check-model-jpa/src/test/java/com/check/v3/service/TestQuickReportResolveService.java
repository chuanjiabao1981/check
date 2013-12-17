package com.check.v3.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.domain.ResolveImage;
import com.check.v3.domain.User;
import com.check.v3.domain.test.util.DepartmentBuilder;
import com.check.v3.domain.test.util.OrganizationBuilder;
import com.check.v3.domain.test.util.QuickReportBuilder;
import com.check.v3.domain.test.util.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml","classpath:application-test-jpa.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestQuickReportResolveService {

	@Resource
	DepartmentBuilder 		departmentBuilder;
	@Resource
	UserBuilder		  		userBuilder;
	@Resource
	OrganizationBuilder		organizationBuilder;
	@Resource
	QuickReportBuilder		quickReportBuilder;
	@Resource
	QuickReportResolveService quickReportResolveService;
	
	
	Department 				department;
	Organization			organization;
	User					user;
	QuickReport				quickReport;
	
	@Before
	public void init()
	{
		department 			= departmentBuilder.create("xxxxxxx");
		organization		= organizationBuilder.create(department);
		user 				= userBuilder.create(department, "kkkkkk");
		quickReport			= quickReportBuilder.create(user, organization, "dddddddd");
		
	}
	@Test
	public void testSave()
	{
		QuickReportResolve quickReportResolve = new QuickReportResolve();
		quickReportResolve.setQuickReport(quickReport);
		quickReportResolve.setDepartment(department);
		quickReportResolve.setDescription("xxxxxxx");
		ResolveImage resolveImage = new ResolveImage();
		resolveImage.setName("kkkk");
		resolveImage.setDepartment(department);
		quickReportResolve.addImage(resolveImage);
		quickReportResolve = quickReportResolveService.save(quickReportResolve);

		assertNotNull(quickReportResolve.getId());
		assertTrue(quickReportResolve.getImages().size() == 1);
		ResolveImage i = quickReportResolve.getImages().iterator().next();
		System.err.println(quickReportResolve.getId());
		assertNotNull(quickReportResolve.getQuickReport().getId());
	}
	@Test
	public void testCompare()
	{
		Long a = null;
		Long b = null;
//		System.err.println(Long.compare(a, b));
	}
}
