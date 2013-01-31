package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class AlreadyLikedException extends BaseException{
	private static final long serialVersionUID = 1L;

	public AlreadyLikedException(){
		setId(ExceptionMapper.ALREADY_LIKED_EXCEPTION);
		setDescription("You have already liked this story.");
		setStatusCode(HttpStatus.FORBIDDEN.value());
	}
}
