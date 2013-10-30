package com.check.v3.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.repository.OrganizationRepository;
import com.check.v3.service.OrganizationService;
import com.check.v3.domain.Organization;

@Service("organizationService")
@Repository
@Transactional
public class OrganizationServiceImpl implements OrganizationService{

	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	@Transactional(readOnly=true)
	public Object load(Long id) {
		return findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Organization findById(Long id) {
		return organizationRepository.findOne(id);
	}

	@Override
	public Organization save(Organization organization) {
		return organizationRepository.save(organization);
	}

	@Override
	public void delete(Organization organization) {
		organizationRepository.delete(organization);
	}

	@Override
	@Transactional(readOnly=true)
	public Organization findByName(String name) {
		return organizationRepository.findByName(name);
	}

}
