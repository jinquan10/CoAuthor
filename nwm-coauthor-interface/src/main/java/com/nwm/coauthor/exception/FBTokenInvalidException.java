package com.nwm.coauthor.exception;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class FBTokenInvalidException extends BaseException{
	private static final long serialVersionUID = 1L;

	public FBTokenInvalidException(){
		setId(ExceptionMapper.FB_TOKEN_INVALID);
		setDescription("Your Facebook token is invalid or expired.");
		setStatusCode(401);
	}
}
