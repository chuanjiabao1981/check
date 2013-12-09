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
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "units")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="unit")
public class Unit implements Serializable{
	
	private static final long serialVersionUID = 1538046195130155882L;

	@Id
	@GeneratedValue(strategy = IDENTITY) 
    @Column(name = "id")
	private Long id;
	
    @Column(name = "name")
    @NotEmpty
	private String name;
    
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
