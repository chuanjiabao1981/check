package com.check.v3.tools.rest;


import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.check.v3.domain.Organization;

public class Organizations {

	
	
	static final String ORGANIZATIONS_INDEX = ApiConstant.URI_PREFIX+ApiConstant.API_PREFIX+"/organizations?JSESSIONID=";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "2E2AC1B454920C70038B126DB388B9F7");
	    cookie.setDomain(ApiConstant.DOMAIN);
	    cookie.setPath("/");
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    CookieStore cookieStore = httpClient.getCookieStore();
	    cookieStore.addCookie(cookie);
	    
		ClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		restTemplate.setErrorHandler(new CustomResponseErrorHandler());
		Organization[] s = restTemplate.getForEntity(ORGANIZATIONS_INDEX, Organization[].class).getBody();
		for(Organization o :s){
			System.err.println(o.getName());
		}
	}

}
