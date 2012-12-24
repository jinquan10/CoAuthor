package com.nwm.coauthor.exception;

import java.util.Map;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class CreateStoryBadRequestException extends BaseException {
	private static final long serialVersionUID = 1L;

	public CreateStoryBadRequestException(){
		setId(ExceptionMapper.CREATE_STORY_BAD_REQUEST_EXCEPTION);
		setDescription("You have errors in your story creation.");
		setStatusCode(400);
	}
	
	public CreateStoryBadRequestException(Map<String, String> batchErrors){
		setId(ExceptionMapper.CREATE_STORY_BAD_REQUEST_EXCEPTION);
		setDescription("You have errors in your story creation.");
		setBatchErrors(batchErrors);
		setStatusCode(400);
	}
	
	public CreateStoryBadRequestException(BaseException e){
		setId(e.getId());
		setDescription(e.getDescription());
		setStatusCode(e.getStatusCode());
		setBatchErrors(e.getBatchErrors());
	}
}
