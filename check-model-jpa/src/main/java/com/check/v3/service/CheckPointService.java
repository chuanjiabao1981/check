package com.check.v3.service;

import com.check.v3.domain.CheckPoint;

public interface CheckPointService {

	public CheckPoint save(CheckPoint checkPoint);
	public CheckPoint findById(Long id);
	public void delete(CheckPoint checkPoint);

}
