package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class AddEntryVersionException extends BaseException{
	public AddEntryVersionException(){
		setId(ExceptionMapper.ADD_ENTRY_VERSION_EXCEPTION);
		setDescription("You have attempted to add an entry to a stale story.  Refresh your story first.");
		setHttpStatus(HttpStatus.PRECONDITION_FAILED);
	}
}
