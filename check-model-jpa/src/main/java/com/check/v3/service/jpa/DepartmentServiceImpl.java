package com.check.v3.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Department;
import com.check.v3.repository.DepartmentRepository;
import com.check.v3.service.DepartmentService;
import com.check.v3.service.exception.DepartmentNameDuplicateException;
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
	@Transactional(rollbackFor={DepartmentNameDuplicateException.class,DataIntegrityViolationException.class})
	public Department save(Department department) throws DataIntegrityViolationException,DepartmentNameDuplicateException{
		try{
			return departmentRepository.save(department);
		}catch(DataIntegrityViolationException ex){
			throw new DepartmentNameDuplicateException("name");
		}
	}
	@Transactional
	public Department update(Department department)
	{
		Department s = em.merge(department);
		return s;
		
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
	public Department findById(Long id) {
		return departmentRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		departmentRepository.delete(id);
	}

}
