package com.check.v3.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "reports")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="report")
public class Report implements Serializable{

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
	
	@ManyToOne
    @JoinColumn(name="department_id")
	private Department		department;
}
