package com.check.v3.rest.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.check.v3.ApplicationConstant;
import com.check.v3.domain.Organization;
import com.check.v3.domain.QuickReport;
import com.check.v3.domain.User;
import com.check.v3.dto.IdDTO;
import com.check.v3.dto.QuickReportRequestDTO;
import com.check.v3.service.OrganizationService;
import com.check.v3.service.QuickReportService;
import com.check.v3.service.UserService;

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
	
	@RequestMapping(value="/api/v1/organizations/{organization_id}/quick_reports",method=RequestMethod.POST)
	@ResponseBody
	public IdDTO create(@Valid @RequestBody QuickReportRequestDTO quickReportRequestDTO,
						HttpServletRequest httpServletRequest,Model model)
	{
		QuickReport q = new QuickReport();
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		q.setSubmitter(user);
		q.setDepartment(user.getDepartment());
		BeanUtils.copyProperties(quickReportRequestDTO, q);

//		q.setDeadline(quickReportRequestDTO.getDeadLine());
//		q.setDescription(quickReportRequestDTO.getDescription());
//		q.setLevel(quickReportRequestDTO.getLevel());
//		if (quickReportRequestDTO.getResponsiblePerson() != null){
//			q.setResponsiblePerson(quickReportRequestDTO.getResponsiblePerson());
//		}
//		if (quickReportRequestDTO.getOrganizationId() != null){
//			q.setOrganization(quickReportRequestDTO.getOrganization());
//		}
		q = quickReportService.save(q);
		System.err.println(q.getOrganization().getName());
		return new IdDTO(q.getId());
	}
	@InitBinder
	protected void initBinder(HttpServletRequest request, WebDataBinder binder) 
	{
		binder.addValidators(new ReportRequestDTOValidator());
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
