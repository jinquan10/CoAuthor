package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class StoryNotFoundException extends BaseException {
	private static final long serialVersionUID = 1L;

	public StoryNotFoundException(){
		setId(ExceptionMapper.STORY_NOT_FOUND_EXCEPTION);
		setDescription("The private story cannot be found.");
		setHttpStatus(HttpStatus.NOT_FOUND);
	}
}
