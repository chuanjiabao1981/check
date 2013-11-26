package com.check.v3.dto;

import java.util.List;

public class OrganizationListDTO {
	private List<OrganizationDTO> organizations;
	
	public OrganizationListDTO()
	{
		
	}
	public OrganizationListDTO(List<OrganizationDTO> organizations)
	{
		this.organizations = organizations;
	}
	public List<OrganizationDTO> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<OrganizationDTO> organizations) {
		this.organizations = organizations;
	}
	
}
