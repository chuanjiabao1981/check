package com.check.v3.web.controller.checktemplate.checkpoint;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.CheckPoint;
import com.check.v3.domain.CheckTemplate;
import com.check.v3.security.annotation.InstanceId;

@Controller
public class CheckPointsCreateController extends CheckPointsController{

	@RequestMapping(value="/check_templates/{check_template_id}/check_points/new",method=RequestMethod.GET)
	public String newForm()
	{
		
		return VIEW_NEW;
	}
	@RequestMapping(value="/check_templates/{check_template_id}/check_points",method=RequestMethod.POST)
	public String create(@ModelAttribute("checkPoint") @Valid CheckPoint checkPoint,
			  			 BindingResult bindingResult,
			  			 Model model)
	{
		if (bindingResult.hasErrors()){
			return VIEW_NEW;
		}
		checkPoint = checkPointService.save(checkPoint);
		return "redirect:/check_templates/"+checkPoint.getCheckTemplate().getId();
	}
	
	
	

	
	@ModelAttribute("checkPoint")
	public CheckPoint populateCheckPoint(@PathVariable("check_template_id") Long checkTemplateId)
	{
		CheckTemplate checkTemplate = checkTemplateService.findById(checkTemplateId);
		return checkTemplate.buildCheckPoint();
	}

}
