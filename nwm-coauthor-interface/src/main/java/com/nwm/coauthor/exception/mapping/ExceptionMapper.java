package com.nwm.coauthor.exception.mapping;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BaseException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.GetPrivateStoryException;
import com.nwm.coauthor.exception.SomethingWentWrongException;

public enum ExceptionMapper {
	AUTHENTICATION_UNAUTHORIZED_EXCEPTION(AuthenticationUnauthorizedException.class),
	SOMETHING_WENT_WRONG_EXCEPTION(SomethingWentWrongException.class),
	BAD_REQUEST_EXCEPTION(BadRequestException.class),
	ADD_ENTRY_EXCEPTION(AddEntryException.class),
	GET_PRIVATE_STORY_EXCEPTION(GetPrivateStoryException.class);
	
	private Class<? extends BaseException> clazz;
	
	ExceptionMapper(Class<? extends BaseException> clazz){
		this.setClazz(clazz);
	}

	public Class<? extends BaseException> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends BaseException> clazz) {
		this.clazz = clazz;
	}
}
