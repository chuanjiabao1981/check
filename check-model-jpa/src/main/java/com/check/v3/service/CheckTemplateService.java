package com.check.v3.service;

import java.util.List;

import com.check.v3.domain.CheckTemplate;
import com.check.v3.domain.Department;

public interface CheckTemplateService {
	public CheckTemplate			findById(Long id);
	public CheckTemplate			findByIdWithCheckPoints(Long id);
	public List<CheckTemplate> 		findAllByDepartment(Department department);
	public CheckTemplate 			save(CheckTemplate checkTemplate);
	public void 					delete(CheckTemplate checkTemplate);

}
