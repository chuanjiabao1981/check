package com.check.v3.web.controller.checktemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.CheckTemplate;
import com.check.v3.security.annotation.InstanceId;

@Controller
public class CheckTemplatesEditController extends CheckTemplatesController{

	@RequestMapping(value="/check_templates/{check_template_id}/edit",method=RequestMethod.GET)
	public String edit(@InstanceId @PathVariable("check_template_id") Long id,Model model,HttpServletRequest httpServletRequest)
	{
		return VIEW_EDIT;
	}
	@RequestMapping(value="/check_templates/{check_template_id}",method=RequestMethod.POST)
	public String update(@InstanceId @PathVariable("check_template_id") Long id,
			 			 @ModelAttribute("checkTemplate") @Valid CheckTemplate checkTemplate,
			 			 BindingResult bindingResult,
			 			 Model model,
			 			 HttpServletRequest httpServletRequest)
	{
		if (bindingResult.hasErrors()){
			return VIEW_EDIT;
		}
		checkTemplateService.save(checkTemplate);

		return "redirect:/check_templates/"+checkTemplate.getId();
	}
	
	@RequestMapping(value="/check_templates/{check_template_id}",method=RequestMethod.DELETE)
	public String destroy(@InstanceId @PathVariable("check_template_id") Long id,
						  @ModelAttribute("checkTemplate") @Valid CheckTemplate checkTemplate)
	{
		checkTemplateService.delete(checkTemplate);
		return "redirect:/check_templates";
	}

	@ModelAttribute("checkTemplate")
	public CheckTemplate populateCheckTemplate(@PathVariable("check_template_id") Long id)
	{
		return this.checkTemplateService.findById(id);
	}


}
