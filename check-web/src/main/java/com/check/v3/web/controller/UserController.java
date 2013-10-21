package com.check.v3.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
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


@Controller
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value="/users/new",method=RequestMethod.GET)
	public String newUser(Model model)
	{
		model.addAttribute("user", new User());
		return "users/new";
	}
	@RequestMapping(value="/users",method = RequestMethod.POST)
	public String create(
			@ModelAttribute User user,
			Model model) {
		System.out.println(user.getName().toString());
		user.setPassword_cryp(SecurityTools.getEncryptPassword(user.getPassword()));
		userService.save(user);
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
    public String update(@ModelAttribute User user,
			Model model
    		) {
        userService.save(user);
        return "redirect:/users/" + + user.getId();
    }	
}
