package com.nwm.coauthor.service.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nwm.coauthor.exception.BaseException;
import com.nwm.coauthor.exception.SomethingWentWrongException;

public class BaseControllerImpl {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<BaseException> handleExceptions(Throwable t) throws IOException {
		if(t instanceof BaseException){
			return getExceptionBody(t);			
		}else{
			return getSomethingWentWrongExceptionBody(t);
		}
	}
	
	protected ResponseEntity<BaseException> getExceptionBody(Throwable t){
		BaseException baseException = (BaseException)t;
		
		return new ResponseEntity<BaseException>(baseException, HttpStatus.valueOf(baseException.getStatusCode()));
	}
	
	protected ResponseEntity<BaseException> getSomethingWentWrongExceptionBody(Throwable t) throws IOException{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		
		t.printStackTrace(printWriter);
	    printWriter.flush();
	    logger.error(stringWriter.toString());
		
		SomethingWentWrongException body = new SomethingWentWrongException();
		
		return new ResponseEntity<BaseException>(body, HttpStatus.valueOf(body.getStatusCode()));
	}
}
