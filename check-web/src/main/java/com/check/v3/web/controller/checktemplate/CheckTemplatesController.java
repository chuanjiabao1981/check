package com.check.v3.web.controller.checktemplate;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.check.v3.service.CheckTemplateService;

public class CheckTemplatesController {
	Object s= null;
	@Resource
	protected CheckTemplateService checkTemplateService;
	
	@Value("#{ messageSource.getMessage('module_name_check_template',null,'zh_CN')}")
	private String moduleName;
	@Value("#{ messageSource.getMessage('module_description_check_template',null,'zh_CN')}")
	private String moduleDescription;

	public final static String VIEW_LIST = "check_templates/index";
	public final static String VIEW_NEW	 = "check_templates/new";
	public final static String VIEW_EDIT = "check_templates/edit";
	public final static String VIEW_SHOW = "check_templates/show";
	
	
	@ModelAttribute("moduleName")
	public String getmoduleName()
	{
		return moduleName;
	}
	@ModelAttribute("moduleDescription")
	public String getmoduleDescription()
	{
		return moduleDescription;
	}
}
