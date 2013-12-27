package com.check.v3.web.controller.checktemplate;

import javax.annotation.Resource;

import com.check.v3.service.CheckTemplateService;

public class CheckTemplatesController {

	@Resource
	protected CheckTemplateService checkTemplateService;
	
	public final static String VIEW_LIST = "check_templates/index";
	public final static String VIEW_NEW	 = "check_templates/new";
	public final static String VIEW_EDIT = "check_templates/edit";
	public final static String VIEW_SHOW = "check_templates/show";
}
