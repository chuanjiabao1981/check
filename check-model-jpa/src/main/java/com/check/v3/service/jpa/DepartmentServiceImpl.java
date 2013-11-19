package com.check.v3.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Department;
import com.check.v3.repository.DepartmentRepository;
import com.check.v3.service.DepartmentService;
import com.google.common.collect.Lists;

@Service("departmentService")
@Transactional
@Repository
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@PersistenceContext 
	private EntityManager em;


	@Override
	@Transactional
	public Department save(Department department){
			return departmentRepository.save(department);
	}
	
	@Override
	@Transactional
	public void delete(Department department) {
		 departmentRepository.delete(department);
	}

	@Override
	@Transactional(readOnly=true)
	public Department findByName(String name) {
		return departmentRepository.findByName(name);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Department> findAll() {
		return Lists.newArrayList(departmentRepository.findAll());
	}

	@Override
	@Transactional(readOnly=true)
	public Department findById(Long id) {
		return departmentRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		departmentRepository.delete(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Department findByIdWithUsers(Long id) {
		return em.createNamedQuery("Department.findByIdWithUsers", Department.class).setParameter("id", id).getSingleResult();
	}

}
