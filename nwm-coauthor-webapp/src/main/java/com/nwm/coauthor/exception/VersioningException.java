package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class VersioningException extends BaseException {
    public VersioningException(){
        setId(ExceptionMapper.VERSIONING_EXCEPTION);
        setDescription("The resource has changed state since you last updated.");
        setHttpStatus(HttpStatus.PRECONDITION_FAILED);
    }
}
