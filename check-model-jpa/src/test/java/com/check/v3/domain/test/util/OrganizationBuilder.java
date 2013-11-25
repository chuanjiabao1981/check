package com.check.v3.domain.test.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.domain.OrganizationType;
import com.check.v3.service.OrganizationService;

@Service("organizationBuilderServiceTest")
public class OrganizationBuilder {

	@Resource
	OrganizationService organizationService;
	
	public Organization build(Department department)
	{
		Organization o = new Organization(department,"TEST_ORGANIZATION",OrganizationType.LEAF_NODE);
		return o;
	}
	public Organization create(Department department)
	{
		return organizationService.save(build(department));
	}
}
