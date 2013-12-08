package com.check.v3.domain;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


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
	
	@ManyToOne
    @JoinColumn(name="submitter_id")
	private User submitter;
	
//	@Transient
//	private MultipartFile file ;
	
//	public MultipartFile getFile() {
//		return file;
//	}
//	public void setFile(MultipartFile file) {
//		this.file = file;
//	}
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
//	@PrePersist
//	public  void onPrePersist() throws IOException
//	{
//		System.err.println("lllllllll");
//		System.err.println(name);
//		System.err.println(file);
//		if (file != null)
//			System.err.println(file.isEmpty());
//		if (name != null && file != null && !file.isEmpty()){
//			 BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
//			 for(CheckImageType type:CheckImageType.values()){
//				 BufferedImage r = resize(src,Mode.AUTOMATIC,type.getWidth(),type.getHeigth());
//				 File f = new File(IMAGE_PATH_PREFIX+getImageName(type));
//				 if (!f.getParentFile().exists())
//				     f.getParentFile().mkdirs();
//				 if (!f.exists())
//				     f.createNewFile();
//				 ImageIO.write(r, IMAGE_TYPE, f);
//			 }
//		}
//	}
//	@PostUpdate
//	public  void onPostUpdate() throws IOException
//	{
//		System.err.println("kkkkkkkkkkkkk");
//		if (name != null && file != null && !file.isEmpty()){
//			 BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
//			 for(CheckImageType type:CheckImageType.values()){
//				 BufferedImage r = resize(src,Mode.AUTOMATIC,type.getWidth(),type.getHeigth());
//				 File f = new File(IMAGE_PATH_PREFIX+getImageName(type));
//				 if (!f.getParentFile().exists())
//				     f.getParentFile().mkdirs();
//				 if (!f.exists())
//				     f.createNewFile();
//				 ImageIO.write(r, IMAGE_TYPE, f);
//			 }
//		}
//	}
//
//	@PostRemove
//	public void onPostRemove() throws IOException
//	{
//		 for(CheckImageType type:CheckImageType.values()){
//			 try{
//			 	FileUtils.forceDelete(new File(getImageDir(type)));
//			 }catch (FileNotFoundException e){
//				 continue;
//			 }
//		 }
//	}
	
}
