package com.check.v3.rest.controller;

import java.util.List;
import java.util.Locale;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.check.v3.ApplicationConstant;
import com.check.v3.dto.ErrorsDTO;

@ControllerAdvice
public class RestErrorHandler {
	
//	private static final Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);
	
	@Autowired
    private MessageSource messageSource;


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDTO processValidationError(MethodArgumentNotValidException ex) 
    {
        BindingResult result			 = ex.getBindingResult();
        List<FieldError> fieldErrors  	 = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }
    @ExceptionHandler(org.springframework.validation.BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDTO processValidationError(BindException ex) 
    {
        BindingResult result			 = ex.getBindingResult();
        List<FieldError> fieldErrors  	 = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }
    
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDTO processAuthenticationError(AuthenticationException ex,Locale locale)
    {
        ErrorsDTO dto = new ErrorsDTO();
        dto.addFieldError(ApplicationConstant.AuthenticationField, messageSource.getMessage(ApplicationConstant.AuthenticationFailCode, null,locale));
        return dto;
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDTO processAuthenticationError(IncorrectCredentialsException ex,Locale locale)
    {
        ErrorsDTO dto = new ErrorsDTO();
        dto.addFieldError(ApplicationConstant.AuthenticationField, messageSource.getMessage(ApplicationConstant.AuthenticationFailCode, null,locale));
        return dto;
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDTO proccessHttpMessageNotReadableError(HttpMessageNotReadableException ex,Locale locale)
    {
        ErrorsDTO dto = new ErrorsDTO();
        dto.addFieldError(ApplicationConstant.Base, messageSource.getMessage(ApplicationConstant.RequestFormatErrorCode, null,locale));
        return dto;
    }
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDTO processAuthorizationError(UnauthenticatedException ex,Locale locale)
    {
        ErrorsDTO dto = new ErrorsDTO();
        dto.addFieldError(ApplicationConstant.AuthenticationField, messageSource.getMessage(ApplicationConstant.AuthenticationFailCode, null,locale));
        return dto;
    }
    private ErrorsDTO processFieldErrors(List<FieldError> fieldErrors) {
        ErrorsDTO dto = new ErrorsDTO();

        for (FieldError fieldError: fieldErrors) {
//            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return dto;
    }
    
    


}
