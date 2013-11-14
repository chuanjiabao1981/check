package com.check.v3.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Department;
import com.check.v3.repository.DepartmentRepository;
import com.check.v3.service.DepartmentService;

@Service("departmentService")
@Repository
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	@Transactional
	public Department save(Department department) {
		return departmentRepository.save(department);
	}

}
