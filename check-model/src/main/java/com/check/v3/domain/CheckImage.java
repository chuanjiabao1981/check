package com.check.v3.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "check_images")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="check_image")
public class CheckImage extends BaseEntity{

	private static final long serialVersionUID = -6047258182419503689L;
	
	
	
	@Column(name="name")
	String name;
	@Column(name = "orignial_name")
	String originalName;
	
	
	@Transient
	MultipartFile file;
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	

}
