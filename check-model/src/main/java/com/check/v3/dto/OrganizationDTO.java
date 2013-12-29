package com.check.v3.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.check.v3.domain.CheckTemplate;
import com.check.v3.domain.Organization;
import com.check.v3.domain.User;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class OrganizationDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5740797606343858500L;
	private String name;
	private Long id;
	private List<UserDTO> 				users 			= new ArrayList<UserDTO>();
	private List<CheckTemplateDTO>		checkTemplates	= new ArrayList<CheckTemplateDTO>();
	public OrganizationDTO()
	{}
	public OrganizationDTO(Organization  organization,boolean containUser)
	{
		name 	= organization.getName();
		id 		= organization.getId();
		if (containUser){
			users 	= Lists.transform(organization.getListUsers(), new Function<User, UserDTO>(){
				public UserDTO apply(User user){
					return new UserDTO(user);
				}
			});
			checkTemplates = Lists.transform(organization.getListCheckTemplates(), new Function<CheckTemplate, CheckTemplateDTO>(){
				public CheckTemplateDTO apply(CheckTemplate checkTemplate){
					return new CheckTemplateDTO(checkTemplate);
				}
			});
		}
	}
	public String getName() {
		return name;
	}
	public Long getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<UserDTO> getUsers() {
		return users;
	}
	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
	public List<CheckTemplateDTO> getCheckTemplates() {
		return checkTemplates;
	}
	public void setCheckTemplates(List<CheckTemplateDTO> checkTemplates) {
		this.checkTemplates = checkTemplates;
	}
	
	
}
