package com.check.v3.tools.test;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.QuickReport;
import com.check.v3.service.QuickReportService;

public class TestNotExsitField {

	
	private static QuickReportService quickReportService;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath*:/application-context.xml","classpath*:/application-web-security-context.xml");
		ctx.refresh();
		quickReportService	    = (QuickReportService) ctx.getBean("quickReportService");
		QuickReport quickReport = quickReportService.findById(159L);
		//
		System.err.println(quickReport == null);

	}

}
