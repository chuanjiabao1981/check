package com.check.v3.web.controller.checktemplate;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.CheckTemplate;
import com.check.v3.domain.Department;
import com.check.v3.security.util.SecurityTools;

@Controller
public class CheckTemplatesIndexController extends CheckTemplatesController{


	@RequestMapping(value="/check_templates",method=RequestMethod.GET)
	public String index(Model model)
	{
		Department	department				= SecurityTools.getCurrentDepartment();
		List<CheckTemplate> checkTemplates  = this.checkTemplateService.findAllByDepartment(department);
		model.addAttribute("check_templates", checkTemplates);
		return VIEW_LIST;
	}
}
