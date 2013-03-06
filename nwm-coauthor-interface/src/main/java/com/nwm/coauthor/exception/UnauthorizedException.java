package com.nwm.coauthor.exception;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class UnauthorizedException extends BaseException{
    public UnauthorizedException(){
        setId(ExceptionMapper.UNAUTHORIZED_EXCEPTION);
        setDescription("You are unauthorized for this operation.");
        setStatusCode(401);
    }
}
