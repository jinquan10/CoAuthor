package com.nwm.coauthor.service.controller;

import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.AlreadyPublishedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.ConsecutiveEntryBySameMemberException;
import com.nwm.coauthor.exception.MoreEntriesLeftException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnpublishedStoryLikedException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.exception.UserLikingOwnStoryException;
import com.nwm.coauthor.exception.VersioningException;
import com.nwm.coauthor.service.resource.request.ChangeTitleRequest;
import com.nwm.coauthor.service.resource.request.NewEntryRequest;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.StoriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public interface StoryController {
	public ResponseEntity<StoryResponse> createStory(String coToken, NewStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException;
	public ResponseEntity<StoriesResponse> getMyStories(String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException;
	public ResponseEntity<EntriesResponse> getEntries(String coToken, String storyId, Integer beginIndex, Integer clientCharVersion) throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException, StoryNotFoundException, VersioningException, MoreEntriesLeftException, SomethingWentWrongException;
	public ResponseEntity<StoryResponse> newEntry(String coToken, String storyId, NewEntryRequest newEntryRequest) throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException, SomethingWentWrongException;
	public ResponseEntity<StoryResponse> getMyStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, SomethingWentWrongException, NonMemberException;
	public void likeStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, AlreadyLikedException, StoryNotFoundException, SomethingWentWrongException, UserLikingOwnStoryException, UnpublishedStoryLikedException;
	public ResponseEntity<StoryResponse> publishStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, SomethingWentWrongException;
    public ResponseEntity<StoryResponse> changeTitle(String coToken, String storyId, ChangeTitleRequest request) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException, AlreadyLikedException;
    
    // public newFriend();
    // public changeLeader();
    
        
    //	public void comment(String coToken, String storyId, CommentRequest request);
}