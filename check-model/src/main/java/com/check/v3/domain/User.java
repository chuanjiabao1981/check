package com.check.v3.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;


@Entity
@Table(name = "users")
@NamedQueries({
	@NamedQuery(name="User.findByDepartmentId",query="select distinct u from User u where u.department.id = :id and u.role in :roles"),
	@NamedQuery(name="User.findByIdWithOrganizations",query="select u from User u " +
															"left join fetch u.organizations o " +
														    "left join fetch o.users " +
															"where u.id = :id")
})
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
    private Role					role;
    
    private Set<Organization>		organizations	= new HashSet<Organization>();
    
	@OneToMany(mappedBy = "submitter", cascade={CascadeType.REMOVE,CascadeType.MERGE})
    private List<QuickReport>		submittedQuickReport 	= new ArrayList<QuickReport>();
	
	@OneToMany(mappedBy = "responsiblePerson", cascade={CascadeType.REMOVE,CascadeType.MERGE})
    private List<QuickReport>	    responsibleQuickReport	 = new ArrayList<QuickReport>();
    
    
	public User()
	{
		
	}
	public User(Long id)
	{
		this.id = id;
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
	@JoinColumn(name="department_id")
	@NotNull
	public Department getDepartment()
	{
		return this.department;
	}


    @Enumerated(EnumType.STRING)
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
	public Set<Organization> getOrganizations() {
		return organizations;
	}
	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}
	
	public List<Organization> getListOrganizations()
	{
		return Lists.newArrayList(organizations.iterator());
	}
	
	@Transient
	public boolean isDpartmentAdmin()
	{
		if (role == Role.DEPARTMENT_ADMIN){
			return true;
		}
		return false;
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
