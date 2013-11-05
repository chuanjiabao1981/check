package com.check.v3.jsr303.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.check.v3.domain.User;

@Service("localBeanValidationService")
public class LocalBeanValidationService {
	@Autowired
	private Validator validator;

	public Set<ConstraintViolation<User>> validateUser(User user) {
		return validator.validate(user);
	}
}
