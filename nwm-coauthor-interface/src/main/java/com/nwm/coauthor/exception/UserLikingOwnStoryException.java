package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class UserLikingOwnStoryException extends BaseException{
	public UserLikingOwnStoryException(){
		setId(ExceptionMapper.USER_LIKING_OWN_STORY_EXCEPTION);
		setDescription("You cannot like a story that you belong to!");
		setHttpStatus(HttpStatus.BAD_REQUEST);
	}
}
