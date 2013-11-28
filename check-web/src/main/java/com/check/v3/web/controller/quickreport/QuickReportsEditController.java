package com.check.v3.web.controller.quickreport;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.security.SecurityConstant;
import com.check.v3.security.annotation.InstanceId;

@Controller
public class QuickReportsEditController extends QuickReportsController{
	
	@RequestMapping(value="/quick_reports/{quick_report_id}/edit",method=RequestMethod.GET)
	public String edit(@InstanceId @PathVariable("quick_report_id") Long id,
					    Model model,
					    HttpServletRequest httpServletRequest
					    )
	{
		System.err.println(httpServletRequest.getAttribute(SecurityConstant.CurrentInstance));
		model.addAttribute("quick_report", this.quickReportService.findById(id));
		return VIEW_EDIT;
	}

}
