package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class AlreadyRatedException extends BaseException{
    public AlreadyRatedException(){
        setId(ExceptionMapper.ALREADY_RATED_EXCEPTION);
        setDescription("You have already rated this story.");
        setHttpStatus(HttpStatus.FORBIDDEN);
    }
}
