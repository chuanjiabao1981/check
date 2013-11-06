package com.check.v3.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.check.v3.domain.Organization;


@Entity
@Table(name = "organization_posts")
public class OrganizationPost implements Serializable,Affiliation{
	
	private static final long serialVersionUID = -4907955816987201008L;
	
	private Long id;
	private OrganizationPostType type;
	private Organization organization;
	private Set<User> users 			= new HashSet<User>();
	
	public OrganizationPost()
	{}
	public OrganizationPost(OrganizationPostType type)
	{
		this.type = type;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY) 
	@Column(name = "id")
	public Long getId() {
		return id;
	}
    @Enumerated(EnumType.STRING)
	public OrganizationPostType getType() {
		return type;
	}
    @ManyToOne
    @JoinColumn(name = "organization_id")
	@NotNull
	public Organization getOrganization() {
		return organization;
	}
    @ManyToMany(mappedBy = "organizationPosts")
    public Set<User> getUsers()
    {
    	return users;
    }
    public void setId(long id) {
		this.id = id;
	}
	public void setType(OrganizationPostType type) {
		this.type = type;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@Override
	@Transient
	public Set<Organization> getBelongsToOrganizations() {
		return null;
	}
	
	
}
