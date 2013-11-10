package com.check.v3.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.OrganizationPost;
import com.check.v3.repository.OrganizationPostRepository;
import com.check.v3.service.OrganizationPostService;

@Service("organizatioPostService")
@Repository
@Transactional
public class OrganizationPostServiceImpl implements OrganizationPostService{

	@Autowired
	private OrganizationPostRepository organizationPostRepository;
	@Override
	@Transactional(readOnly=true)
	public OrganizationPost findById(Long Id) {
		return organizationPostRepository.findOne(Id);
	}

	
	@Override
	public void delete(OrganizationPost organizationPost) {
		organizationPostRepository.delete(organizationPost)	;	
	}

}
