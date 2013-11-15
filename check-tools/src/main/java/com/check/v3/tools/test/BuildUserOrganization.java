package com.check.v3.tools.test;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.check.v3.domain.Department;
import com.check.v3.service.DepartmentService;

public class BuildUserOrganization {
	
	
	public static void main(String[] args)
	{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath*:/application-context.xml");
		ctx.refresh();
	}
	
	public static Department buildDepartment(GenericXmlApplicationContext ctx,String name)
	{
		DepartmentService ds = (DepartmentService) ctx.getBean("departmentService");
		Department		  d  = new Department(name);
		ds.save(d);
		return d;
	}
	
	
	
}
