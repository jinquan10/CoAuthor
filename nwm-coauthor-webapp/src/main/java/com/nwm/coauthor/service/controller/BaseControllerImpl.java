package com.nwm.coauthor.service.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nwm.coauthor.exception.BaseException;
import com.nwm.coauthor.exception.SomethingWentWrongException;

public class BaseControllerImpl {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseException> handleExceptions(Throwable t) throws IOException {
        if (t instanceof BaseException) {
            return getExceptionBody(t);
        } else {
            return getSomethingWentWrongExceptionBody(t);
        }
    }

    protected ResponseEntity<BaseException> getExceptionBody(Throwable t) {
        BaseException baseException = (BaseException) t;

        return new ResponseEntity<BaseException>(baseException, baseException.getHttpStatus());
    }

    protected ResponseEntity<BaseException> getSomethingWentWrongExceptionBody(Throwable t) throws IOException {
        // StringWriter stringWriter = new StringWriter();
        // PrintWriter printWriter = new PrintWriter(stringWriter);
        //
        // t.printStackTrace(printWriter);
        // printWriter.flush();
        // logger.error(stringWriter.toString());

        logger.error(t.getMessage(), t);

        SomethingWentWrongException body = new SomethingWentWrongException();

        return new ResponseEntity<BaseException>(body, body.getHttpStatus());
    }
}
