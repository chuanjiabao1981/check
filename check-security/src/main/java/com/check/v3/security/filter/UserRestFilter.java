package com.check.v3.security.filter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class UserRestFilter extends AccessControlFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		
		 HttpServletRequest httpReq = (HttpServletRequest) request;
//		 for(Cookie c:httpReq.getCookies())
//		 {
//			 System.err.println(c.getName());
//			 System.err.println(c.getValue());
//		 }
		 Subject subject = getSubject(request, response);
         return subject.getPrincipal() != null;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setContentType("application/json;charset=utf8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter p = httpResponse.getWriter();
        p.write("{\"errors\":[{\"field\":\"authentication\",\"message\":\"未登录用户\"}]}");
        p.flush();
		return false;
	}

}
