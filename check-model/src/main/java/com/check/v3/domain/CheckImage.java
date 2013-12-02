package com.check.v3.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;
import static org.imgscalr.Scalr.*;



@Entity
@Table(name = "check_images")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="check_image")
public class CheckImage extends BaseEntity{

	@Transient
	private String IMAGE_PATH_PREFIX = "";
	private enum CheckImageType
	{
		little_thumbnail(32,32,"little-thumbnail"),
		small_thumbnail(64,64,"small-thumbnail"),
		thumbnail(128,128,"thumbnail"),
		normal(256,256,"normal");
		int width,height;
		String text;
		private CheckImageType(int width,int height,String text)
		{
			this.width 		= width;
			this.height		= height;
			this.text		= text;
		}
		
		public int getWidth()
		{
			return width;
		}
		public int getHeigth()
		{
			return height;
		}
		public String getText()
		{
			return text;
		}
	};
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
		return this.name+"-"+type.getText();
	}
	@PrePersist
	public  void onPrePersist() throws IOException
	{
		if (name != null && file != null && !file.isEmpty()){
			 BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
//			 for(CheckImageType type:CheckImageType.values()){
//				 BufferedImage r = resize(src,Mode.AUTOMATIC,type.getWidth(),type.getHeigth());
//				 File f = new File(IMAGE_PATH_PREFIX+getImageName(type));
//				 if (!f.getParentFile().exists())
//				     f.getParentFile().mkdirs();
////				 if (!f.exists())
////				     f.createNewFile();
//				 System.err.println(f.getAbsolutePath());
////				 ImageIO.write(r, "jpg", f);
//			 }
		}
	}
	
}
