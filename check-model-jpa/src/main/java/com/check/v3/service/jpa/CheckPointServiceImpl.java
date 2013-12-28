package com.check.v3.service.jpa;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.CheckPoint;
import com.check.v3.repository.CheckPointRepository;
import com.check.v3.service.CheckPointService;

@Service("checkPointService")
@Transactional
@Repository
public class CheckPointServiceImpl implements CheckPointService{

	@Resource
	private CheckPointRepository checkPointRepository;
	
	@Override
	@Transactional
	public CheckPoint save(CheckPoint checkPoint) {
		return checkPointRepository.save(checkPoint);
	}

	@Override
	@Transactional
	public void delete(CheckPoint checkPoint) {
		checkPointRepository.delete(checkPoint);	
	}

	@Override
	@Transactional(readOnly=true)
	public CheckPoint findById(Long id) {
		return checkPointRepository.findOne(id);
	}


}
