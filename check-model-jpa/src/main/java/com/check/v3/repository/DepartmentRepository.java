package com.check.v3.repository;

import org.springframework.data.repository.CrudRepository;

import com.check.v3.domain.Department;


public interface DepartmentRepository  extends CrudRepository<Department,Long>
{
}
