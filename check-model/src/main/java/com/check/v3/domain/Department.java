package com.check.v3.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Entity
@Table(name="units")
@DiscriminatorValue("department")
@NamedQuery(name="Department.findByIdWithUsers",query="select distinct d from Department d left join fetch d.users u where d.id = :id")
public class Department extends Unit implements Serializable{
	
	private static final Logger logger = LoggerFactory.getLogger(Department.class);

	
	private static final long serialVersionUID = 5620006938704778198L;
	
	@OneToMany(mappedBy = "department", cascade={CascadeType.REMOVE,CascadeType.MERGE})
	private List<Organization> organizations   = new ArrayList<Organization>();
	@OneToMany(mappedBy = "department", cascade={CascadeType.REMOVE,CascadeType.MERGE})
	private List<User> users 					= new ArrayList<User>();
	@OneToMany(mappedBy = "department", cascade={CascadeType.REMOVE,CascadeType.MERGE})
	private List<QuickReport> quickReports		= new ArrayList<QuickReport>();
	public Department()
	{}
	public Department(String name)
	{
		this.setName(name);
	}
	public Organization addOrganization(Organization organization)
	{
		return addOrganization(organization,true);
	}
	public Organization addOrganization(Organization organization,boolean set)
	{
		if (organization == null){
			logger.trace("add null organization to department");
			return organization;
		}
		
		if (this.getOrganizations().contains(organization)){
			this.getOrganizations().set(this.getOrganizations().indexOf(organization), organization);
		}else{
			this.getOrganizations().add(organization);
		}
		if (set){
			organization.setDepartment(this,false);
		}
		return organization;
	}
	public void removeOrganization(Organization organization)
	{
		for(Organization t: organization.getSubOrganizations()){
			this.removeOrganization(t);
		}
		this.getOrganizations().remove(organization);
		organization.setDepartment(null);
	}
	
	public List<Organization> getOrganizations() {
		return organizations;
	}
	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}
	
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<User> getDepartmentAdmins()
	{
		Collection<User> u = Collections2.filter(users, new Predicate<User>(){
			public boolean apply(User i)
			{
				if (i.getRole() == Role.DEPARTMENT_ADMIN){
					return true;
				}
				return false;
			}
		} );
		return Lists.newArrayList(u.iterator());
	}
	public boolean equals(Object object)
	{
		if (object == this){
			return true;
		}
		if ((object == null) || ! (object instanceof Department)){
			return false;
		}
		
		final Department other = (Department)object;
		
		if ((other.getId()!=null) && (this.getId() !=null)){
			return other.getId().equals(this.getId());
		}
		return false;
	}


}
