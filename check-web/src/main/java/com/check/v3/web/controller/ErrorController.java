package com.check.v3.web.controller;

import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
	public String UnauthorizeExceptionPage()
	{
		return "errors/unauthorized";
	}
}
