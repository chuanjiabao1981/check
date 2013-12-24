package com.check.v3.rest.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.check.v3.domain.QuickReport;
import com.check.v3.domain.QuickReportResolve;
import com.check.v3.domain.User;
import com.check.v3.dto.IdDTO;
import com.check.v3.dto.QuickReportResolveDTO;
import com.check.v3.dto.QuickReportResolveRequestDTO;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.service.QuickReportResolveService;
import com.check.v3.service.QuickReportService;

@Controller
public class QuickReportResolvesRestController {
	@Resource
	QuickReportResolveService quickReportResolveService;
	@Resource
	QuickReportService	quickReportService;
	
	@RequestMapping(value="/api/v1/quick_reports/{quick_report_id}/quick_report_resolves",method=RequestMethod.POST)
	@ResponseBody
	public QuickReportResolveDTO create(@InstanceId @PathVariable("quick_report_id") Long quickReportId,
										@Valid @RequestPart("quickReportResolveJson") QuickReportResolveRequestDTO quickReportResolveRequestDTO,
										HttpServletRequest httpServletRequest,
										@RequestPart("quickReportResolveImages") List<MultipartFile> newImageFiles)
	{
		QuickReportResolve quickReportResolve = new QuickReportResolve();
		QuickReport quickReport 			  = quickReportService.findByIdWithMediaAndResolve(quickReportId);
		User user 							  = (User) SecurityUtils.getSubject().getPrincipal();
		quickReportResolve.setSubmitter(user);
		quickReportResolve.setDepartment(user.getDepartment());
		quickReportResolve.setQuickReport(quickReport);
		BeanUtils.copyProperties(quickReportResolveRequestDTO, quickReportResolve);
		initCheckImage(quickReportResolve,newImageFiles);
		quickReportResolve 					  =  quickReportResolveService.save(quickReportResolve, newImageFiles, null);
		return new QuickReportResolveDTO(quickReportResolve);
	}
	@RequestMapping(value="/api/v1/quick_report_resolves/{quick_report_resolve_id}",method=RequestMethod.POST)
	@ResponseBody
	public QuickReportResolveDTO update(@InstanceId @PathVariable("quick_report_resolve_id") Long quickReportResolveId,
										@Valid @RequestPart("quickReportResolveJson") QuickReportResolveRequestDTO quickReportResolveRequestDTO,
										HttpServletRequest httpServletRequest,
										@RequestPart("quickReportResolveImages") List<MultipartFile> newImageFiles)
	{
		QuickReportResolve quickReportResolve = quickReportResolveService.findByIdWithMedia(quickReportResolveId);
		quickReportResolveRequestDTO.setId(quickReportResolve.getId());
		BeanUtils.copyProperties(quickReportResolveRequestDTO, quickReportResolve);
		initCheckImage(quickReportResolve,newImageFiles);
		quickReportResolve = quickReportResolveService.save(quickReportResolve, newImageFiles, quickReportResolveRequestDTO.getNeededdeleteImagesId());
		return new QuickReportResolveDTO(quickReportResolve);
	}
	@RequestMapping(value="/api/v1/quick_report_resolves/{quick_report_resolve_id}/destroy",method=RequestMethod.POST)
	@ResponseBody
	public IdDTO destroy(@InstanceId @PathVariable("quick_report_resolve_id") Long quickReportResolveId,
						HttpServletRequest httpServletRequest
					   )
	{
		QuickReportResolve quickReportResolve = quickReportResolveService.findByIdWithMedia(quickReportResolveId);
		quickReportResolveService.delete(quickReportResolve);
		return new IdDTO(quickReportResolveId);
	}
	private void initCheckImage(QuickReportResolve quickReportResolve,List<MultipartFile> newImageFiles)
	{
		for(int i =0 ;i < newImageFiles.size();i++){
			quickReportResolve.buildCheckImage();
		}

	}
}
