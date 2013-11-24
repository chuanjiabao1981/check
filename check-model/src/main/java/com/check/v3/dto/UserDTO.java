package com.check.v3.dto;

import java.io.Serializable;

import com.check.v3.domain.Role;
import com.check.v3.domain.User;

public class UserDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4432399009496676485L;
	
	
	private Long id;
	private String name;
	private String account;
	private Role   role;
	
	public UserDTO(){}
	public UserDTO(User u){
		id			= u.getId();
		name 		= u.getName();
		account 	= u.getAccount();
		role		= u.getRole();
	}
	public String getName() {
		return name;
	}
	public String getAccount() {
		return account;
	}
	public Role getRole() {
		return role;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
