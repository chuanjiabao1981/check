package com.check.v3.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.check.v3.domain.exception.OrganizationRingException;
import com.check.v3.domain.util.BaseEntityComparer;
import com.google.common.collect.Lists;


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
    @JsonIgnore
	private OrganizationType type 					= OrganizationType.NON_LEAF_NODE;
    
    @ManyToOne
    @JoinColumn(name="department_id")
    @NotNull
    private Department department;
    @ManyToMany(mappedBy="organizations")

    private Set<User> users = new HashSet<User>();
    
//  @JoinTable(name="user_organizations", 
//  joinColumns={@JoinColumn(name="organization_id",referencedColumnName="id",insertable=false, updatable=false)}, 
//  inverseJoinColumns={@JoinColumn(name="user_id",referencedColumnName="id",insertable=false, updatable=false)})
    
    
    @OneToMany(mappedBy = "organization", cascade={CascadeType.REMOVE,CascadeType.MERGE})
    private List<QuickReport> quickReports = new ArrayList<QuickReport>();
    @ManyToMany
    @JoinTable(name="organization_check_templates", 
                joinColumns={@JoinColumn(name="organization_id")}, 
                inverseJoinColumns={@JoinColumn(name="check_template_id")})
    private Set<CheckTemplate> checkTemplates = new HashSet<CheckTemplate>();
    
	public Organization()
	{
	}
	public Organization(Long id)
	{
		this.setId(id);
	}
	public Organization(Department department)
	{
		this.setDepartment(department);
	}
	public Organization(Department department,String name,OrganizationType type)
	{
		this.setDepartment(department);
		this.setName(name);
		this.type = type;;
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
	public void addUser(User user)
	{
		if (this.getUsers().contains(user)){
			return;
		}else{
			this.getUsers().add(user);
		}
		user.addOrganization(this);
	}
	public void removeUser(User user)
	{
		if (!user.getOrganizations().contains(user))
			return;
		this.getUsers().remove(user);
		user.removeOrganization(this);
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public List<User> getListUsers()
	{
		 ArrayList<User> l = Lists.newArrayList(users.iterator());
		 return l;
	}
	public List<QuickReport> getQuickReports() {
		return quickReports;
	}
	public void setQuickReports(List<QuickReport> quickReports) {
		this.quickReports = quickReports;
	}
	
	public Set<CheckTemplate> getCheckTemplates() {
		return checkTemplates;
	}
	public void setCheckTemplates(Set<CheckTemplate> checkTemplates) {
		this.checkTemplates = checkTemplates;
	}
	public List<CheckTemplate> getListCheckTemplates()
	{
		 ArrayList<CheckTemplate> l = Lists.newArrayList(checkTemplates.iterator());
		 Collections.sort(l,new BaseEntityComparer());
		 return l;

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
