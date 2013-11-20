package com.check.v3.web.controller;


import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.check.v3.domain.Department;
import com.check.v3.domain.User;
import com.check.v3.security.annotation.InstanceId;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.UserService;
import com.check.v3.service.exception.UserAccountDuplicateException;
import com.check.v3.web.form.Message;


@Controller
public class UsersController {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	public final static String VIEW_LIST = "users/index";


	@Resource
	UserService userService;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String index(Model model)
	{
		Department 	  department 				= SecurityTools.getCurrentDepartment();
		List<User>	  users 					= userService.findAllByDepartmentId(department.getId());
		model.addAttribute("users",users);
		return VIEW_LIST;
	}
	
	@RequestMapping(value="/users/new",method=RequestMethod.GET)
	public String newUser(Model model)
	{
		model.addAttribute("user", new User());
		return "users/new";
	}
	@RequestMapping(value="/users",method = RequestMethod.POST)
	public String create(
			@ModelAttribute("user") @Valid User user,
			BindingResult bindingResult, 
			Model model,
			RedirectAttributes redirectAttributes,
			Locale locale) {
		System.out.println("Creating user: " + user.getName().toString());
		if (bindingResult.hasErrors()) {
			model.addAttribute("message",
					new Message("error", messageSource.getMessage("user_create_fail", new Object[] {}, locale)));
			model.addAttribute("user", user);
			return "users/new";
		}
		user.setPassword_cryp(SecurityTools.getEncryptPassword(user.getPassword()));
		try {
			userService.save(user);
		} catch (UserAccountDuplicateException e) {
			model.addAttribute("message",
					new Message("error", messageSource.getMessage("user_create_fail", new Object[] {}, locale)));
			bindingResult.rejectValue("account","user_account_duplicate",
					messageSource.getMessage("user_account_duplicate", new Object[] {}, locale));

			model.addAttribute("user", user);
			return "users/new";
		}
		//注意这句只能在redirect的时候使用
		model.asMap().clear();

		redirectAttributes.addFlashAttribute("message",
				new Message("success", messageSource.getMessage("user_create_success", new Object[] {}, locale)));
		return "redirect:/users/" + user.getId();
	}
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        User user = userService.findById(id);
		uiModel.addAttribute("user", user);
        return "users/show";
    }
	@RequestMapping(value = "/users/{id}/edit", method = RequestMethod.GET)
    public String edit(@InstanceId @PathVariable("id") Long id, Model uiModel,HttpServletRequest httpServletRequest) {
        uiModel.addAttribute("user", userService.findById(id));
        return "users/edit";
	}	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public String update(@InstanceId @PathVariable("id") Long id,
    		@ModelAttribute @Valid User user,
			BindingResult bindingResult, 
			Model model,
			RedirectAttributes redirectAttributes,
			Locale locale
    		) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message",
					new Message("error", messageSource.getMessage("user_update_fail", new Object[] {}, locale)));
			model.addAttribute("user", user);
			return "users/edit";
		}
        try {
			userService.save(user);
		} catch (UserAccountDuplicateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        redirectAttributes.addFlashAttribute("message",
				new Message("success", messageSource.getMessage("user_update_success", new Object[] {}, locale)));
        return "redirect:/users/" + + user.getId();
    }	
	
}
