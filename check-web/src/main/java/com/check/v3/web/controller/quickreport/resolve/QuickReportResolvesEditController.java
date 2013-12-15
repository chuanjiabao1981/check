package com.check.v3.web.controller.quickreport.resolve;

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
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.domain.ResolveImage;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.service.exception.ImageTypeWrongException;

@Controller
public class QuickReportResolvesEditController extends QuickReportResolvesController{

	
	@RequestMapping(value="/quick_report_resolves/{quick_report_resolve_id}/edit",method=RequestMethod.GET)
	public String edit(@InstanceId @PathVariable("quick_report_resolve_id") Long quickReportResolveId,
						  HttpServletRequest httpServletRequest)
	{
		return VIEW_EDIT;
	}
	@RequestMapping(value="/quick_report_resolves/{quick_report_resolve_id}",method=RequestMethod.POST)
	public String update(@InstanceId @PathVariable("quick_report_resolve_id") Long quickReportResolveId,
						 @ModelAttribute("quick_report_resolve") @Valid QuickReportResolve quickReportResolve,
						 BindingResult bindingResult,
						 Model model,
						 @RequestParam(value="image_files[]",required=false) List<MultipartFile> imageFiles)
	{
		
		if (bindingResult.hasErrors()){
			return VIEW_EDIT;
		}
		try{
			quickReportResolveService.save(quickReportResolve, imageFiles);
		}catch( ImageTypeWrongException e){
			bindingResult.rejectValue("images["+e.getIdx()+"].name", "validation.checkImage.type.message");
			return VIEW_EDIT;
		}

		 return "redirect:/quick_reports/"+quickReportResolve.getQuickReport().getId();

	}
	@RequestMapping(value="/quick_report_resolves/{quick_report_resolve_id}",method=RequestMethod.DELETE)
	public String destroy(@InstanceId @PathVariable("quick_report_resolve_id") Long quickReportResolveId,
						  @ModelAttribute("quick_report_resolve") @Valid QuickReportResolve quickReportResolve,
						  HttpServletRequest httpServletRequest
			 			 )
	{
		quickReportResolveService.delete(quickReportResolve);
		return "redirect:/quick_reports/"+quickReportResolve.getQuickReport().getId();
	}
	
	@ModelAttribute("quick_report_resolve")
	public QuickReportResolve populateResolve(@PathVariable("quick_report_resolve_id") Long quickReportResolveId)
	{
		
		QuickReportResolve r =  this.quickReportResolveService.findByIdWithMedia(quickReportResolveId);
		int num		 		 =  ApplicationConstant.CHECK_IMAGES_NUM - r.getImages().size();

		for(int i =0;i<num;i++){
			ResolveImage image = new ResolveImage();
			image.setDepartment(r.getDepartment());
			image.setSubmitter(r.getSubmitter());
			r.addImage(image);
		}
		return r;
	}



}
