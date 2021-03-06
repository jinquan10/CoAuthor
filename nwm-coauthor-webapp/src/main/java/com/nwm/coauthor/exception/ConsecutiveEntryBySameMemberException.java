package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class ConsecutiveEntryBySameMemberException extends BaseException {
    public ConsecutiveEntryBySameMemberException(){
        setId(ExceptionMapper.CONSECUTIVE_NEW_ENTRY_EXCEPTION);
        setDescription("You must wait for your friends to create a new entry before you do again.");
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
