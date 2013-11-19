package com.check.v3.web.controller;

import java.beans.PropertyEditorSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.check.v3.domain.Department;
import com.check.v3.domain.Role;
import com.check.v3.domain.User;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.DepartmentService;
import com.check.v3.service.UserService;

@Controller
public class DepartmentAdminsController {
	
	
	private static final Logger logger 			= LoggerFactory.getLogger(DepartmentAdminsController.class);

	private static final String VIEW_NEW 	   	= "department_admins/new";
	private static final String VIEW_LIST		= "department_admins/index";
	private static final String VIEW_EDIT		= "department_admins/edit";
	
	@Resource
	DepartmentService departmentService;
	@Autowired
	UserService userService;

	@RequestMapping(value="/departments/{department_id}/department_admins",method=RequestMethod.GET)
	public String index(@InstanceId @PathVariable("department_id") Long departmentId,Model model)
	{
		//这个不会返回null，出错都是异常
		Department department = departmentService.findByIdWithUsers(departmentId);
		model.addAttribute("department_admins", department.getDepartmentAdmins());
		model.addAttribute("department_id",departmentId);
		return VIEW_LIST;
	}
	
	@RequestMapping(value="/departments/{department_id}/department_admins/new",method=RequestMethod.GET)
	public String newForm(@InstanceId @PathVariable("department_id") Long departmentId,Model model)
	{
		User departmentAdmin = new User();
		departmentAdmin.setRole(Role.DEPARTMENT_ADMIN);
		model.addAttribute("department_id", departmentId);
		model.addAttribute("department_admin", departmentAdmin);
		return VIEW_NEW;
	}
	@RequestMapping(value="/departments/{department_id}/department_admins",method=RequestMethod.POST)
	public String create(@InstanceId @PathVariable("department_id") Long departmentId,
						 @ModelAttribute("department_admin") @Valid User departmentAdmin,
							BindingResult bindingResult, 

						  Model model)
	{
		model.addAttribute("department_id", departmentId);
		if (bindingResult.hasErrors()){
			//@ModelAttribute 这个已经添加到model中了，所以不需要在手动添加了
			return VIEW_NEW;
		}
		departmentAdmin.setPassword_cryp(SecurityTools.getEncryptPassword(departmentAdmin.getPassword()));
		try{
			userService.save(departmentAdmin);
		}catch(DataIntegrityViolationException e){
			bindingResult.rejectValue("account","user_account_duplicate");
			return VIEW_NEW;
		}
		return "redirect:/departments/"+departmentId+"/department_admins";
	}
	@RequestMapping(value="/department_admins/{id}",method=RequestMethod.POST)
	public String update(@ModelAttribute("department_admin") @Valid User departmentAdmin,
						 BindingResult bindingResult,
						  Model model) 
	{
		model.addAttribute("department_id", departmentAdmin.getDepartment().getId());

		if (bindingResult.hasErrors()){
			return VIEW_EDIT;
		}
		departmentAdmin.setPassword_cryp(SecurityTools.getEncryptPassword(departmentAdmin.getPassword()));
		try{
			userService.save(departmentAdmin);
		}catch(DataIntegrityViolationException e){
			bindingResult.rejectValue("account","user_account_duplicate");
			return VIEW_EDIT;
		}
		return "redirect:/departments" ;
	}
	@RequestMapping(value="/department_admins/{id}",method=RequestMethod.GET)
	public String edit(@InstanceId @PathVariable("id") Long id,Model model)
	{
		User department_admin = userService.findById(id);
		model.addAttribute("department_admin", department_admin);
		model.addAttribute("department_id", department_admin.getDepartment().getId());
		return VIEW_EDIT;
	}
	
	@RequestMapping(value="/department_admins/{id}",method=RequestMethod.DELETE)
	public String destroy(@InstanceId @PathVariable("id") Long id)
	{
		User departmentAdmin = userService.findById(id);
		userService.delete(departmentAdmin);
		return "redirect:/departments/"+departmentAdmin.getDepartment().getId()+"/department_admins";
	}
	
	
	@ModelAttribute("department_admin")
	public User populateUser()
	{
		User u = new User();
		u.setRole(Role.DEPARTMENT_ADMIN);
		return u;
	}
	
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, WebDataBinder binder) 
	{
		
	   binder.registerCustomEditor(Department.class,  new DepartmentEditor(this.departmentService));
	}
	public class DepartmentEditor extends PropertyEditorSupport 
	{
		private DepartmentService departmentService;
		public DepartmentEditor(DepartmentService departmentService)
		{
			this.departmentService = departmentService;
		}
		public void setAsText(String text)
		{
			
			setValue(this.departmentService.findById(Long.valueOf(text)));

		}
	}

}
