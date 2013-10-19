package com.check.v3.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class SessionDTO implements Serializable{
	
	private static final long serialVersionUID = -901893144190378361L;
	private String sessionId;

	public SessionDTO(String sessionId)
	{
		this.sessionId = sessionId;
	}
	@JsonProperty("session_id")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
}
