package com.check.v3.dto;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import com.check.v3.domain.Role;
import com.check.v3.domain.User;

public class SessionDTO implements Serializable{
	
	private static final long serialVersionUID = -901893144190378361L;
	@JsonProperty("jsession_id")
	private String jsessionId;
	
	private String account;
	private String name;
	private Role   role;
	private Long   id;

	public SessionDTO()
	{}
	public SessionDTO(String sessionId,User user)
	{
		this.jsessionId = sessionId;
		this.account   = user.getAccount();
		this.name 	   = user.getName();
		this.role 	   = user.getRole();
		this.id 	   = user.getId();
	}
	public String getJsessionId() {
		return jsessionId;
	}
	public String getAccount() {
		return account;
	}
	public String getName() {
		return name;
	}
	public Role getRole() {
		return role;
	}
	public Long getId() {
		return id;
	}
	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "SessionDTO [jsessionId=" + jsessionId + ", account=" + account
				+ ", name=" + name + ", role=" + role + ", id=" + id + "]";
	}
	
	
}
