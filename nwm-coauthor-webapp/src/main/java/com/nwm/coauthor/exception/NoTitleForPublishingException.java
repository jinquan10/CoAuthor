package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class NoTitleForPublishingException extends BaseException{
	public NoTitleForPublishingException(){
		setId(ExceptionMapper.NO_TITLE_FOR_PUBLISHING_EXCEPTION);
		setDescription("Story needs a title before it can be published.");
		setHttpStatus(HttpStatus.BAD_REQUEST);
	}
}
