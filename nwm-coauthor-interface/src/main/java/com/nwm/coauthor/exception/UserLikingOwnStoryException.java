package com.nwm.coauthor.exception;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class UserLikingOwnStoryException extends BaseException{
	public UserLikingOwnStoryException(){
		setId(ExceptionMapper.USER_LIKING_OWN_STORY_EXCEPTION);
		setDescription("You cannot like a story that you belong to!");
		setStatusCode(400);
	}
}
