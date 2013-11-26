package com.check.v3.tools.rest;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

public class QuickReports {
	
	
	public static void main(String[] args) {
		String 			  session 		= "2E2AC1B454920C70038B126DB388B9F7";
		Long			  organization	= 258L;
		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", session);
	    cookie.setDomain(ApiConstant.DOMAIN);
	    cookie.setPath("/");
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    CookieStore cookieStore = httpClient.getCookieStore();
	    cookieStore.addCookie(cookie);
	    
	}

}
