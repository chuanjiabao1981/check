package com.check.v3.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@MappedSuperclass
public abstract class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2155457150797011563L;

	
	@Id
	@GeneratedValue(strategy = IDENTITY) 
    @Column(name = "id")
	private Long id;
	
	
	@Column(name = "created_at")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(iso=ISO.DATE)
	private DateTime 		createdAt;
	
	
	@Column(name = "updated_at")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(iso=ISO.DATE)
	private DateTime 		updatedAt;
	

	public Long getId() {
		return id;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public DateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@PreUpdate
    public void preUpdate() {
		updatedAt = new DateTime();
    }
    
    @PrePersist
    public void prePersist() {
    	DateTime now = new DateTime();
    	createdAt = now;
        updatedAt = now;
    }
    
    /*
     *  这里设计为Unidirectional，
     *  原因是没有人会通过department统计BaseEntity
     */
	@ManyToOne
	@JoinColumn(name="department_id")
	@NotNull
	private Department		department;
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
}
