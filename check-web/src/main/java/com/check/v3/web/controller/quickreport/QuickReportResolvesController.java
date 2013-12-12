package com.check.v3.web.controller.quickreport;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
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
import com.check.v3.domain.Department;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.domain.ResolveImage;
import com.check.v3.domain.User;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.CheckImageFileService;
import com.check.v3.service.QuickReportResolveService;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.exception.ImageTypeWrongException;
import com.check.v3.service.tools.FileAlignmentMedia;
import com.check.v3.service.tools.FileAlignmentMedia.FileAlignmentMediaResult;

@Controller
public class QuickReportResolvesController {
	
	
	@Resource
	QuickReportService quickReportService;
	@Resource
	CheckImageFileService checkImageFileService;
	@Resource
	QuickReportResolveService quickReportResolveService;

	private static final String VIEW_NEW = "quick_report_resolves/new";
	@RequestMapping(value="/quick_report/{quick_report_id}/quick_report_resolves/new",method=RequestMethod.GET)
	public String newForm()
	{
		return VIEW_NEW;
	}
	
	@RequestMapping(value="/quick_report/{quick_report_id}/quick_report_resolves",method=RequestMethod.POST)
	public String create(@InstanceId @PathVariable("quick_report_id") Long quickReportId,
			 			@ModelAttribute("quick_report_resolve") @Valid QuickReportResolve quickReportResolve,
			 			BindingResult bindingResult, 
						HttpServletRequest httpServletRequest,
						Model model,
						@RequestParam(value="image_files[]") List<MultipartFile> imageFiles
						)
	{

		if (bindingResult.hasErrors()){
			return VIEW_NEW;
		}
		FileAlignmentMediaResult result = null;
		try {
			result = FileAlignmentMedia.getResult(imageFiles, quickReportResolve.getImages().iterator());
		} catch (ImageTypeWrongException e) {
			bindingResult.rejectValue("images["+e.getIdx()+"].name", "validation.checkImage.type.message");
			return VIEW_NEW;
		}
		for(CheckImage checkImage : result.getEmptyCheckImages()){
			quickReportResolve.removeImage((ResolveImage) checkImage);
		}
		quickReportResolveService.save(quickReportResolve);
		checkImageFileService.save(imageFiles, result.getNeededStoreCheckImages());
		return "redirect:/quick_reports/"+quickReportResolve.getQuickReport().getId();

	}
	@ModelAttribute("quick_report")
	public QuickReportResolve populateResolve(@PathVariable("quick_report_id") Long quickReportId)
	{
		QuickReportResolve r 				= new QuickReportResolve();
		Department	department				= SecurityTools.getCurrentDepartment();
		User user 							= (User) SecurityUtils.getSubject().getPrincipal();

		r.setDepartment(department);
		r.setSubmitter(user);
		for(int i =0;i<ApplicationConstant.CHECK_IMAGES_NUM;i++){
			ResolveImage image = new ResolveImage();
			image.setDepartment(department);
			image.setSubmitter(user);
			r.addImage(image);
		}
		QuickReport q = quickReportService.findById(quickReportId);
		q.addResolve(r);
		return r;
	}
}
