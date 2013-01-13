package com.nwm.coauthor.exception;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class GetPrivateStoryException extends BaseException {
	private static final long serialVersionUID = 1L;

	public GetPrivateStoryException(){
		setId(ExceptionMapper.GET_PRIVATE_STORY_EXCEPTION);
		setDescription("The private story cannot be found.");
		setStatusCode(404);
	}
}
