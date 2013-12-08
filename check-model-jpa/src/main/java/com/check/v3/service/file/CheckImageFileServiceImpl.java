package com.check.v3.service.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.CheckImage;
import com.check.v3.domain.CheckImageType;
import com.check.v3.service.CheckImageFileService;

import static org.imgscalr.Scalr.*;

@Service("checkImageFileService")
@Repository
public class CheckImageFileServiceImpl implements CheckImageFileService{
	
	private static final Logger logger = LoggerFactory.getLogger(CheckImageFileServiceImpl.class);

	private static final String IMAGE_TYPE="jpg";
	private static final String IMAGE_SUFFIX="."+IMAGE_TYPE;
	private static final String IMAGE_PATH_PREFIX = "/var/check_v3_data/";
	
	@Override
	public void save(List<MultipartFile> imageFiles,List<CheckImage> checkImages) 
	{
		Iterator<CheckImage> i = checkImages.iterator();
		for(MultipartFile file : imageFiles){
			CheckImage image = (CheckImage) i.next();
			BufferedImage src;
			try {
				src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
			} catch (IOException e) {
				logger.error("read image fail !");
				e.printStackTrace();
				continue;
			}
			 for(CheckImageType type:CheckImageType.values()){
				 BufferedImage r = resize(src,Mode.AUTOMATIC,type.getWidth(),type.getHeigth());
				 File f = new File(IMAGE_PATH_PREFIX+getImageName(image.getName(),type));
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
			
	}

	@Override
	public void delete(Iterator checkImages) 
	{
		Iterator i = checkImages;
		while(i.hasNext()){
			CheckImage checkImage = (CheckImage) i.next();
			for(CheckImageType type:CheckImageType.values()){
				try{
					FileUtils.forceDelete(new File(getImageDir(checkImage.getName(),type)));
				}catch (FileNotFoundException e){
					continue;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getImageDir(String name,CheckImageType type)
	{
		return IMAGE_PATH_PREFIX+getImageName(name,type);
	}

	
	public String getImageName(String name,CheckImageType type)
	{
		return name+"-"+type.getText()+IMAGE_SUFFIX;
	}

}
