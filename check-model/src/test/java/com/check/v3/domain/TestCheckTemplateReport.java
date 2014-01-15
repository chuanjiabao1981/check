package com.check.v3.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class TestCheckTemplateReport {

	@Test
	public void testSetCheckTemplateItems() {
		CheckTemplateReport checkTemplateReport = new CheckTemplateReport();
		Set<CheckTemplateReportItem> checkTemplateReportItems = new HashSet<CheckTemplateReportItem>();
		checkTemplateReportItems.add(new CheckTemplateReportItem());
		checkTemplateReport.setCheckTemplateReportItems(checkTemplateReportItems);
		assertEquals(checkTemplateReport.getCheckTemplateReportItems().size(), 1);
	}
	
	@Test
	public void testSetCheckTemplate() {
		CheckTemplateReport checkTemplateReport = new CheckTemplateReport();
		
		CheckTemplate checkTemplate = new CheckTemplate();
		Set<Organization> organizations = new HashSet<Organization>();
		Organization testOrg = new Organization();
		organizations.add(testOrg);
		checkTemplate.setOrganizations(organizations);
		checkTemplateReport.setOrganization(testOrg);
		
		checkTemplateReport.setCheckTemplate(checkTemplate);
		assertEquals(checkTemplate.getCheckTemplateReports().size(), 1);
		
		checkTemplateReport.setCheckTemplate(checkTemplate, false);
		assertEquals(checkTemplate.getCheckTemplateReports().size(), 1);
		assertTrue(checkTemplateReport.getCheckTemplate() == checkTemplate);
	}
}