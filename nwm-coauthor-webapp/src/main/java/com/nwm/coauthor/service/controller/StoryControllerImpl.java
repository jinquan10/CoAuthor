package com.nwm.coauthor.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.resource.request.CoAuthorRequest;

@Controller
@RequestMapping(value = "/story", produces = "application/json", consumes = "application/json")
public class StoryControllerImpl extends BaseControllerImpl implements StoryController{
	@Autowired
	private AuthenticationManagerImpl authenticationManager = null;
	
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createStory() {
		
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
