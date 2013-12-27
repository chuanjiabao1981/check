package com.check.v3.web.controller.checktemplate;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.CheckTemplate;
import com.check.v3.domain.Department;
import com.check.v3.security.util.SecurityTools;



@Controller
public class CheckTemplatesCreateController extends CheckTemplatesController{
	@RequestMapping(value="/check_templates/new",method=RequestMethod.GET)
	public String newForm()
	{
		
		return VIEW_NEW;
	}
	@RequestMapping(value="/check_templates",method=RequestMethod.POST)
	public String create(@ModelAttribute("checkTemplate") @Valid CheckTemplate checkTemplate,
			  			 BindingResult bindingResult,
			  			 Model model)
	{
		if (bindingResult.hasErrors()){
			return VIEW_NEW;
		}
		checkTemplate = checkTemplateService.save(checkTemplate);
		return "redirect:/check_templates/"+checkTemplate.getId();
	}
	
	@ModelAttribute("checkTemplate")
	public CheckTemplate populateCheckTemplate()
	{
		CheckTemplate checkTemplate 		= new CheckTemplate();
		Department	department				= SecurityTools.getCurrentDepartment();

		checkTemplate.setDepartment(department);
		return checkTemplate;
	}
}
