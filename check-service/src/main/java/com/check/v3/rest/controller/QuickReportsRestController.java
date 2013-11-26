package com.check.v3.rest.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.check.v3.domain.QuickReport;
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
	public QuickReportDTO create(	@ModelAttribute("quick_report") @Valid @RequestBody  QuickReport quickReport,
						HttpServletRequest httpServletRequest,Model model)
	{
		QuickReport q = quickReportService.save(quickReport);
		return new QuickReportDTO(q);
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

}
