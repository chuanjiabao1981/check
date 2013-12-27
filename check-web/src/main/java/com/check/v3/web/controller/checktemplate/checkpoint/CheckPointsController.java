package com.check.v3.web.controller.checktemplate.checkpoint;

import javax.annotation.Resource;

import com.check.v3.service.CheckPointService;
import com.check.v3.service.CheckTemplateService;

public class CheckPointsController {

	@Resource
	protected CheckPointService checkPointService;
	@Resource
	protected CheckTemplateService checkTemplateService;
	public final static String VIEW_NEW	 = "check_points/new";
	public final static String VIEW_EDIT = "check_points/edit";

}
