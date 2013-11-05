package com.check.v3.tools.rest;


import java.io.IOException;

import com.check.v3.dto.ErrorsDTO;

public class CheckApiException extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7127287828377791702L;
	
	private ErrorsDTO errorsDTO;
	public CheckApiException()
	{
		
	}
	public ErrorsDTO getErrorsDTO() {
		return errorsDTO;
	}
	public void setErrorsDTO(ErrorsDTO errorsDTO) {
		this.errorsDTO = errorsDTO;
	}
	
	
	
}
