package com.check.v3.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity
@Table(name = "users")
public class User implements Serializable {
	
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
	
    
	private Department				department;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @NotNull
    private Role					role;
    
    
    private List<Organization>		organizations	= new ArrayList<Organization>();
    
    
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
	
	public void setDepartment(Department d)
	{
		this.department = d;
	}
	@ManyToOne
	@JoinColumn(name="department_id", nullable=false)
	public Department getDepartment()
	{
		return this.department;
	}

	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void addOrganization(Organization o)
	{
		if (this.getOrganizations().contains(o)){
			return;
		}else{
			this.getOrganizations().add(o);
		}
		o.addUser(this);
	}
	
	public void removeOrganization(Organization o)
	{
		if (!this.getOrganizations().contains(o))
			return;
		this.getOrganizations().remove(o);
		o.removeUser(this);
	}
	@ManyToMany
    @JoinTable(name="user_organizations", 
                joinColumns={@JoinColumn(name="user_id")}, 
                inverseJoinColumns={@JoinColumn(name="organization_id")})
	public List<Organization> getOrganizations() {
		return organizations;
	}
	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}
	public boolean equals(Object object)
	{
		if (object == this){
			return true;
		}
		if ((object == null) || ! (object instanceof User)){
			return false;
		}
		
		final User other = (User)object;
		
		if ((other.getId()!=null) && (this.getId() !=null)){
			return other.getId().equals(this.getId());
		}
		return false;

	}

}
