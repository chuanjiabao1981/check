package com.check.v3.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
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

import com.google.common.collect.Sets;


@Entity
@Table(name="organizations")
public class Organization implements Serializable,Affiliation{
	
	private static final Logger logger = LoggerFactory.getLogger(Organization.class);

	private static final long serialVersionUID = -3552455311356671929L;
	
	private Long 	id;
	private String 	name;
	private Set<OrganizationPost> organizationPosts = new HashSet<OrganizationPost>();
	private Set<Organization> subOrganizations		= new HashSet<Organization>();
	private OrganizationType type 					= OrganizationType.NON_LEAF_NODE;
	private Organization parentOrganization;
	
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
	
	@Id
	@GeneratedValue(strategy = IDENTITY) 
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	@Column(name = "name")
	@NotNull
	public String getName() {
		return name;
	}
	@OneToMany(mappedBy = "organization", cascade=CascadeType.ALL, orphanRemoval=true,fetch = FetchType.EAGER)
	public Set<OrganizationPost> getOrganizationPosts() {
		return organizationPosts;
	}
	//TODO::eager load 优化之?
	@OneToMany(mappedBy = "parentOrganization",cascade=CascadeType.ALL,orphanRemoval=true,fetch = FetchType.EAGER)
	public Set<Organization> getSubOrganizations()
	{
		return subOrganizations;
	}
	@ManyToOne
    @JoinColumn(name="parent_id")
	public Organization getParentOrganization()
	{
		return parentOrganization;
	}
    @Enumerated(EnumType.STRING)
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
	//TODO::
	// 1. check subOrganization parent organization exsits?
	// 2. validator only NON_LEAF_NODE can has organization 
	public void setSubOrganizations(Set<Organization> subOrganizations) {
		this.subOrganizations = subOrganizations;
	}
	public void setType(OrganizationType type) {
		this.type = type;
	}
	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}
	
	public void setOrganizationPosts(Set<OrganizationPost> organizationPosts) {
		this.organizationPosts = organizationPosts;
	}
	public void addSubOrganization(Organization organization)
	{
		if (this.type  != OrganizationType.NON_LEAF_NODE){
			logger.warn("this organization can't have child node");
			return;
		}
		organization.setParentOrganization(this);
		this.subOrganizations.add(organization);
	}
	public boolean isContainOrganization(Organization s){
		if (s.getId() == this.getId()){
			return true;
		}
		for(Organization o:this.getSubOrganizations()){
			return o.isContainOrganization(s);
		}
		return false;
	}
	@Override
	@Transient
	public Set<Organization> getBelongsToOrganizations() {
		if (parentOrganization != null){
			return Sets.newHashSet(parentOrganization);
		}
		return null;
	}

}
