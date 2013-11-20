package com.check.v3.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationType;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.OrganizationService;


@Controller
public class OrganizationsController {

	@Resource
	OrganizationService organizationService;
	
	public final static String VIEW_LIST = "organizations/index";
	public final static String VIEW_NEW	 = "organizations/new";
	public final static String VIEW_EDIT = "organizations/edit";
	
	@RequestMapping(value="/organizations",method=RequestMethod.GET)
	public String index(Model model)
	{
		Department department 				= SecurityTools.getCurrentDepartment();
		System.err.println(department.getId());
		List<Organization>	  organizations = organizationService.findAllByDepartmentId(department.getId());
		model.addAttribute("organizations",organizations);
		return VIEW_LIST;
	}
	@RequestMapping(value="/organizations/new",method=RequestMethod.GET)
	public String newForm(Model model)
	{
		Organization organization = new Organization();
		model.addAttribute("organization", organization);
		return VIEW_NEW;
	}
	@RequestMapping(value="/organizations",method=RequestMethod.POST)
	public String create(@ModelAttribute("organization") @Validated Organization organization,BindingResult bindingResult,Model model)
	{
		if (bindingResult.hasErrors()){
			return VIEW_NEW;
		}
		organizationService.save(organization);
		
		return "redirect:/organizations";
	}
	@RequestMapping(value="/organizations/{id}",method=RequestMethod.GET)
	public String edit(@InstanceId @PathVariable Long id,Model model)
	{
		Organization organization = organizationService.findById(id);
		model.addAttribute("organization", organization);
		return VIEW_EDIT;
	}
	@RequestMapping(value="/organizations/{id}",method=RequestMethod.POST)
	public String update(@InstanceId @PathVariable Long id,
					     @ModelAttribute("organization") @Validated Organization organization,
					     BindingResult bindingResult,
					     Model model)
	{
		if (bindingResult.hasErrors()){
			return VIEW_EDIT;
		}
		organizationService.save(organization);
		return "redirect:/organizations";
	}
	@RequestMapping(value="/organizations/{id}",method=RequestMethod.DELETE)
	public String destroy(@InstanceId @PathVariable Long id)
	{
		organizationService.delete(id);
		return "redirect:/organizations";
	}
	
	@ModelAttribute("organization")
	public Organization populateOrganization()
	{
		Organization o = new Organization();
		o.setType(OrganizationType.LEAF_NODE);
		/*
		 * 	这个department，来自于current_user, current_user已经被序列化了 
		 *  所以，department.organizations一定取不到。
		 *  而且，也不需要department和organization保持同步。
		 */
		o.setDepartment(SecurityTools.getCurrentDepartment(),false);
		return o;
	}
 

}
