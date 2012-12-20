package com.nwm.coauthor.service.controller;

import org.apache.log4j.Logger;
import com.nwm.coauthor.exception.BaseException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;

public class BaseControllerImpl {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<BaseException> handleExceptions(Throwable t) {
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
	
	protected ResponseEntity<BaseException> getSomethingWentWrongExceptionBody(Throwable t){
		if(t.getMessage() != null){
			logger.error(t.getMessage());			
		}
		
		SomethingWentWrongException body = new SomethingWentWrongException();
		
		return new ResponseEntity<BaseException>(body, HttpStatus.valueOf(body.getStatusCode()));
	}
}
