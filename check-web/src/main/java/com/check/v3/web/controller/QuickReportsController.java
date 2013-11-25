package com.check.v3.web.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.service.QuickReportService;

@Controller
public class QuickReportsController {
	
	@Resource
	QuickReportService quickReportService;
	
	public final static String VIEW_LIST = "quick_reports/index";
	public final static String VIEW_NEW	 = "quick_reports/new";

	@RequestMapping(value="/organizations/{organization_id}/quick_reports",method=RequestMethod.GET)
	public String index(@InstanceId @PathVariable("organization_id") Long organizationId,HttpServletRequest httpServletRequest,Model model)
	{
		int page 	= 1;
		int rows  	= 10;
		Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
		PageRequest pageRequest = new PageRequest(page-1,rows,sort);

		Page<QuickReport> quickReports = quickReportService.findByOrganization(new Organization(organizationId), pageRequest);
		model.addAttribute("quick_reports", quickReports.getContent());
		return VIEW_LIST;
	}
	@RequestMapping(value="/organizations/{organization_id}/quick_reports/new",method=RequestMethod.GET)
	public String newForm(@InstanceId @PathVariable("organization_id") Long organizationId,HttpServletRequest httpServletRequest,Model model)
	{
		return VIEW_NEW;
	}

}
