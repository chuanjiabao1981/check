package com.check.v3.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.check.v3.domain.exception.OrganizationRingException;
import com.google.common.collect.Lists;


@Entity
@Table(name="organizations")
public class Organization implements Serializable,Affiliation{
	
	private static final Logger logger = LoggerFactory.getLogger(Organization.class);

	private static final long serialVersionUID = -3552455311356671929L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY) 
	@Column(name = "id")
	private Long 	id;
	
	@Column(name = "name")
	@NotNull
	private String 	name;
	
	@OneToMany(mappedBy = "organization", cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<OrganizationPost> organizationPosts = new HashSet<OrganizationPost>();
	
	@OneToMany(mappedBy = "parentOrganization",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Organization> subOrganizations		= new LinkedList<Organization>();
	
	@ManyToOne
    @JoinColumn(name="parent_id")
	private Organization parentOrganization;
	
    @Enumerated(EnumType.STRING)
	private OrganizationType type 					= OrganizationType.NON_LEAF_NODE;
	
	public Organization()
	{
		OrganizationPost op1 = new OrganizationPost(OrganizationPostType.SUPERVISOR);
		OrganizationPost op2 = new OrganizationPost(OrganizationPostType.MEMEBER);
		OrganizationPost op3 = new OrganizationPost(OrganizationPostType.ADMIN);
		op1.setOrganization(this);
		op2.setOrganization(this);
		op3.setOrganization(this);
		organizationPosts.add(op1);
		organizationPosts.add(op2);
		organizationPosts.add(op3);
	}
	public Organization(String name,OrganizationType type)
	{
		this();
		this.name = name;
		this.type = type;
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Set<OrganizationPost> getOrganizationPosts() {
		return organizationPosts;
	}
	public OrganizationPost getOrganizationPost(OrganizationPostType type)
	{
		for(OrganizationPost post:organizationPosts){
			if (post.getType().equals(type))
				return post;
		}
		return null;
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
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSubOrganizations(List<Organization> subOrganizations) {
		this.subOrganizations = subOrganizations;
	}
	public void setType(OrganizationType type) {
		this.type = type;
	}
	
	
	public void setOrganizationPosts(Set<OrganizationPost> organizationPosts) {
		this.organizationPosts = organizationPosts;
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
	@Override
	@Transient
	public List<Organization> getBelongsToOrganizations() {
		if (parentOrganization != null){
			return Lists.newArrayList(parentOrganization);
		}
		return null;
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
		
		if ((other.getId()!=null) && (id !=null)){
			return other.getId().equals(id);
		}
		return false;
	}

}
