package com.nwm.coauthor.service.controller;

import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.resource.request.AddEntryRequest;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.AddEntryResponse;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;
import com.nwm.coauthor.service.resource.response.PrivateStoriesResponseWrapper;

public interface StoryController {
	public ResponseEntity<CreateStoryResponse> createStory(String coToken, CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException;
	public ResponseEntity<PrivateStoriesResponseWrapper> getPrivateStories(String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException;
//	public ResponseEntity<PrivateStoryResponse> getPrivateStory(String coToken, String storyId) throws SomethingWentWrongException;
	public ResponseEntity<AddEntryResponse> addEntry(String coToken, AddEntryRequest entry) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException; 
}