package com.nwm.coauthor.exception;

import org.springframework.web.client.HttpStatusCodeException;

public class HttpException extends Exception {
    private HttpStatusCodeException httpStatusCodeException;
    
    public HttpException(HttpStatusCodeException e){
        this.setHttpStatusCodeException(e);
    }

    public HttpStatusCodeException getHttpStatusCodeException() {
        return httpStatusCodeException;
    }

    public void setHttpStatusCodeException(HttpStatusCodeException httpStatusCodeException) {
        this.httpStatusCodeException = httpStatusCodeException;
    }
}
