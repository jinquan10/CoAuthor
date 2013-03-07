package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class AuthenticationUnauthorizedException extends BaseException{
	private static final long serialVersionUID = 1L;

	public AuthenticationUnauthorizedException(){
		setId(ExceptionMapper.AUTHENTICATION_UNAUTHORIZED_EXCEPTION);
		setDescription("Your authentication credentials are unauthorized.");
		setHttpStatus(HttpStatus.UNAUTHORIZED);
	}
}
