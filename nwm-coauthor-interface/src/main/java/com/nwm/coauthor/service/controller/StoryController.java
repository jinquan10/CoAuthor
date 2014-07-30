package com.nwm.coauthor.service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AlreadyAMemberException;
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
import com.nwm.coauthor.service.resource.request.EntryRequest;
import com.nwm.coauthor.service.resource.request.NewEntryRequest;
import com.nwm.coauthor.service.resource.request.NewFriendsRequest;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.StoryInListResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

public interface StoryController {

    public void createStory(Long timeZoneOffsetMinutes, String coToken, NewStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException;
    ResponseEntity<List<StoryInListResponse>> getTopViewStories();
    ResponseEntity<StoryResponse> getStory(String id);
    void entryRequest(Long timeZoneOffsetMinutes, String coToken, EntryRequest entry);
    
    
    
    
    
    
    // - Read
//    public ResponseEntity<StoriesInListResponse> getMyStories(String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException;
	public ResponseEntity<StoryInListResponse> getMyStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, SomethingWentWrongException, NonMemberException;

	// - Update
	public ResponseEntity<StoryInListResponse> newEntry(String coToken, String storyId, NewEntryRequest newEntryRequest) throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException, SomethingWentWrongException;
	public ResponseEntity<StoryInListResponse> likeStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, AlreadyLikedException, StoryNotFoundException, SomethingWentWrongException, UserLikingOwnStoryException, UnpublishedStoryLikedException;
	public ResponseEntity<StoryInListResponse> publishStory(String coToken, String storyId) throws BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, SomethingWentWrongException;
    public ResponseEntity<StoryInListResponse> changeTitle(String coToken, String storyId, ChangeTitleRequest request) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException, AlreadyLikedException;
    public ResponseEntity<StoryInListResponse> newFriends(String coToken, String storyId, NewFriendsRequest request) throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, AlreadyAMemberException, NonMemberException;

    public ResponseEntity<StoryInListResponse> rateStory(String coToken, String storyId, Integer rating) throws SomethingWentWrongException, BadRequestException;

    // public newFavoriteStory();
    // public getFavoriteStories();
    // public getFavoriteStories();
    // public newFavoriteStory();
    // public changeLeader();
    // public removeMember();
    
    public ResponseEntity<EntriesResponse> getEntries(String coToken, String storyId, Integer beginIndex, Integer clientCharVersion) throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException, StoryNotFoundException, VersioningException, MoreEntriesLeftException, SomethingWentWrongException;
    // get 1 public story
    // get public stories by latest
    // get public stories by title
    // get public stories by # of likes
    // get public stories by # of comments
    // get public stories by longest
    //	public void comment(String coToken, String storyId, CommentRequest request);
	

}