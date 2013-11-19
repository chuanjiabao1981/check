package com.check.v3.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.check.v3.domain.Department;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.service.DepartmentService;
import com.check.v3.web.form.Message;

@Controller
public class DepartmentsContoller {
	private static final Logger logger = LoggerFactory.getLogger(DepartmentsContoller.class);

	
	@Autowired
	DepartmentService departmentService;
	@Autowired
	MessageSource messageSource;

	
	@RequestMapping(value="/departments",method=RequestMethod.GET)
	public String index(Model uiModel)
	{
		List<Department> departments = departmentService.findAll();
		logger.trace("find "+departments.size() +" departments");
		uiModel.addAttribute("departments", departments);
		return "departments/index";
	}
	@RequestMapping(value="/departments/new",method=RequestMethod.GET)
	public String newForm(Model uiModel)
	{
		Department department = new Department();
		uiModel.addAttribute("department", department);
		return "departments/new";
	}
	@RequestMapping(value="/departments",method = RequestMethod.POST)
	public String create(@ModelAttribute("department") @Valid Department department,
			BindingResult bindingResult, 
			Model model,
			RedirectAttributes redirectAttributes,
			Locale locale)
	{
		String url = createOrupdate(department,bindingResult,model,locale);
		if (url != null){
			return url;
		}
		model.asMap().clear();
		return "redirect:/departments" ;
	}
	@RequestMapping(value = "/departments/{id}/edit", method = RequestMethod.GET)
	public String edit(@InstanceId @PathVariable("id") Long id,Model uiModel)
	{
		Department d = departmentService.findById(id);
        uiModel.addAttribute("department", d);
        return "departments/edit";
	}
	/*
	 * RequestMapping中的{id}会通过Department::setId方法设置到对象中
	 */
	@RequestMapping(value="/departments/{id}",method = RequestMethod.POST)
	public String update( @Valid Department department,
						  BindingResult bindingResult,
						  Model model,
						  Locale locale,
						  HttpServletRequest httpServletRequest)
	{
		String url = createOrupdate(department,bindingResult,model,locale);
		if (url != null){
			return url;
		}
		return "redirect:/departments" ;
	}
	@RequestMapping(value="/departments/{id}",method = RequestMethod.DELETE)
	public String destroy(@InstanceId @PathVariable("id") Long id)
	{
		departmentService.delete(id);
		return "redirect:/departments" ;
	}
	private String createOrupdate(Department department,BindingResult bindingResult,Model model,Locale locale)
	{
		String url = null;
		if (department.getId() != null){
			url = "departments/edit";
		}else{
			url = "departments/new";
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("message",new Message("error", messageSource.getMessage("department_create_fail", new Object[] {}, locale)));
			model.addAttribute("department", department);
			return url;
		}
		try{
			departmentService.save(department);
		}catch (DataIntegrityViolationException ex){
			model.addAttribute("message",new Message("error", messageSource.getMessage("department_create_fail", new Object[] {}, locale)));
			model.addAttribute("department", department);
			bindingResult.rejectValue("name","department_name_duplicate");
			return url;
		}

		return null;
	}
}
