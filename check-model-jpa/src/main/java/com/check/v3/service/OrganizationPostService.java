package com.check.v3.service;

import com.check.v3.domain.OrganizationPost;

public interface OrganizationPostService {
	public OrganizationPost findById(Long Id);
	public void delete(OrganizationPost organizationPost);
}
