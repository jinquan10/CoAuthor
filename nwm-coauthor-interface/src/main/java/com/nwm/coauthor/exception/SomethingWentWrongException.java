package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class SomethingWentWrongException extends BaseException{
	private static final long serialVersionUID = 1L;

	public SomethingWentWrongException(){
		setId(ExceptionMapper.SOMETHING_WENT_WRONG_EXCEPTION);
		setDescription("Sorry, something didn't go right.  We are on it!");
		setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public SomethingWentWrongException(String message){
        setId(ExceptionMapper.SOMETHING_WENT_WRONG_EXCEPTION);
        setDescription(message);
        setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
