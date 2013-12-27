package com.check.v3.web.controller.checktemplate.checkpoint;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.CheckPoint;
import com.check.v3.security.annotation.InstanceId;

@Controller
public class CheckPointsEditController extends CheckPointsController{

	@RequestMapping(value="/check_points/{check_point_id}/edit",method=RequestMethod.GET)
	public String edit(@InstanceId @PathVariable("check_point_id") Long id,Model model,HttpServletRequest httpServletRequest)
	{
		return VIEW_EDIT;
	}
	@RequestMapping(value="/check_points/{check_point_id}",method=RequestMethod.POST)
	public String update(@InstanceId @PathVariable("check_point_id") Long id,
			 			 @ModelAttribute("checkPoint") @Valid CheckPoint checkPoint,
			 			 BindingResult bindingResult,
			 			 Model model,
			 			 HttpServletRequest httpServletRequest)
	{
		if (bindingResult.hasErrors()){
			return VIEW_EDIT;
		}
		checkPointService.save(checkPoint);

		return "redirect:/check_templates/"+checkPoint.getCheckTemplate().getId();
	}
	
	@RequestMapping(value="/check_points/{check_point_id}",method=RequestMethod.DELETE)
	public String destroy(@InstanceId @PathVariable("check_point_id") Long id,
						  @ModelAttribute("checkPoint") @Valid CheckPoint checkPoint)
	{
		checkPointService.delete(checkPoint);
		return "redirect:/check_templates/"+checkPoint.getCheckTemplate().getId();
	}

	@ModelAttribute("checkPoint")
	public CheckPoint populateCheckPoint(@PathVariable("check_point_id") Long id)
	{
		return checkPointService.findById(id);
	}
}
