package com.check.v3.web.controller.checktemplate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.CheckTemplate;
import com.check.v3.security.annotation.InstanceId;
@Controller
public class CheckTemplatesShowController extends CheckTemplatesController{

	@RequestMapping(value="/check_templates/{check_template_id}",method=RequestMethod.GET)
	public String show(@InstanceId @PathVariable("check_template_id") Long id,
		    HttpServletRequest httpServletRequest)
	{
		
		return VIEW_SHOW;
	}
	
	@ModelAttribute("checkTemplate")
	public CheckTemplate populateCheckTemplate(@PathVariable("check_template_id") Long id)
	{
		return this.checkTemplateService.findByIdWithCheckPoints(id);
	}


}
