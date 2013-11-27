package com.check.v3.rest.controller;

import java.beans.PropertyEditorSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportLevel;
import com.check.v3.domain.User;
import com.check.v3.dto.QuickReportDTO;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.QuickReportService;

@Controller
public class QuickReportsRestController {
	@Resource
	private QuickReportService quickReportService;
	
	@RequestMapping(value="/api/v1/organizations/{organization_id}/quick_reports",method=RequestMethod.POST)
	@ResponseBody
	public QuickReportDTO create(@ModelAttribute("quick_report") @Valid @RequestBody QuickReport quickReport,
						HttpServletRequest httpServletRequest,Model model)
	{
		System.err.println("3.kkkkkkkkkkkkkkk");
		QuickReport q = quickReportService.save(quickReport);
		return new QuickReportDTO(q);
	}
	
	@ModelAttribute("quick_report")
	public QuickReport populateQuickReport(@RequestBody  QuickReport quickReport)
	{
		QuickReport q = quickReport;
		q.setDepartment(SecurityTools.getCurrentDepartment());
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		q.setSubmitter(user);
		System.err.println("2.kkkkkkkkkkkkk");
		System.err.println(quickReport.getDescription());
		return q;
	}

	
	@InitBinder
	protected void initBinder(HttpServletRequest request, WebDataBinder binder) 
	{
		System.err.println("1. kkkkkkkkkk");
		binder.setDisallowedFields("organization");
		binder.registerCustomEditor(Organization.class, new OrganizationEditor());
		binder.registerCustomEditor(User.class, new UserEditor());
		binder.registerCustomEditor(QuickReportLevel.class, new QuickReportLevelEditor());
	}
	
	
	
	public class OrganizationEditor extends PropertyEditorSupport 
	{
		public void setAsText(String text)
		{
			System.err.println(text);
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
