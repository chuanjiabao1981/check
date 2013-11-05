package com.check.v3.tools.rest;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import com.check.v3.dto.ErrorsDTO;

public class CustomResponseErrorHandler implements ResponseErrorHandler{
	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
	    return errorHandler.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
	    String theString = IOUtils.toString(response.getBody());
	    ObjectMapper mapper = new ObjectMapper();
	    ErrorsDTO eDTO = mapper.readValue(theString, ErrorsDTO.class);
	    System.err.println(theString);
	    System.err.println(eDTO.getErrors().size());
	    CheckApiException e= new CheckApiException();
	    e.setErrorsDTO(eDTO);
	    throw e;
	}

}
