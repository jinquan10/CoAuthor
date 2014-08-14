package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class CannotGetEntriesException extends BaseException {
	public CannotGetEntriesException(){
		setId(ExceptionMapper.CANNOT_GET_ENTRIES_EXCEPTION);
		setDescription("You cannot get the entries for this story.");
		setHttpStatus(HttpStatus.FORBIDDEN);
	}
}
