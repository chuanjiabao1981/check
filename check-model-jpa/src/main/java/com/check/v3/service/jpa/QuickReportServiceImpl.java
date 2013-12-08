package com.check.v3.service.jpa;



import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.repository.QuickReportRepository;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.exception.ImageTypeWrongException;

@Service("quickReportService")
@Repository
@Transactional
public class QuickReportServiceImpl implements QuickReportService{

	
	@PersistenceContext 
	private EntityManager em;

	@Resource
	private QuickReportRepository quickReportRepository;
	
	@Override
	@Transactional(readOnly=true)
	public QuickReport findById(Long id) {
		return quickReportRepository.findOne(id);
	}

	@Override
	@Transactional
	public QuickReport save(QuickReport quickReport) throws ImageTypeWrongException {
		
//		for(QuickReportImage image:quickReport.getImages()){
//			idx++;
//			System.err.println("name is :"+image.getName()+"|"+image.getId());
//			if ((image.getFile() == null || image.getFile().isEmpty()) && image.getName() == null){
//				needRemoved.add(image);
//				continue;
//			}
//			if ((image.getFile() !=null && !image.getFile().isEmpty()) &&
//				((image.getFile().getContentType() == null) || 
//				(!image.getFile().getContentType().equals("image/jpeg") &&
//				!image.getFile().getContentType().equals("image/png")))
//			   ){
//				
//
//				throw new ImageTypeWrongException(idx-1);
//				
//			}
//			if (!image.getFile().isEmpty() && image.getFile() !=null && image.getFile().getOriginalFilename() != null){
//				System.err.println("-----------------------------");
//				System.err.println(image.getName());
//				System.err.println(image.getFile());
//				System.err.println(image.getFile().getContentType());
////				System.err.println(image.getFile().getSize());
//				System.err.println("+++++++++++++++++++++++++++++");
//				image.setOriginalName(image.getFile().getOriginalFilename());
//			}
//		}
		
		//remove empty image
//		for(QuickReportImage i : needRemoved){
//			quickReport.removeImage(i);
//		}
//		for(QuickReportImage image:quickReport.getImages()){
//			if (image.getName() == null){
////				image.setName(BuildImageName(image));
//			}
//		}
		return quickReportRepository.save(quickReport);
//		for(QuickReportImage image:quickReport.getImages()){
//			System.err.println("before");
////			System.err.println(image.getFile());
//		}
//
//		if (quickReport.getId() == null ) {
//			em.persist(quickReport);
//			return quickReport;
//		} else {
//			 quickReport=  em.merge(quickReport);
//		}
//		for(QuickReportImage image:quickReport.getImages()){
//			System.err.println("after");
////			System.err.println(image.getFile());
//		}

//		return quickReport;

	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		quickReportRepository.delete(id);
	}

	@Override
	@Transactional
	public void delete(QuickReport quickReport) {
		quickReportRepository.delete(quickReport);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<QuickReport> findByOrganization(Organization organization,
			Pageable pageable) {
		return quickReportRepository.findByOrganization(organization, pageable);
	}

	
	@Override
	@Transactional(readOnly=true)
	public QuickReport findByIdWithMedia(Long id) {
		return this.quickReportRepository.findByIdWithMedia(id);
	}

}
