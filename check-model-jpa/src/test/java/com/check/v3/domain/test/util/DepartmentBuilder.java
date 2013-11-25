package com.check.v3.domain.test.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.v3.domain.Department;
import com.check.v3.service.DepartmentService;

@Service("departmentServiceBuilderService")
public class DepartmentBuilder {
	
	@Resource
	DepartmentService departmentService;
	
	public Department build(String name)
	{
		Department d = new Department(name);
		return d;
	}
	public Department create(String name)
	{
		return departmentService.save(build(name));
	}
}
