package com.nwm.coauthor.exception.mapping;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BaseException;
import com.nwm.coauthor.exception.CreateStoryBadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;

public enum ExceptionMapper {
	AUTHENTICATION_UNAUTHORIZED_EXCEPTION(AuthenticationUnauthorizedException.class),
	SOMETHING_WENT_WRONG_EXCEPTION(SomethingWentWrongException.class),
	CREATE_STORY_BAD_REQUEST_EXCEPTION(CreateStoryBadRequestException.class);
	
	private Class<? extends BaseException> clazz;
	private BaseException baseException;
	
	ExceptionMapper(Class<? extends BaseException> clazz){
		this.setClazz(clazz);
	}

	public Class<? extends BaseException> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends BaseException> clazz) {
		this.clazz = clazz;
	}

	public BaseException getBaseException() {
		return baseException;
	}

	public void setBaseException(BaseException baseException) {
		this.baseException = baseException;
	}
}
