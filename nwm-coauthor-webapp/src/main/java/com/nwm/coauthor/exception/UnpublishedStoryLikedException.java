package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;

public class UnpublishedStoryLikedException extends BaseException{
    public UnpublishedStoryLikedException(){
        setId(ExceptionMapper.UNPUBLISHED_STORY_LIKED_EXCEPTION);
        setDescription("You cannot like an unpublished story.");
        setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
