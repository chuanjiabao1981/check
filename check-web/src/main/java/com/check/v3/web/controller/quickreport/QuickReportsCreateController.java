package com.check.v3.web.controller.quickreport;

import java.util.List;

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

import com.check.v3.ApplicationConstant;
import com.check.v3.domain.Department;
import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.User;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.exception.ImageTypeWrongException;

@Controller
public class QuickReportsCreateController extends QuickReportsController{
	
//	private static final Logger logger = LoggerFactory.getLogger(QuickReportsCreateController.class);

	@RequestMapping(value="/organizations/{organization_id}/quick_reports/new",method=RequestMethod.GET)
	public String newForm(@InstanceId @PathVariable("organization_id") Long organizationId,HttpServletRequest httpServletRequest,Model model)
	{
		return VIEW_NEW;
	}
	@RequestMapping(value="/organizations/{organization_id}/quick_reports",method=RequestMethod.POST)
	public String create(@InstanceId @PathVariable("organization_id") Long organizationId,
			 			@ModelAttribute("quick_report") @Valid QuickReport quickReport,
			 			BindingResult bindingResult, 
						HttpServletRequest httpServletRequest,
						Model model
						)
	{
		if (bindingResult.hasErrors()){
			return VIEW_NEW;
		}
		try{
			quickReportService.save(quickReport);
		}catch( ImageTypeWrongException e){
			bindingResult.rejectValue("listImages["+e.getIdx()+"].file", "validation.checkImage.type.message");
			return VIEW_NEW;
		}
		return "redirect:/quick_reports/"+quickReport.getId();

	}
	@ModelAttribute("quick_report")
	public QuickReport populateQuickReport(@PathVariable("organization_id") Long organizationId)
	{
		QuickReport q 						= new QuickReport();
		Department	department				= SecurityTools.getCurrentDepartment();
		q.setDepartment(department);
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		q.setSubmitter(user);
		q.setOrganization(new Organization(organizationId));
		for(int i =0;i<ApplicationConstant.CHECK_IMAGES_NUM;i++){
			q.buildCheckImage();
		}
		return q;
	}
	@ModelAttribute("responsiblePersons")
	public List<User> populateResponsiblePersons(@PathVariable("organization_id") Long organizationId)
	{
		return this.getResponsiblePersons(organizationId);
	}

}
