package com.check.v3.domain;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "resolves")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="resolve")
public class Resolve extends BaseEntity{
	
	private static final Logger logger = LoggerFactory.getLogger(Resolve.class);


	/**
	 * 
	 */
	private static final long serialVersionUID = -7264315979569436753L;
	
	
	@OneToMany(mappedBy = "resolve", cascade=CascadeType.ALL)
	@OrderBy("id asc")
    private List<ResolveImage> images 	= new ArrayList<ResolveImage>();

	
	@ManyToOne
    @JoinColumn(name="submitter_id")
	private User 				submitter;
	
	private String				description;


	public List<ResolveImage> getImages() {
		return images;
	}


	public void setImages(List<ResolveImage> images) {
		this.images = images;
	}
	
	
	
	public User getSubmitter() {
		return submitter;
	}


	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}

	@Column(name="description")
	@NotEmpty
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public ResolveImage addImage(ResolveImage image)
	{
		return addImage(image,true);
	}
	public ResolveImage addImage(ResolveImage image,boolean set)
	{
		if (image == null){
			logger.trace("add null image to quick report");
			return image;
		}
		image.setSubmitter(this.getSubmitter());
		if (this.getImages().contains(image)){
			this.getImages().set(this.getImages().indexOf(image), image);
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
