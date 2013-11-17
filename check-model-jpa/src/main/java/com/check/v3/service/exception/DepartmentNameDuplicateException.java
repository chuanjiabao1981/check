package com.check.v3.service.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DepartmentNameDuplicateException extends
		DataIntegrityViolationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1187617761572094537L;

	public DepartmentNameDuplicateException(String msg) {
		super(msg);
	}

}
