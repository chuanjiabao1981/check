package com.check.v3.service.jpa;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.ResolveImage;
import com.check.v3.repository.ResolveImageRepository;
import com.check.v3.service.ResolveImageService;

@Service("resolveImageService")
@Repository
@Transactional
public class ResolveImageServiceImpl implements ResolveImageService{

	@Resource
	private ResolveImageRepository  resolveImageRepository;

	@Override
	@Transactional
	public void save(ResolveImage resolveImage) {
		resolveImageRepository.save(resolveImage);
	}

}
