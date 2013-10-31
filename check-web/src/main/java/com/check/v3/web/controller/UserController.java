package com.check.v3.web.controller;


import java.util.Locale;

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

import com.check.v3.domain.User;
import com.check.v3.security.util.SecurityTools;
import com.check.v3.service.UserService;
import com.check.v3.service.exception.UserAccountDuplicateException;
import com.check.v3.web.form.Message;


@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);


	@Autowired
	UserService userService;
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value="/users/new",method=RequestMethod.GET)
	public String newUser(Model model)
	{
		model.addAttribute("user", new User());
		return "users/new";
	}
	@RequestMapping(value="/users",method = RequestMethod.POST)
	public String create(
			@Valid User user,
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
		model.asMap().clear();
		user.setPassword_cryp(SecurityTools.getEncryptPassword(user.getPassword()));
		try {
			userService.save(user);
		} catch (UserAccountDuplicateException e) {
			model.addAttribute("message",
					new Message("error", messageSource.getMessage("user_create_fail", new Object[] {}, locale)));
			bindingResult.rejectValue("account","user_account_duplicate",
					messageSource.getMessage("user_account_duplicate", new Object[] {}, locale));

			model.addAllAttributes(bindingResult.getModel());
			model.addAttribute("user", user);
			return "users/new";
		}
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
	@RequestMapping(value = "/users/{id}", params = "edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("user", userService.findById(id));
        return "users/edit";
	}	
	@RequestMapping(value = "/users/{id}", params = "edit", method = RequestMethod.POST)
    public String update(@ModelAttribute @Valid User user,
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
