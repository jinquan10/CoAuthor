package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class UserIsNotLeaderException extends BaseException{
	public UserIsNotLeaderException(){
		setId(ExceptionMapper.USER_IS_NOT_LEADER_EXCEPTION);
		setDescription("You are not the leader, so you cannot publish this story.");
		setHttpStatus(HttpStatus.FORBIDDEN);
	}
}
