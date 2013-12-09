package com.check.v3.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="check_images")
@DiscriminatorValue("resolve_image")
public class ResolveImage extends CheckImage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4852856752528528942L;
	
	@ManyToOne
    @JoinColumn(name="resolve_id")
	private Resolve resolve;
	
	
	
	
	public Resolve getResolve() {
		return resolve;
	}
	public void setResolve(Resolve resolve) {
		setResolve(resolve,true);
	}
	public void setResolve(Resolve resolve,boolean add)
	{
		this.resolve = resolve;
		if (resolve != null && add){
			this.resolve.addImage(this,false);
		}
	}

	
	public boolean equals(Object object)
	{
		if (object == this){
			return true;
		}
		if ((object == null) || ! (object instanceof ResolveImage)){
			return false;
		}
		
		final ResolveImage other = (ResolveImage)object;
		
		if ((other.getId()!=null) && (this.getId() !=null)){
			return other.getId().equals(this.getId());
		}
		return false;
	}

}
