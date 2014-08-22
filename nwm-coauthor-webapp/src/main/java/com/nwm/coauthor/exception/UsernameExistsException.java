package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class UsernameExistsException extends BaseException {
    private static final long serialVersionUID = 1L;
    
    public UsernameExistsException() {
        setId(ExceptionMapper.USERNAME_EXISTS_EXCEPTION);
        setDescription("The username already exists.");
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
