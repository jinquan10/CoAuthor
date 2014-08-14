package com.nwm.coauthor.exception.mapping;

import com.nwm.coauthor.exception.BaseException;

public class ExceptionMapperWrapper {
	private ExceptionMapper exceptionMapper;
	private BaseException baseException;
	
	public ExceptionMapperWrapper(){
		
	}
	
	public ExceptionMapperWrapper(ExceptionMapper exceptionMapper, BaseException baseException){
		this.exceptionMapper = exceptionMapper;
		this.baseException = baseException;
	}

	public Class<?> getClazz(){
		return exceptionMapper.getClazz();
	}
	
	public ExceptionMapper getExceptionMapper() {
		return exceptionMapper;
	}

	public void setExceptionMapper(ExceptionMapper exceptionMapper) {
		this.exceptionMapper = exceptionMapper;
	}

	public BaseException getBaseException() {
		return baseException;
	}

	public void setBaseException(BaseException baseException) {
		this.baseException = baseException;
	}
}
