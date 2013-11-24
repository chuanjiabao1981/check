package com.check.v3.rest.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.check.v3.domain.Organization;
import com.check.v3.domain.User;
import com.check.v3.dto.OrganizationDTO;
import com.check.v3.service.UserService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Controller
public class OrganizationsRestController {
	@Resource
	UserService userService;
	
	@RequestMapping(value = "/api/v1/organizations", method = RequestMethod.GET)
	@ResponseBody
	public List<OrganizationDTO> index()
	{
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		User userWithOrganizations = userService.findByIdWithOrganizations(user.getId());
	    List<Organization> s = userWithOrganizations.getOrganizations();
	    return Lists.transform(s, new Function<Organization, OrganizationDTO>(){
	        public OrganizationDTO apply(Organization organization) {
	        	return new OrganizationDTO(organization);
	        }
	    });
	}
}
