package com.nwm.coauthor.exception;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class SomethingWentWrongException extends BaseException{
	private static final long serialVersionUID = 1L;

	public SomethingWentWrongException(){
		setId(ExceptionMapper.SOMETHING_WENT_WRONG_EXCEPTION);
		setDescription("Sorry, something didn't go right.  We are on it!");
		setStatusCode(500);
	}
}
