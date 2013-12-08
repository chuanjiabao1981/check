package com.check.v3.web.controller.quickreport;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.QuickReport;
import com.check.v3.security.annotation.InstanceId;

@Controller
public class QuickReportsShowController extends QuickReportsController{

	@RequestMapping(value="/quick_reports/{quick_report_id}",method=RequestMethod.GET)
	public String show(@InstanceId @PathVariable("quick_report_id") Long id,
		    HttpServletRequest httpServletRequest)
	{
		return VIEW_SHOW;
	}
	
	@ModelAttribute("quick_report")
	public QuickReport populateQuickReport(@PathVariable("quick_report_id") Long id)
	{
		QuickReport q =  this.quickReportService.findByIdWithMedia(id);
		return q;
	}


}
