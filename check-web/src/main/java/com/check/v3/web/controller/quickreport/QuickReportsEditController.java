package com.check.v3.web.controller.quickreport;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.QuickReport;
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
		QuickReport quickReport = (QuickReport) httpServletRequest.getAttribute(SecurityConstant.CurrentInstance);
		if (quickReport != null && quickReport.getOrganization() != null && quickReport.getOrganization().getId()!=null)
			 model.addAttribute("responsiblePersons", getResponsiblePersons(quickReport.getOrganization().getId()));
		model.addAttribute("quick_report", quickReport);
		return VIEW_EDIT;
	}
	
	@RequestMapping(value="/quick_reports/{quick_report_id}",method=RequestMethod.POST)
	public String update(@InstanceId @PathVariable("quick_report_id") Long id,
						 @ModelAttribute("quick_report") @Valid QuickReport quickReport,
						 BindingResult bindingResult,
						 Model model)
	{
		if (bindingResult.hasErrors()){
			 model.addAttribute("responsiblePersons", getResponsiblePersons(quickReport.getOrganization().getId()));
			return VIEW_EDIT;
		}
		quickReportService.save(quickReport);
		return "redirect:/quick_reports/"+quickReport.getId();
	}
	@RequestMapping(value="/quick_reports/{quick_report_id}",method=RequestMethod.GET)
	public String show(@InstanceId @PathVariable("quick_report_id") Long id,
		    HttpServletRequest httpServletRequest)
	{
		return VIEW_SHOW;
	}
	@RequestMapping(value="/quick_reports/{quick_report_id}",method=RequestMethod.DELETE)
	public String destroy(@InstanceId @PathVariable("quick_report_id") Long id,
						  @ModelAttribute("quick_report") @Valid QuickReport quickReport)
	{
		this.quickReportService.deleteById(id);
		return "redirect:/organizations/"+quickReport.getOrganization().getId()+"/quick_reports";
	}
	
	@ModelAttribute("quick_report")
	public QuickReport populateQuickReport(@PathVariable("quick_report_id") Long id)
	{
		QuickReport q =  this.quickReportService.findById(id);
		return q;
	}

}
