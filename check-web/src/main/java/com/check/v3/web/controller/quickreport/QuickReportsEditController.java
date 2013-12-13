package com.check.v3.web.controller.quickreport;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.check.v3.ApplicationConstant;
import com.check.v3.domain.CheckImage;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportImage;
import com.check.v3.security.SecurityConstant;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.service.exception.ImageTypeWrongException;
import com.check.v3.service.tools.FileAlignmentMedia;
import com.check.v3.service.tools.FileAlignmentMedia.FileAlignmentMediaResult;

@Controller
public class QuickReportsEditController extends QuickReportsController{
	
	@RequestMapping(value="/quick_reports/{quick_report_id}/edit",method=RequestMethod.GET)
	public String edit(@InstanceId @PathVariable("quick_report_id") Long id,
					    Model model,
					    HttpServletRequest httpServletRequest
					    )
	{
		QuickReport quickReport = (QuickReport) httpServletRequest.getAttribute(SecurityConstant.CurrentInstance);
		if (quickReport != null && quickReport.getOrganization() != null && quickReport.getOrganization().getId()!=null)
			 model.addAttribute("responsiblePersons", getResponsiblePersons(quickReport.getOrganization().getId()));
		model.addAttribute("quick_report", quickReport);
		return VIEW_EDIT;
	}
	
	@RequestMapping(value="/quick_reports/{quick_report_id}",method=RequestMethod.POST)
	public String update(@InstanceId @PathVariable("quick_report_id") Long id,
						 @ModelAttribute("quick_report") @Valid QuickReport quickReport,
						 BindingResult bindingResult,
						 Model model,
						 @RequestParam(value="image_files[]",required=false) List<MultipartFile> imageFiles
						)
	{
		if (bindingResult.hasErrors()){
			 model.addAttribute("responsiblePersons", getResponsiblePersons(quickReport.getOrganization().getId()));
			return VIEW_EDIT;
		}
		try{
			quickReportService.save(quickReport, imageFiles);
		}catch( ImageTypeWrongException e){
			bindingResult.rejectValue("images["+e.getIdx()+"].name", "validation.checkImage.type.message");
			return VIEW_EDIT;
		}

		return "redirect:/quick_reports/"+quickReport.getId();
	}
	@RequestMapping(value="/quick_reports/{quick_report_id}",method=RequestMethod.DELETE)
	public String destroy(@InstanceId @PathVariable("quick_report_id") Long id,
						  @ModelAttribute("quick_report") @Valid QuickReport quickReport)
	{
		this.quickReportService.deleteById(id);
		this.checkImageFileService.delete(quickReport.getImages().iterator());
		return "redirect:/organizations/"+quickReport.getOrganization().getId()+"/quick_reports";
	}
	
	@ModelAttribute("quick_report")
	public QuickReport populateQuickReport(@PathVariable("quick_report_id") Long id)
	{
		QuickReport q =  this.quickReportService.findByIdWithMedia(id);
		int num		  =  ApplicationConstant.CHECK_IMAGES_NUM - q.getImages().size();
		for(int i =0;i< num;i++){
			QuickReportImage image = new QuickReportImage();
			image.setDepartment(q.getDepartment());
			image.setSubmitter(q.getSubmitter());
			q.addImage(image);
		}
		return q;
	}

}
