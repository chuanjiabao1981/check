package com.check.v3.web.controller.checktemplate.checkpoint;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.check.v3.service.CheckPointService;
import com.check.v3.service.CheckTemplateService;

public class CheckPointsController {

	@Value("#{ messageSource.getMessage('module_name_check_point',null,'zh_CN')}")
	private String moduleName;
	@Value("#{ messageSource.getMessage('module_description_check_point',null,'zh_CN')}")
	private String moduleDescription;

	@Resource
	protected CheckPointService checkPointService;
	@Resource
	protected CheckTemplateService checkTemplateService;
	public final static String VIEW_NEW	 = "check_points/new";
	public final static String VIEW_EDIT = "check_points/edit";
	
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
