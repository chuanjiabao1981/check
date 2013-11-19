package com.check.v3.service;

import java.util.List;

import com.check.v3.domain.Department;
import com.check.v3.service.exception.DepartmentNameDuplicateException;

public interface DepartmentService {
	public Department save(Department department) throws DepartmentNameDuplicateException;
	public void delete(Department department);
	public void delete(Long id);
	public Department findByName(String name);
	public Department findById(Long id);
	public Department findByIdWithUsers(Long id);
	public List<Department> findAll();

}
