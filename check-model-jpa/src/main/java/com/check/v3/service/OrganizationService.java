package com.check.v3.service;

import com.check.v3.domain.Organization;

public interface OrganizationService extends LoadInstanceService{
	public Organization findById(Long id);
	public Organization findByName(String name);
	public Organization save(Organization organization);
	public void delete(Organization organization);
}
