package com.nwm.coauthor.exception.mapping;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AddEntryVersionException;
import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BaseException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.UnauthorizedException;
import com.nwm.coauthor.exception.UserLikingOwnStoryException;

public enum ExceptionMapper {
	AUTHENTICATION_UNAUTHORIZED_EXCEPTION(AuthenticationUnauthorizedException.class),
	SOMETHING_WENT_WRONG_EXCEPTION(SomethingWentWrongException.class),
	BAD_REQUEST_EXCEPTION(BadRequestException.class),
	ADD_ENTRY_EXCEPTION(AddEntryException.class),
	STORY_NOT_FOUND_EXCEPTION(StoryNotFoundException.class),
	ALREADY_LIKED_EXCEPTION(AlreadyLikedException.class),
	USER_LIKING_OWN_STORY_EXCEPTION(UserLikingOwnStoryException.class),
	UNAUTHORIZED_EXCEPTION(UnauthorizedException.class),
	ADD_ENTRY_VERSION_EXCEPTION(AddEntryVersionException.class);
	
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
