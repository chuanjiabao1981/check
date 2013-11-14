package com.check.v3.domain;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.check.v3.domain.exception.OrganizationRingException;


@Entity
@Table(name="units")
@DiscriminatorValue("organization")
public class Organization extends Unit implements Serializable{
	
	private static final Logger logger = LoggerFactory.getLogger(Organization.class);

	private static final long serialVersionUID = -3552455311356671929L;
	
	
	@OneToMany(mappedBy = "parentOrganization",cascade={ CascadeType.ALL},fetch = FetchType.EAGER)
	private List<Organization> subOrganizations		= new LinkedList<Organization>();
	
	@ManyToOne
    @JoinColumn(name="parent_organization_id")
	private Organization parentOrganization;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "organization_type")
	private OrganizationType type 					= OrganizationType.NON_LEAF_NODE;
    
    @ManyToOne
    @JoinColumn(name="department_id")
    @NotNull
    private Department department;
	
	public Organization()
	{
	}
	public Organization(Department department)
	{
		this.setDepartment(department);
	}
	public Organization(String name,OrganizationType type)
	{
		this.setName(name);
		this.type = type;
	}
		
	public List<Organization> getSubOrganizations()
	{
		return subOrganizations;
	}
	
	public Organization getParentOrganization()
	{
		return parentOrganization;
	}
	public OrganizationType getType()
	{
		return type;
	}
	public void setSubOrganizations(List<Organization> subOrganizations) {
		this.subOrganizations = subOrganizations;
	}
	public void setType(OrganizationType type) {
		this.type = type;
	}
	
	
	public void setDepartment(Department department)
	{
		setDepartment(department,true);
	}
	public Department getDepartment()
	{
		return this.department;
	}
	public void setDepartment(Department department,boolean add)
	{
		this.department = department;
		if (department != null && add){
			this.department.addOrganization(this,false);
		}
	}
	public void setParentOrganization(Organization parentOrganization) {
		setParentOrganization(parentOrganization,true);
	}
	/*
	 * add的意思是是否把this object添加到parent的SubOrganizations中。
	 */
	public void setParentOrganization(Organization parentOrganization,boolean add){
		this.parentOrganization = parentOrganization;
		if (parentOrganization!= null && add){
			parentOrganization.addSubOrganization(this,false);
		}
	}
	public Organization addSubOrganization(Organization organization)
	{
		return addSubOrganization(organization,true);
	}
	public Organization addSubOrganization(Organization organization,boolean set)
	{
		if (organization == null){
			logger.warn("null sub organization is given");
			return organization;
		}
		Organization parent = this;
		while(parent != null){
			if (parent.equals(organization)){
				throw new OrganizationRingException("the organization is point to the farther");
			}
			parent = parent.getParentOrganization();
		}
		if (this.type  != OrganizationType.NON_LEAF_NODE){
			logger.warn("this organization can't have child node");
			return organization;
		}
		
		if (this.getSubOrganizations().contains(organization)){
			this.getSubOrganizations().set(this.getSubOrganizations().indexOf(organization), organization);
		}else{
			this.getSubOrganizations().add(organization);
		}
		
		if (set){
			organization.setParentOrganization(this,false);
		}
		return organization;
	}
	public Organization removeSubOrganization(Organization organization)
	{
		this.getSubOrganizations().remove(organization);
		organization.setParentOrganization(null);
		return organization;
	}
	public boolean isContainOrganization(Organization s){
		if (this.getId() != null && s.getId() != null && s.getId() == this.getId()){
			return true;
		}
		if (this.equals(s)){
			return true;
		}
		for(Organization o:this.getSubOrganizations()){
			if (o.isContainOrganization(s)){
				return true;
			}
		}
		return false;
	}
		
	public boolean equals(Object object)
	{
		if (object == this){
			return true;
		}
		if ((object == null) || ! (object instanceof Organization)){
			return false;
		}
		
		final Organization other = (Organization)object;
		
		if ((other.getId()!=null) && (this.getId() !=null)){
			return other.getId().equals(this.getId());
		}
		return false;
	}

}
