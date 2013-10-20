package com.check.v3.repository;

import org.springframework.data.repository.CrudRepository;

import com.check.v3.domain.Organization;

public interface OrganizationRepository extends CrudRepository<Organization,Long>{
	public Organization findByName(String name);
}
