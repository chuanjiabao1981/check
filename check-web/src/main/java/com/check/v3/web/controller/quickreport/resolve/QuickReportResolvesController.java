package com.check.v3.web.controller.quickreport.resolve;

import javax.annotation.Resource;

import com.check.v3.service.QuickReportResolveService;
import com.check.v3.service.QuickReportService;

public class QuickReportResolvesController {

	@Resource
	QuickReportService quickReportService;
	@Resource
	QuickReportResolveService quickReportResolveService;

	static final String VIEW_NEW 	= "quick_report_resolves/new";
	static final String VIEW_EDIT	= "quick_report_resolves/edit";


}
