package com.check.v3.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.OrganizationService;


@Controller
public class OrganizationsController {

	@Resource
	OrganizationService organizationService;
	
	public final static String VIEW_LIST = "organizations/index";
	@RequestMapping(value="/organizations",method=RequestMethod.GET)
	public String index(Model model)
	{
		Department department 				= SecurityTools.getCurrentDepartment();
		List<Organization>	  organizations = organizationService.findAllByDepartmentId(department.getId());
		model.addAttribute("organizations",organizations);
		return VIEW_LIST;
	}

}
