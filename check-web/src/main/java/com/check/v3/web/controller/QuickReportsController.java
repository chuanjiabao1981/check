package com.check.v3.web.controller;


import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportLevel;
import com.check.v3.domain.User;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.QuickReportService;

@Controller
public class QuickReportsController {
	
	@Resource
	QuickReportService quickReportService;
	@Resource
	OrganizationService organizationService;
	
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
		model.addAttribute("quick_report", new QuickReport());
		model.addAttribute("responsiblePersons",populateResponsiblePersons(organizationId));
		return VIEW_NEW;
	}
	@RequestMapping(value="/organizations/{organization_id}/quick_reports",method=RequestMethod.POST)
	public String create(@InstanceId @PathVariable("organization_id") Long organizationId,
			 			@ModelAttribute("quick_report") @Valid QuickReport quickReport,
			 			BindingResult bindingResult, 
						HttpServletRequest httpServletRequest,Model model)
	{
		if (bindingResult.hasErrors()){
			model.addAttribute("responsiblePersons",populateResponsiblePersons(organizationId));
			return VIEW_NEW;
		}
		quickReportService.save(quickReport);
		return "redirect:/organizations/"+organizationId+"/quick_reports";
	}
	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, WebDataBinder binder) 
	{
		binder.registerCustomEditor(Organization.class, new OrganizationEditor());
		binder.registerCustomEditor(User.class, new UserEditor());
		binder.registerCustomEditor(QuickReportLevel.class, new QuickReportLevelEditor());
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

	@ModelAttribute("levels")
	public Map<String,String> populateLevels()
	{
		Map<String,String> s = new HashMap<String,String>();
		for(QuickReportLevel level :QuickReportLevel.values()){
			s.put(level.toString(), level.getText());
		}
		return s;
	}
	public Map<Long,String> populateResponsiblePersons(Long id)
	{
		Organization o 	= organizationService.findByIdWithUsers(id);
		Map<Long,String> s = new HashMap<Long,String>();
		for(User user:o.getUsers()){
			s.put(user.getId(), user.getName());
		}
		return s;
	}
	
	public class OrganizationEditor extends PropertyEditorSupport 
	{
		public void setAsText(String text)
		{
			setValue(new Organization(Long.valueOf(text)));
		}
	}
	public class UserEditor extends PropertyEditorSupport
	{
		public void setAsText(String text)
		{
			if (text == null || text.trim().length() == 0)
				setValue(null);
			else{
				User u = new User();
				u.setId(Long.valueOf(text));
				setValue(u);
			}
		}
	}
	public class QuickReportLevelEditor extends PropertyEditorSupport
	{
		public void setAsText(String text)
		{
			setValue(QuickReportLevel.valueOf(text));
		}
	}

 

}
