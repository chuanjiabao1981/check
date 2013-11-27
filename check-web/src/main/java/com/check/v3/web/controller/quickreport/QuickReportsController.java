package com.check.v3.web.controller.quickreport;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReportLevel;
import com.check.v3.domain.User;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.QuickReportService;

public class QuickReportsController {
	
	@Resource
	OrganizationService organizationService;
	@Resource
	QuickReportService quickReportService;

	
	public final static String VIEW_LIST = "quick_reports/index";
	public final static String VIEW_NEW	 = "quick_reports/new";
	
	@ModelAttribute("levels")
	public Map<String,String> populateLevels()
	{
		Map<String,String> s = new HashMap<String,String>();
		for(QuickReportLevel level :QuickReportLevel.values()){
			s.put(level.toString(), level.getText());
		}
		return s;
	}
	
	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, WebDataBinder binder) 
	{
		binder.registerCustomEditor(Organization.class, new OrganizationEditor());
		binder.registerCustomEditor(User.class, new UserEditor());
		binder.registerCustomEditor(QuickReportLevel.class, new QuickReportLevelEditor());
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
	
	protected Map<Long,String> getResponsiblePersons(Long id)
	{
		Organization o 	= organizationService.findByIdWithUsers(id);
		Map<Long,String> s = new HashMap<Long,String>();
		for(User user:o.getUsers()){
			s.put(user.getId(), user.getName());
		}
		return s;
	}


}
