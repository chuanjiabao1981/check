package com.check.v3.service.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UserAccountDuplicateException extends DataIntegrityViolationException{

	public UserAccountDuplicateException(String msg) {
		super(msg);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5765187986319696133L;

}
