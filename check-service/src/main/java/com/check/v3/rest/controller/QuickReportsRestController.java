package com.check.v3.rest.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.check.v3.ApplicationConstant;
import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.User;
import com.check.v3.dto.QuickReportDTO;
import com.check.v3.dto.QuickReportDetailDTO;
import com.check.v3.dto.QuickReportPageDTO;
import com.check.v3.dto.QuickReportRequestDTO;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.service.CheckImageFileService;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.UserService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Controller
public class QuickReportsRestController {
	@Resource
	private QuickReportService quickReportService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private UserService userService;
	@Value("#{ messageSource.getMessage('object_not_exsits',null,'zh_CN')}")
	private String MessageNotExsits;
	@Resource
	private CheckImageFileService checkImageFileService;
	
	@RequestMapping(value="/api/v1/organizations/{organization_id}/quick_reports",method=RequestMethod.POST)
	@ResponseBody
	public QuickReportDTO create(@Valid @RequestPart("quickReportJson") QuickReportRequestDTO quickReportRequestDTO,
						HttpServletRequest httpServletRequest,Model model,
						@RequestPart("quickReportImages") List<MultipartFile> newImageFiles)
	{
		QuickReport quickReport = new QuickReport();
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		quickReport.setSubmitter(user);
		quickReport.setDepartment(user.getDepartment());
		BeanUtils.copyProperties(quickReportRequestDTO, quickReport);
		initCheckImage(quickReport,newImageFiles);
		quickReport = quickReportService.save(quickReport, newImageFiles, null);
		return new QuickReportDTO(quickReport);
	}
	@RequestMapping(value="/api/v1/quick_reports/{id}",method=RequestMethod.POST)
	@ResponseBody
	public QuickReportDTO update(@InstanceId @PathVariable("id") Long id,
								 @Valid @RequestPart("quickReportJson")  QuickReportRequestDTO quickReportRequestDTO,
								 HttpServletRequest httpServletRequest,
								 @RequestPart("quickReportImages") List<MultipartFile> newImageFiles)
	{
		QuickReport quickReport = quickReportService.findByIdWithMediaAndResolve(id);
		quickReportRequestDTO.setId(quickReport.getId());
		BeanUtils.copyProperties(quickReportRequestDTO, quickReport);
		initCheckImage(quickReport,newImageFiles);
		quickReport = quickReportService.save(quickReport, newImageFiles, quickReportRequestDTO.getNeededdeleteImagesId());
		return new QuickReportDTO(quickReport);
	}
	@RequestMapping(value="/api/v1/organizations/{organization_id}/quick_reports",method=RequestMethod.GET)
	@ResponseBody
	public QuickReportPageDTO index(@InstanceId @PathVariable("organization_id") Long organizationId,
									HttpServletRequest httpServletRequest,
									@RequestParam(value = "page", required = false) Integer page)
	{
		int rows  			= 10;
		Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
		if (page == null)
			page = 1;
		PageRequest pageRequest = new PageRequest(page-1,rows,sort);
		Page<QuickReport> quickReports = quickReportService.findAllByOrganizationIdWithMedia(organizationId, pageRequest);

		QuickReportPageDTO	quickReportPageDTO	= new QuickReportPageDTO();
		quickReportPageDTO.setCurrentPage(quickReports.getNumber());
		quickReportPageDTO.setTotalPages(quickReports.getTotalPages());
		quickReportPageDTO.setTotalRecords(quickReports.getTotalElements());
		if (quickReports.getContent() != null && quickReports.getContent().size() != 0){
			quickReportPageDTO.setQuickReports(Lists.transform(quickReports.getContent(), 
					new Function<QuickReport,QuickReportDTO>(){
						public QuickReportDTO apply(QuickReport q)
						{
							return new QuickReportDTO(q);
						}
					}
			));
		}

		return quickReportPageDTO;

	}
	@RequestMapping(value="/api/v1/quick_reports/{quick_report_id}",method=RequestMethod.GET)
	@ResponseBody
	public QuickReportDetailDTO show(@InstanceId @PathVariable("quick_report_id") Long quickReportId,HttpServletRequest httpServletRequest)
	{
		QuickReport quickReport = quickReportService.findByIdWithMediaAndResolve(quickReportId);
		QuickReportDetailDTO quickReportDetailDTO = new QuickReportDetailDTO(quickReport);
		return quickReportDetailDTO;
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, WebDataBinder binder) 
	{
		binder.addValidators(new ReportRequestDTOValidator());
	}
	private void initCheckImage(QuickReport quickReport,List<MultipartFile> newImageFiles)
	{
		for(int i =0 ;i < newImageFiles.size();i++){
			quickReport.buildCheckImage();
		}
	}
	public class ReportRequestDTOValidator implements Validator {

		@Override
		public boolean supports(Class<?> clazz) {
			return QuickReportRequestDTO.class.isAssignableFrom(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			QuickReportRequestDTO quickReportRequestDTO = (QuickReportRequestDTO) target;
			if (quickReportRequestDTO.getOrganizationId()!=null){
				Organization s = organizationService.findById(quickReportRequestDTO.getOrganizationId() );
				if (s!=null){
					quickReportRequestDTO.setOrganization(s);
				}else{
					errors.rejectValue("organizationId", ApplicationConstant.ObjectNotExsits,MessageNotExsits);
				}
			}
			if (quickReportRequestDTO.getResponsiblePersonId() != null){
				User u = userService.findById(quickReportRequestDTO.getResponsiblePersonId());
				if (u != null){
					quickReportRequestDTO.setResponsiblePerson(u);
				}else{
					errors.rejectValue("responsiblePersonId", ApplicationConstant.ObjectNotExsits,MessageNotExsits);
				}
					
			}
		}
	}

}
