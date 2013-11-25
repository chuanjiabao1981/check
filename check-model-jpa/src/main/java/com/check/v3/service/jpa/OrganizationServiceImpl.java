package com.check.v3.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	
	@PersistenceContext 
	private EntityManager em;

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
	@Transactional
	public Organization save(Organization organization) {
		return organizationRepository.save(organization);
	}

	@Override
	@Transactional
	public void delete(Organization organization) {
		Organization o = em.merge(organization);
		o.getParentOrganization().removeSubOrganization(o);
		o.getDepartment().removeOrganization(o);
		em.remove(o);
	}

	@Override
	@Transactional(readOnly=true)
	public Organization findByName(String name) {
		return organizationRepository.findByName(name);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Organization> findAllByDepartmentId(Long departmentId) {
		CriteriaBuilder cb 					= em.getCriteriaBuilder();
		CriteriaQuery<Organization> c 		= cb.createQuery(Organization.class);
		Root<Organization> emp 				= c.from(Organization.class);
		c.select(emp).where(cb.equal(emp.get("department").get("id"), departmentId));
		return em.createQuery(c).getResultList();
	}

	@Override
	public void delete(Long id) {
		organizationRepository.delete(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Organization findByIdWithUsers(Long id) {
		return organizationRepository.findWithUsers(id);
	}

}
