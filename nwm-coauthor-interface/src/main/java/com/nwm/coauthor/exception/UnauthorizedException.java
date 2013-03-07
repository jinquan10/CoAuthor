package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class UnauthorizedException extends BaseException{
    public UnauthorizedException(){
        setId(ExceptionMapper.UNAUTHORIZED_EXCEPTION);
        setDescription("You are unauthorized for this operation.");
        setHttpStatus(HttpStatus.UNAUTHORIZED);
    }
}
