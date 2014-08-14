package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class AlreadyAMemberException extends BaseException{
    public AlreadyAMemberException(){
        setId(ExceptionMapper.ALREADY_A_MEMBER_EXCEPTION);
        setDescription("This person is already a member.");
        setHttpStatus(HttpStatus.FORBIDDEN);
    }
}
