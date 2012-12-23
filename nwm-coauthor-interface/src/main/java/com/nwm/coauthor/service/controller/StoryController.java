package com.nwm.coauthor.service.controller;

import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.service.resource.request.CoAuthorRequest;

public interface StoryController {
	public ResponseEntity<String> createStory(String userId);
}
