package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class NonMemberException extends BaseException{
	public NonMemberException(){
		setId(ExceptionMapper.NON_MEMBER_OR_LEADER_EXCEPTION);
		setDescription("You cannot complete this operation, because you are not the member or leader of this story.");
		setHttpStatus(HttpStatus.FORBIDDEN);
	}
}
