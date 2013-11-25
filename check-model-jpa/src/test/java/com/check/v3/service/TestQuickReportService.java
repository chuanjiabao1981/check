package com.check.v3.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.User;
import com.check.v3.domain.test.util.DepartmentBuilder;
import com.check.v3.domain.test.util.OrganizationBuilder;
import com.check.v3.domain.test.util.QuickReportBuilder;
import com.check.v3.domain.test.util.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml","classpath:application-test-jpa.xml"})
@Transactional
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager") 
public class TestQuickReportService {
	
	@Resource
	DepartmentBuilder 		departmentBuilder;
	@Resource
	UserBuilder		  		userBuilder;
	@Resource
	OrganizationBuilder		organizationBuilder;
	@Resource
	QuickReportBuilder		quickReportBuilder;
	@Resource
	QuickReportService		quickReportService;
	
	
	Department 				department;
	Organization			organization;
	User					user;
	
	@Before
	public void init()
	{
		department 			= departmentBuilder.create("xxxxxxx");
		organization		= organizationBuilder.create(department);
		user 				= userBuilder.create(department, "kkkkkk");
	}
	@Test
	public void testSave()
	{
		QuickReport quickReport = quickReportBuilder.build(user, organization, "desc");
		assertNull(quickReport.getCreatedAt());
		assertNull(quickReport.getId());
		quickReport = quickReportService.save(quickReport);
		assertNotNull(quickReport.getId());
		assertNotNull(quickReport.getCreatedAt());
		assertNotNull(quickReport.getUpdatedAt());
		
		QuickReport quickReport2 = quickReportService.findById(quickReport.getId());
		assertNotNull(quickReport2.getDepartment());
	}
	@Test
	public void testPage()
	{
		int page 	= 2;
		int rows  	= 10;
		for(Integer i = 0; i< 20;i++)
		{
			quickReportBuilder.create(user, organization, i.toString());
		}
		
		Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
		PageRequest pageRequest = new PageRequest(page-1,rows,sort);
		Organization t 			= new Organization();
		t.setId(organization.getId());
		Page<QuickReport> quickReports = quickReportService.findByOrganization(t, pageRequest);
		
		assertEquals(10,quickReports.getSize());
		assertTrue(quickReports.getContent().get(rows-1).getDescription().equals("0"));

	}
}
