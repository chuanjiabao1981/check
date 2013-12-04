package com.check.v3.domain;

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
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import static org.imgscalr.Scalr.*;



@Entity
@Table(name = "check_images")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="check_image")
public class CheckImage extends BaseEntity{

	@Transient
	private static final String IMAGE_TYPE="jpg";
	@Transient
	private static final String IMAGE_SUFFIX="."+IMAGE_TYPE;
	@Transient
	private static final String URL_PREFIX="/check-data/";
	@Transient
	private String IMAGE_PATH_PREFIX = "/var/check_v3_data/";
	
	
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
	public String getImageName(CheckImageType type)
	{
		return this.name+"-"+type.getText()+IMAGE_SUFFIX;
	}
	public String getImageUri(CheckImageType type)
	{
		return URL_PREFIX+getImageName(type);
	}
	public String getImageDir(CheckImageType type)
	{
		return IMAGE_PATH_PREFIX+getImageName(type);
	}
	@PostPersist
	public  void onPostPersist() throws IOException
	{
		if (name != null && file != null && !file.isEmpty()){
			 BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			 for(CheckImageType type:CheckImageType.values()){
				 BufferedImage r = resize(src,Mode.AUTOMATIC,type.getWidth(),type.getHeigth());
				 File f = new File(IMAGE_PATH_PREFIX+getImageName(type));
				 if (!f.getParentFile().exists())
				     f.getParentFile().mkdirs();
				 if (!f.exists())
				     f.createNewFile();
				 ImageIO.write(r, IMAGE_TYPE, f);
			 }
		}
	}
	@PostRemove
	public void onPostRemove() throws IOException
	{
		 for(CheckImageType type:CheckImageType.values()){
			 try{
			 	FileUtils.forceDelete(new File(getImageDir(type)));
			 }catch (FileNotFoundException e){
				 continue;
			 }
		 }
	}
	
}
