package com.check.v3.tools.rest;

import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.check.v3.dto.SessionDTO;
import com.check.v3.dto.UserPasswordDTO;


public class Session {
	
	public static final String HOST 				= "localhost:8088";
//	public static final String HOST					= "42.121.55.211:8088";
	public static final String CONTEXT_ROOT 		= "/check-service";
	public static final String URI_PREFIX 			= "http://"+HOST+CONTEXT_ROOT;
	public static final String API_PREFIX			= "/api/v1";
	public static final String SESSION_CREATE 		= URI_PREFIX+API_PREFIX+"/sessions/create";
			
	public static void main(String[] args)  
	{
		ClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(new DefaultHttpClient());
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		UserPasswordDTO userPasswordDTO = new UserPasswordDTO();
		userPasswordDTO.setName("ceshi_002");
		userPasswordDTO.setPassword("12345");
		try{
			System.err.println(SESSION_CREATE);
			SessionDTO s = restTemplate.postForObject(SESSION_CREATE, userPasswordDTO, SessionDTO.class);
			System.err.println(s.toString());
		}catch(ResourceAccessException ex){
			if (ex.getCause() instanceof CheckApiException){
				System.err.println("xxxxxxx");
				CheckApiException cae = (CheckApiException) ex.getCause();
				System.err.println(cae.toString());
			}
		}
	}


}
