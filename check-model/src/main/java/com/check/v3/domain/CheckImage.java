package com.check.v3.domain;


import static org.imgscalr.Scalr.resize;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;



@Entity
@Table(name = "check_images")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="check_image")
public class CheckImage extends BaseEntity{
	
	private static final String IMAGE_TYPE="jpg";
	private static final String IMAGE_SUFFIX="."+IMAGE_TYPE;
	private static final String IMAGE_PATH_PREFIX = "/var/check_v3_data/check_data/";
	

	private static final Logger logger = LoggerFactory.getLogger(CheckImage.class);

	
	private static final long serialVersionUID = -6047258182419503689L;

	@Transient
	MultipartFile file;
	
	@Column(name="name")
	String name;
	
	@Column(name = "orignial_name")
	String originalName;
	
	@ManyToOne
    @JoinColumn(name="submitter_id")
	private User submitter;
	
	@Transient
	private boolean del = false;
	
	public String getName(CheckImageType type)
	{
		return name+"-"+type.getText();
	}
	public String getName() {
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	public User getSubmitter() {
		return submitter;
	}
	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	 @PostPersist 
	 void onPostPersist() 
	 {
		 if (file != null && !file.isEmpty()){
			 save();
		 }
	 }	
	 @PostUpdate 
	 void onPostUpdate()
	 {

		 if(file != null && !file.isEmpty()){
			 save();
		 }
	 }
	 @PostRemove 
	 void onPostRemove() 
	 {
		 delete();
	 }
	 
	 private void save()
	 {
		BufferedImage src;
		try {
				src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
		} catch (IOException e) {
				logger.error("read image fail !");
				return;
		}
		for(CheckImageType type:CheckImageType.values()){
			BufferedImage r = resize(src,Mode.AUTOMATIC,type.getWidth(),type.getHeigth());
				 File f = new File(IMAGE_PATH_PREFIX+getImageName(getName(),type));
				 if (!f.getParentFile().exists())
				     f.getParentFile().mkdirs();
					try {
						 if (!f.exists()){
							 f.createNewFile();
						 }
						 ImageIO.write(r, IMAGE_TYPE, f);
					} catch (IOException e) {
						logger.error("write image fail !");
						e.printStackTrace();
					}
			 }

	 }
	 private void delete()
	 {
		for(CheckImageType type:CheckImageType.values()){
				try{
					FileUtils.forceDelete(new File(getImageDir(getName(),type)));
				}catch (FileNotFoundException e){
					continue;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	 }
	 private String getImageDir(String name,CheckImageType type)
	 {
		 return IMAGE_PATH_PREFIX+getImageName(name,type);
	 }


	 private String getImageName(String name,CheckImageType type)
	 {
		 return name+"-"+type.getText()+IMAGE_SUFFIX;
	 }
	 

}
