package com.nwm.coauthor.exception.mapping;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.SomethingWentWrongException;

public enum ExceptionMapper {
	AUTHENTICATION_UNAUTHORIZED_EXCEPTION(AuthenticationUnauthorizedException.class),
	SOMETHING_WENT_WRONG_EXCEPTION(SomethingWentWrongException.class);
	
	private Class<? extends Throwable> clazz;
	
	ExceptionMapper(Class<? extends Throwable> clazz){
		this.setClazz(clazz);
	}

	public Class<? extends Throwable> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends Throwable> clazz) {
		this.clazz = clazz;
	}
}
