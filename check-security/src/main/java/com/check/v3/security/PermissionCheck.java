package com.check.v3.security;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthenticatedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.check.v3.security.annotation.InstanceId;
import com.check.v3.security.permission.role.PermissionManager;


@Aspect
public class PermissionCheck {
	private static final Logger logger = LoggerFactory.getLogger(PermissionCheck.class);
	@Autowired
	private PermissionManager permissionManager;
	
    @Around("execution(* com.check.v3.web.controller.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
  	 	System.err.println(permissionManager.isAllowed(getHttpServletRequest(joinPoint), getControllerName(joinPoint), getActionName(joinPoint), (Long) getInstanceId(joinPoint)));
        Object result = joinPoint.proceed();
        return result;
    }
    @Around("execution(* com.check.v3.rest.controller.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object permissionCheckRest(ProceedingJoinPoint joinPoint) throws Throwable 
    {
   	 	 boolean r  = permissionManager.isAllowed(getHttpServletRequest(joinPoint), getControllerName(joinPoint), getActionName(joinPoint), (Long) getInstanceId(joinPoint));
   	 	 if (!r){
   	 		 throw new UnauthenticatedException();
   	 	 }
    	 Object result = joinPoint.proceed();
         return result;

    }
    
    private Object getInstanceId(ProceedingJoinPoint joinPoint)
    {
    	int i = 0;
   	 	MethodSignature sig = (MethodSignature)joinPoint.getSignature();
   	 	Annotation parameterAnnotations[][] = sig.getMethod().getParameterAnnotations();
   	 	for(Annotation p[]:parameterAnnotations){
   	 		for(Annotation pp:p){
   	 			if (pp.annotationType().equals(InstanceId.class)){
   	 				return joinPoint.getArgs()[i];
   	 			}
   	 		}
   	 		i++;
   	 	}
   	 	return null;
    }
    private HttpServletRequest getHttpServletRequest(ProceedingJoinPoint joinPoint)
    {
    	int i = 0;
    	MethodSignature sig = (MethodSignature)joinPoint.getSignature();
    	for(@SuppressWarnings("rawtypes") Class s:sig.getParameterTypes()){
   	 		if (s.equals(HttpServletRequest.class)){
	 				return (HttpServletRequest) joinPoint.getArgs()[i];
   	 		}
   	 		i++;
   	 	}
    	return null;
    }
    private String getControllerName(ProceedingJoinPoint joinPoint)
    {
    	return joinPoint.getTarget().getClass().getSimpleName();
    }
    
    private String getActionName(ProceedingJoinPoint joinPoint)
    {
    	return joinPoint.getSignature().getName();
    }
}
