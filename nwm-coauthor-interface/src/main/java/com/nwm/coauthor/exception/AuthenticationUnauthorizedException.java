package com.nwm.coauthor.exception;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class AuthenticationUnauthorizedException extends BaseException{
	private static final long serialVersionUID = 1L;

	public AuthenticationUnauthorizedException(){
		setId(ExceptionMapper.AUTHENTICATION_UNAUTHORIZED_EXCEPTION);
		setDescription("Your authentication credentials are unauthorized.");
		setStatusCode(401);
	}
}
