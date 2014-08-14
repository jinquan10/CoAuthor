package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class AlreadyPublishedException extends BaseException{
    public AlreadyPublishedException(){
        setId(ExceptionMapper.ALREADY_PUBLISHED_EXCEPTION);
        setDescription("The story is already published, so you cannot perform this operation.");
        setHttpStatus(HttpStatus.FORBIDDEN);
    }
}
