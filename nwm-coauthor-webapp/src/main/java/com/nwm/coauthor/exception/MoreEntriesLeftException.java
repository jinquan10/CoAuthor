package com.nwm.coauthor.exception;

import org.springframework.http.HttpStatus;

import com.nwm.coauthor.exception.mapping.ExceptionMapper;
import com.nwm.coauthor.service.resource.response.EntriesResponse;

public class MoreEntriesLeftException extends BaseException {
    public MoreEntriesLeftException(EntriesResponse r){
        this.setId(ExceptionMapper.MORE_ENTRIES_LEFT_EXCEPTION);
        this.setHttpStatus(HttpStatus.REQUEST_ENTITY_TOO_LARGE);
        this.setEntriesResponse(r);
    }
    
    public MoreEntriesLeftException(BaseException e){
        setId(e.getId());
        setHttpStatus(e.getHttpStatus());
        setEntriesResponse(e.getEntriesResponse());
    }
}
