package com.nwm.coauthor.service.controller;

import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.CreateStoryBadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;

public interface StoryController {
	public ResponseEntity<String> createStory(String coToken, CreateStoryRequest createStoryRequest) throws SomethingWentWrongException, AuthenticationUnauthorizedException, CreateStoryBadRequestException;
}
