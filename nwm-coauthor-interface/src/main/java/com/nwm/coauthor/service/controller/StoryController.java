package com.nwm.coauthor.service.controller;

import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.ConsecutiveNewEntryException;
import com.nwm.coauthor.exception.NonMemberOrLeaderException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.VersioningException;
import com.nwm.coauthor.service.resource.request.NewEntryRequest;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.StoriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public interface StoryController {
	public ResponseEntity<StoryResponse> createStory(String coToken, NewStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException;
	public ResponseEntity<StoriesResponse> getMyStories(String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException;
	public ResponseEntity<EntriesResponse> getEntries(String coToken, String storyId, Integer beginIndex) throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException;
	public void newEntry(String coToken, String storyId, NewEntryRequest newEntryRequest) throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberOrLeaderException, ConsecutiveNewEntryException;
//	public ResponseEntity<PrivateStoryResponse> getStoryForEdit(String coToken, String storyId) throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, UnauthorizedException;
//	public ResponseEntity<AddEntryResponse> addEntry(String coToken, EntryRequest entry) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException, StoryNotFoundException, AddEntryVersionException;
//	public void likeStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, AlreadyLikedException, StoryNotFoundException, SomethingWentWrongException, UserLikingOwnStoryException, UnpublishedStoryLikedException;
//	public void publishStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, SomethingWentWrongException;
//    public void changeStoryTitle(String coToken, String storyId, ChangeTitleRequest request) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException, AlreadyLikedException;
//	public void comment(String coToken, String storyId, CommentRequest request);
}