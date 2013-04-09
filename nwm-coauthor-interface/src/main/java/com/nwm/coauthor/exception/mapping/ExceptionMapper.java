package com.nwm.coauthor.exception.mapping;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AddEntryVersionException;
import com.nwm.coauthor.exception.AlreadyAMemberException;
import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.AlreadyPublishedException;
import com.nwm.coauthor.exception.AlreadyRatedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BaseException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.ConsecutiveEntryBySameMemberException;
import com.nwm.coauthor.exception.MemberOrLeaderException;
import com.nwm.coauthor.exception.MoreEntriesLeftException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotPublishedYetException;
import com.nwm.coauthor.exception.UnauthorizedException;
import com.nwm.coauthor.exception.UnpublishedStoryLikedException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.exception.UserLikingOwnStoryException;
import com.nwm.coauthor.exception.VersioningException;

public enum ExceptionMapper {
	AUTHENTICATION_UNAUTHORIZED_EXCEPTION(AuthenticationUnauthorizedException.class),
	SOMETHING_WENT_WRONG_EXCEPTION(SomethingWentWrongException.class),
	BAD_REQUEST_EXCEPTION(BadRequestException.class),
	ADD_ENTRY_EXCEPTION(AddEntryException.class),
	STORY_NOT_FOUND_EXCEPTION(StoryNotFoundException.class),
	ALREADY_LIKED_EXCEPTION(AlreadyLikedException.class),
	USER_LIKING_OWN_STORY_EXCEPTION(UserLikingOwnStoryException.class),
	UNAUTHORIZED_EXCEPTION(UnauthorizedException.class),
	ADD_ENTRY_VERSION_EXCEPTION(AddEntryVersionException.class),
	USER_IS_NOT_LEADER_EXCEPTION(UserIsNotLeaderException.class),
	NO_TITLE_FOR_PUBLISHING_EXCEPTION(NoTitleForPublishingException.class),
	UNPUBLISHED_STORY_LIKED_EXCEPTION(UnpublishedStoryLikedException.class),
	ALREADY_PUBLISHED_EXCEPTION(AlreadyPublishedException.class),
	STORY_NOT_PUBLISHED_YET_EXCEPTION(StoryNotPublishedYetException.class),
	MEMBER_OR_LEADER_EXCEPTION(MemberOrLeaderException.class),
	CANNOT_GET_ENTRIES_EXCEPTION(CannotGetEntriesException.class),
	NON_MEMBER_OR_LEADER_EXCEPTION(NonMemberException.class),
	VERSIONING_EXCEPTION(VersioningException.class),
	CONSECUTIVE_NEW_ENTRY_EXCEPTION(ConsecutiveEntryBySameMemberException.class),
	MORE_ENTRIES_LEFT_EXCEPTION(MoreEntriesLeftException.class),
	ALREADY_A_MEMBER_EXCEPTION(AlreadyAMemberException.class),
	ALREADY_RATED_EXCEPTION(AlreadyRatedException.class);
	
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
