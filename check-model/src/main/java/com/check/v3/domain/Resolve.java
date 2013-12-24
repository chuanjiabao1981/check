package com.check.v3.domain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.check.v3.domain.util.BaseEntityComparer;
import com.check.v3.domain.util.CheckImageUtil;
import com.google.common.collect.Lists;

@Entity
@Table(name = "resolves")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="resolve")
public class Resolve extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7264315979569436753L;
	
	@ManyToOne
    @JoinColumn(name="submitter_id")
	private User 				submitter;
	
	@Column(name="description")
	@NotEmpty
	private String				description;
	
	
	@OneToMany(mappedBy = "resolve", cascade=CascadeType.ALL,orphanRemoval=true)
    private Set<ResolveImage> images 	= new HashSet<ResolveImage>();
	
	public User getSubmitter() {
		return submitter;
	}

	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ResolveImage> getImages() {
		return images;
	}


	public void setImages(Set<ResolveImage> images) {
		this.images = images;
	}
	public ResolveImage addImage(ResolveImage image)
	{
		return addImage(image,true);
	}
	public ResolveImage addImage(ResolveImage image,boolean set)
	{
		if (image == null){
			return image;
		}
		image.setSubmitter(this.getSubmitter());
		if (this.getImages().contains(image)){
			this.getImages().add(image);
		}else{
			this.getImages().add(image);
		}
		if (set){
			image.setResolve(this,false);
		}

		return image;
	}
	public void removeImage(ResolveImage image)
	{
		this.getImages().remove(image);
		image.setResolve(null);
	}
	public List<ResolveImage> getListImages()
	{
		 ArrayList<ResolveImage> l = Lists.newArrayList(images.iterator());
		 Collections.sort(l,new BaseEntityComparer());
		 return l;

	}
	public CheckImage buildCheckImage()
	{
		ResolveImage resolveImage = new ResolveImage();
		resolveImage.setDepartment(this.getDepartment());
		resolveImage.setSubmitter(this.getSubmitter());
		resolveImage.setName(CheckImageUtil.BuildImageName(resolveImage));
		this.addImage(resolveImage);
		return resolveImage;

	}
	public boolean equals(Object object)
	{
		if (object == this){
			return true;
		}
		if ((object == null) || ! (object instanceof Resolve)){
			return false;
		}
		
		final Resolve other = (Resolve)object;
		
		if ((other.getId()!=null) && (this.getId() !=null)){
			return other.getId().equals(this.getId());
		}
		return false;

	}


}
