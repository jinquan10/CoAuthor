package com.nwm.coauthor.exception;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class AddEntryException extends BaseException {
	private static final long serialVersionUID = 1L;

	public AddEntryException(){
		setId(ExceptionMapper.ADD_ENTRY_EXCEPTION);
		setDescription("Someone may have added an entry at the same time as you.  Please try refreshing the story and try again.");
		setStatusCode(400);
	}
}
