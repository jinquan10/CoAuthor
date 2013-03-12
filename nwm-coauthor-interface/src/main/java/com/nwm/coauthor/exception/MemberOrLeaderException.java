package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class MemberOrLeaderException extends BaseException{
	public MemberOrLeaderException(){
		setId(ExceptionMapper.MEMBER_OR_LEADER_EXCEPTION);
		setDescription("You cannot complete this operation, because you are the member or leader of this story.");
		setHttpStatus(HttpStatus.FORBIDDEN);
	}
}
