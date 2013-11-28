package com.check.v3.web.controller.quickreport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.QuickReport;
import com.check.v3.domain.User;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.security.util.SecurityTools;

@Controller
public class QuickReportsCreateController extends QuickReportsController{
	
	@RequestMapping(value="/organizations/{organization_id}/quick_reports/new",method=RequestMethod.GET)
	public String newForm(@InstanceId @PathVariable("organization_id") Long organizationId,HttpServletRequest httpServletRequest,Model model)
	{
		model.addAttribute("quick_report", new QuickReport());
		return VIEW_NEW;
	}
	@RequestMapping(value="/organizations/{organization_id}/quick_reports",method=RequestMethod.POST)
	public String create(@InstanceId @PathVariable("organization_id") Long organizationId,
			 			@ModelAttribute("quick_report") @Valid QuickReport quickReport,
			 			BindingResult bindingResult, 
						HttpServletRequest httpServletRequest,Model model)
	{
		if (bindingResult.hasErrors()){
			return VIEW_NEW;
		}
		quickReportService.save(quickReport);
		return "redirect:/organizations/"+organizationId+"/quick_reports";
	}
	
	@ModelAttribute("quick_report")
	public QuickReport populateQuickReport()
	{
		QuickReport q = new QuickReport();
		q.setDepartment(SecurityTools.getCurrentDepartment());
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		q.setSubmitter(user);
		return q;
	}
	@ModelAttribute("responsiblePersons")
	public Map<Long,String> populateResponsiblePersons(@PathVariable("organization_id") Long organizationId)
	{
		return this.getResponsiblePersons(organizationId);
	}


}
