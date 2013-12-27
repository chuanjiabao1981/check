package com.check.v3.service.jpa;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.CheckTemplate;
import com.check.v3.domain.Department;
import com.check.v3.repository.CheckTemplateRepository;
import com.check.v3.service.CheckTemplateService;

@Service("checkTemplateService")
@Transactional
@Repository
public class CheckTemplateServiceImpl implements CheckTemplateService{
	@Resource
	private CheckTemplateRepository checkTemplateRepository;

	@Override
	@Transactional
	public CheckTemplate save(CheckTemplate checkTemplate) {
		return checkTemplateRepository.save(checkTemplate);
	}

	@Override
	@Transactional(readOnly=true)
	public List<CheckTemplate> findAllByDepartment(Department department) {
		return checkTemplateRepository.findAllByDepartment(department);
	}

	@Override
	@Transactional
	public void delete(CheckTemplate checkTemplate) {
		checkTemplateRepository.delete(checkTemplate);
	}

	@Override
	@Transactional(readOnly=true)
	public CheckTemplate findById(Long id) {
		return checkTemplateRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly=true)
	public CheckTemplate findByIdWithCheckPoints(Long id) {
		return checkTemplateRepository.findByIdWithCheckPoints(id);
	}


}
