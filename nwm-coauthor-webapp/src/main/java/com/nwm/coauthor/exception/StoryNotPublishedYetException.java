package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class StoryNotPublishedYetException extends BaseException{
	public StoryNotPublishedYetException(){
		setId(ExceptionMapper.STORY_NOT_PUBLISHED_YET_EXCEPTION);
		setDescription("You cannot complete this operation, because the story hasn't been published yet.");
		setHttpStatus(HttpStatus.FORBIDDEN);
	}
}
