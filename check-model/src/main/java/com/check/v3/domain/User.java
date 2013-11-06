package com.check.v3.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.check.v3.domain.Organization;


@Entity
@Table(name = "users")
public class User implements Serializable,Affiliation {
	
	private static final Logger logger = LoggerFactory.getLogger(User.class);

	private static final long serialVersionUID = -15913810676325L;
	
	private Long 					id;
	private int 					version; 
	@NotEmpty(message="{validation.name.NotEmpty.message}")
	@Size(min=5, max=60, message="{validation.user.name.Size.message}")
	private String 					name;
	@NotEmpty(message="{validation.account.NotEmpty.message}")
	@Size(min=4, max=60, message="{validation.user.account.Size.message}")
	private String  				account;
	private String  				password;
	private String  				password_verify;
	private String 					password_cryp;
	private Set<OrganizationPost> 	organizationPosts 	= new HashSet<OrganizationPost>();
	
	
	public User()
	{
		
	}
	public User(String account)
	{
		this.account = account;
	}
	public User(String account,String name)
	{
		this(account);
		this.name = name;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY) 
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	
	@Column(name = "version")
	public int getVersion() {
		return version;
	}
	
	@Column(name = "name",unique=true)
	public String getName() {
		return name;
	}
	
	@Column(name="password_cryp")
	public String getPassword_cryp() {
		return password_cryp;
	}
	
	@Column(name="account",unique=true)
	@NotNull
	public String getAccount() {
		return account;
	}
	@Override
	@Transient
	public Set<Organization> getBelongsToOrganizations() {
		if (organizationPosts == null || organizationPosts.isEmpty()){
			return null;
		}
		Set<Organization> s = new HashSet<Organization>();
		for(OrganizationPost post:organizationPosts){
			s.add(post.getOrganization());
		}
		return s;
		
	}
	
	@ManyToMany
    @JoinTable(name = "user_organization_posts",
    		   joinColumns = @JoinColumn(name = "user_id"),
    		   inverseJoinColumns = @JoinColumn(name = "organization_post_id"))
	public Set<OrganizationPost> getOrganizationPosts()
	{
		return organizationPosts;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	public String getPassword() {
		return password;
	}

	@Transient
	public String getPassword_verify() {
		return password_verify;
	}

	public void setPassword_verify(String password_verify) {
		this.password_verify = password_verify;
	}

	public void setPassword_cryp(String password_cryp) {
		this.password_cryp = password_cryp;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	public void setOrganizationPosts(Set<OrganizationPost> organizationPosts) {
		this.organizationPosts = organizationPosts;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof User)){
			return false;
		}
		User other = (User) obj;
		if (this.getId() != null && other.getId() != null && this.getId() == other.getId()){
			return true;
		}
		if (other.getAccount() != null){
			if (other.getAccount().equals(this.account)){
				return true;
			}
		}else{
			if (this.account == null){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

}
