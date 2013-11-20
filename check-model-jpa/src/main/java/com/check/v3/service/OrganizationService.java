package com.check.v3.service;

import java.util.List;

import com.check.v3.domain.Organization;

public interface OrganizationService extends InstanceLoaderService{
	public Organization 			findById(Long id);
	public Organization 			findByName(String name);
	public Organization 			save(Organization organization);
	public List<Organization> 		findAllByDepartmentId(Long departmentId);
	public void 					delete(Organization organization);
	public void 					delete(Long id);
}
